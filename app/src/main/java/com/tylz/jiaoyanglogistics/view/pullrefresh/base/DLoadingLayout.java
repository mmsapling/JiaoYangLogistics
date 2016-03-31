package com.tylz.jiaoyanglogistics.view.pullrefresh.base;


import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.FrameLayout;

public abstract class DLoadingLayout extends FrameLayout implements DLoading{
	 /**回滚的时间*/
    protected static final int SCROLL_DURATION = 250;
    /**回滚的时间*/
    protected static final int SCROLL_DURATION1 = 50;
	 /**容器布局*/
    private View mContainer;
    /**当前的状态*/
    private DState mCurState = DState.NONE;
    /**前一个状态*/
    private DState mPreState = DState.NONE;
    /**刷新是否可用*/
    private boolean refreshEnabled = false;
    /**当前view高度*/
    protected int currentViewHeight;
    
    public DLoadingLayout(Context context) {
        this(context, null);
    }
    public DLoadingLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
    public DLoadingLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.initView(context, attrs);
    }
    private void initView(Context context, AttributeSet attrs) {
        mContainer = createLoadingView(context, attrs);
        if (null == mContainer) {
            throw new NullPointerException("Loading view can not be null.");
        }
        this.addView(mContainer, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
            	// 得到header的内容高度，它将会作为拖动刷新的一个临界值，如果拖动距离大于这个高度
                // 然后再松开手，就会触发刷新操作
            	currentViewHeight=getCurrentViewHeight();
                getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });
    }
   
    /**
     * 显示或隐藏这个布局
     * @param show flag
     */
    public void show(boolean show) {
        if (show == (View.VISIBLE == getVisibility())) {
            return;
        }
        ViewGroup.LayoutParams params = mContainer.getLayoutParams();
        if (null != params) {
            if (show) {
                params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            } else {
                params.height = 0;
            }
            setVisibility(show ? View.VISIBLE : View.INVISIBLE);
        }
    }
    
	
    @Override
    public void setState(DState state) {
    	if(mCurState == state){
    		return;
    	}
    	mPreState = mCurState;
        mCurState = state;
        switch (state) {
	        case RELEASE_TO_REFRESH:
	            this.onReleaseToRefresh();
	            break;
	        case PULL_TO_REFRESH:
	        	this.onPullToRefresh();
	            break;
	        case REFRESHING:
	        	this.onRefreshing();
	            break;
	        case NO_MORE_DATA:
	        	this.onNoMoreData();
	            break;
	        case REFRESHING_SUCCESS:
	        	this.onRefreseSuccess();
	            break;
	        case REFRESHING_FAIL:
	        	this.onRefreseFaile();
	            break;
	        default:
	            break;
        }
    }
    /**得到当前状态*/
    @Override
    public DState getState() {
        return mCurState;
    }
    /**得到前一个状态 */
    protected DState getPreState() {
        return mPreState;
    }
    
    public boolean isRefreshEnabled() {
		return refreshEnabled;
	}
	public void setRefreshEnabled(boolean refreshEnabled) {
		this.refreshEnabled = refreshEnabled;
	}
	/**
     * 判断是否正在下拉刷新
     * @return true正在刷新，否则false
     */
    protected boolean isRefreshing() {
        return this.getState()==DState.REFRESHING;
    }
    protected void onPullToRefresh() {
    }
    protected void onReleaseToRefresh() {
    }
    protected void onRefreshing() {
    }
    protected void onNoMoreData() {
    }
    protected void onRefreseSuccess() {
    }
    protected void onRefreseFaile() {
    }
    /** 创建Loading的View*/
    protected abstract View createLoadingView(Context context, AttributeSet attrs);
}
