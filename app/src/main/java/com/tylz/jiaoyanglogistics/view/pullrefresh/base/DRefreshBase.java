package com.tylz.jiaoyanglogistics.view.pullrefresh.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.Scroller;

/**
 * 这个实现了下拉刷新和上拉加载更多的功能
 * 
 * @author jason
 */
public abstract class DRefreshBase<T extends View> extends LinearLayout {
	
    /**阻尼系数*/
    private static final float OFFSET_RADIO = 2.5f;
    /**上一次移动的点 */
    private float mLastMotionY = -1;
    /**头部*/
    private DHeaderLayout headerLayout;
    /**尾部*/
    private DFooterLayout footerLayout;
    /**可以下拉刷新的View*/
    protected T refreshableView;
    /**表示是否消费了touch事件，如果是，则不调用父类的onTouchEvent方法*/
    private boolean mIsHandledTouchEvent = false;
    /**移动点的保护范围值*/
    private int mTouchSlop;
    private Scroller mScroller;
	public DRefreshBase(Context context) {
        super(context);
        this.initView(context, null);
    }
    public DRefreshBase(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }
    @SuppressLint("NewApi")
	public DRefreshBase(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.initView(context, attrs);
    }
	private void initView(Context context, AttributeSet attrs) {
		this.setOrientation(LinearLayout.VERTICAL);
		mScroller = new Scroller(context, new DecelerateInterpolator());
		
		mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
		
		headerLayout = new DHeaderLayout(context);
		footerLayout=new DFooterLayout(context);
		refreshableView = this.createRefreshableView(context, attrs);
		if (null == refreshableView) {
			throw new NullPointerException("Refreshable view can not be null.");
		}
		this.addView(headerLayout, 0, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT));
		this.addView(refreshableView, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
		this.addView(footerLayout, -1, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,300));
        getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
            	 // 这里得到Header和Footer的高度，设置的padding的top和bottom就应该是header和footer的高度
                // 因为header和footer是完全看不见的
            	refreshLoadingViewsSize();
                getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });
	}
	private void refreshLoadingViewsSize() {
		 int pLeft = getPaddingLeft();
         int pTop = getPaddingTop();
         int pRight = getPaddingRight();
         int pBottom = getPaddingBottom();
         pTop = -headerLayout.getMeasuredHeight();
         pBottom = -footerLayout.getMeasuredHeight() ;
         DRefreshBase.this.setPadding(pLeft, pTop, pRight, pBottom);
	}
	@Override
    protected final void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        refreshLoadingViewsSize();
        // 设置刷新View的大小
        LayoutParams lp = (LayoutParams) refreshableView.getLayoutParams();
        if (lp.height != h) {
            lp.height = h;
            refreshableView.requestLayout();
        }
        post(new Runnable() {
            @Override
            public void run() {
                requestLayout();
            }
        });
    }
	
	@Override
	public void computeScroll() {
		if (mScroller.computeScrollOffset()) {
			this.scrollTo(0, -mScroller.getCurrY());
			postInvalidate();
		}
		super.computeScroll();
	}
  
    @Override
    public final boolean onInterceptTouchEvent(MotionEvent event) {
        if (!headerLayout.isRefreshEnabled()&&!footerLayout.isRefreshEnabled()) {
        	//下拉加载可用
            return false;
        }
        final int action = event.getAction();
        if (action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP) {
            mIsHandledTouchEvent = false;
            return false;
        }
        if (action != MotionEvent.ACTION_DOWN && mIsHandledTouchEvent) {
            return true;
        }
        switch (action) {
	        case MotionEvent.ACTION_DOWN:
	            mLastMotionY = event.getY();
	            mIsHandledTouchEvent = false;
	            break;
	        case MotionEvent.ACTION_MOVE:
	            final float deltaY = event.getY() - mLastMotionY;
	            final float absDiff = Math.abs(deltaY);
	            // 这里有三个条件：
	            // 1，位移差大于mTouchSlop，这是为了防止快速拖动引发刷新
	            // 2，isPullRefreshing()，如果当前正在下拉刷新的话，是允许向上滑动，并把刷新的HeaderView挤上去
	            // 3，isPullLoading()，理由与第2条相同
	            if (absDiff > mTouchSlop || headerLayout.isRefreshing()||footerLayout.isRefreshing())  {
	                mLastMotionY = event.getY();
	                // 第一个显示出来，Header已经显示或拉下
	                if (headerLayout.isRefreshEnabled()&& isReadyForPullDown()) {
	                    // 1，Math.abs(getScrollY()) > 0：表示当前滑动的偏移量的绝对值大于0，表示当前HeaderView滑出来了或完全
	                    // 不可见，存在这样一种case，当正在刷新时并且RefreshableView已经滑到顶部，向上滑动，那么我们期望的结果是
	                    // 依然能向上滑动，直到HeaderView完全不可见
	                    // 2，deltaY > 0.5f：表示下拉的值大于0.5f
	                    mIsHandledTouchEvent = (Math.abs(getScrollY()) > 0 || deltaY > 0.5f);
	                    // 如果截断事件，我们则仍然把这个事件交给刷新View去处理，典型的情况是让ListView/GridView将按下
	                    // Child的Selector隐藏
	                    if (mIsHandledTouchEvent) {
	                    	refreshableView.onTouchEvent(event);
	                    }
	                }else if (footerLayout.isRefreshEnabled() && isReadyForPullUp()) {
	                    // 原理如上
	                    mIsHandledTouchEvent = (Math.abs(getScrollY()) > 0 || deltaY < -0.5f);
	                }
	               
	            }
	            break; 
	        default:
	            break;
        }
        return mIsHandledTouchEvent;
    }

    
	@Override
    public boolean onTouchEvent(MotionEvent ev) {
		 boolean handled = false;
	        switch (ev.getAction()) {
	        case MotionEvent.ACTION_DOWN:
	            mLastMotionY = ev.getY();
	            mIsHandledTouchEvent = false;
	            break;
	        case MotionEvent.ACTION_MOVE:
	            final float deltaY = ev.getY() - mLastMotionY;
	            mLastMotionY = ev.getY();
	            if (headerLayout.isRefreshEnabled() && isReadyForPullDown()) {
	            	headerLayout.pullLayout(this,deltaY / OFFSET_RADIO);
	                handled = true;
	            }else if (footerLayout.isRefreshEnabled() && isReadyForPullUp()) {
	            	footerLayout.pullLayout(this,deltaY / OFFSET_RADIO);
	                handled = true;
	            } else {
	                mIsHandledTouchEvent = false;
	            }
	            break;
	        case MotionEvent.ACTION_CANCEL:
	        case MotionEvent.ACTION_UP:
	        	if (mIsHandledTouchEvent) {
	                mIsHandledTouchEvent = false;
	                // 当第一个显示出来时
	                if (headerLayout.isRefreshEnabled()&&this.isReadyForPullDown()) {
	                    // 调用刷新
	                    if (headerLayout.isRefreshEnabled() && (headerLayout.getState() == DState.RELEASE_TO_REFRESH)) {
	                    	//开始刷新
	                    	headerLayout.startRefreseToPull();
	                        handled = true;
	                    }
	                    headerLayout.resetLayout(this,mScroller);
	                }else if (footerLayout.isRefreshEnabled()&&this.isReadyForPullUp()) {
	                    // 加载更多
	                    if (footerLayout.isRefreshEnabled() && (footerLayout.getState() == DState.RELEASE_TO_REFRESH)) {
	                    	footerLayout.startRefreseToUp();
	                        handled = true;
	                    }
	                    footerLayout.resetLayout(this,mScroller);
	                }
	            }
	            break;
	        default:
	            break;
	        }
	        return handled;
	}

	public void setOnRefreshListener(DOnRefreshListener refreshListener) {
		if(headerLayout!=null){
			headerLayout.setOnRefreshListener(refreshListener);
		}
		if(footerLayout!=null){
			footerLayout.setOnRefreshListener(refreshListener);
		}
	}
	/**设置头部加载是否可用*/
	public void setPullDownEnabled(boolean isRefresh){
		headerLayout.setRefreshEnabled(isRefresh);
	}
	/**设置底部加载是否可用*/
	public void setPullUpEnabled(boolean isLoad){
		footerLayout.setRefreshEnabled(isLoad);
	}
	/**取消头部刷新*/
	public void onStopDownRefresh(Boolean isRefreseSuccess){
		headerLayout.stopRefrese(this, mScroller,isRefreseSuccess);
	}
	/**取消底部刷新*/
	public void onStopUpRefresh(Boolean isMoreData){
		footerLayout.stopRefrese(this, mScroller,isMoreData);
	}
    protected abstract T createRefreshableView(Context context, AttributeSet attrs);
    
    /**
     * 判断刷新的View是否滑动到顶部
     * @return true表示已经滑动到顶部，否则false
     */
    protected abstract boolean isReadyForPullDown();
    
    /**
     * 判断刷新的View是否滑动到底
     * @return true表示已经滑动到底部，否则false
     */
    protected abstract boolean isReadyForPullUp();
    
    public T getRefreshableView(){
    	return refreshableView;
    }
}
