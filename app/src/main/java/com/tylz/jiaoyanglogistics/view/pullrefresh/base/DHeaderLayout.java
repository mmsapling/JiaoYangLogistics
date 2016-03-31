package com.tylz.jiaoyanglogistics.view.pullrefresh.base;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Scroller;
import android.widget.TextView;

import com.tylz.jiaoyanglogistics.R;


public class DHeaderLayout extends DLoadingLayout {
	private RelativeLayout headerContainer;
	private ImageView arrowView = null;// 箭头
	private ProgressBar progressBar = null;// 加载条
	private TextView hintView = null;// 加载提示内容
	private Animation arrowUpAnim = null;// 箭头向上动画
	private Animation arrowDownAnim = null;// 箭头先下动画
	private final int ROTATE_ANIM_DURATION = 180;// 箭头旋转度数
   
    private DOnRefreshListener refreshListener;
    
	public DHeaderLayout(Context context) {
		super(context);
		this.initView(context);
	}
	public DHeaderLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.initView(context);
	}

	private void initView(Context context) {
		headerContainer=(RelativeLayout) findViewById(R.id.pullrefresh_header_layout);
		
		arrowView = (ImageView) findViewById(R.id.pullrefresh_header_arrow);
		hintView = (TextView) findViewById(R.id.pullrefresh_header_text);
		progressBar = (ProgressBar) findViewById(R.id.pullrefresh_header_progressbar);

		/*** 以0.0的位置为起始点，逆时针旋转180度，围绕自身的中心点 */
		arrowUpAnim = new RotateAnimation(0.0f, -180.0f,Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,0.5f);
		arrowUpAnim.setDuration(ROTATE_ANIM_DURATION);
		arrowUpAnim.setFillAfter(true);

		/*** 以逆时针180度的位置为起始点，顺时针旋转到0.0的位置，围绕自身的中心点 */
		arrowDownAnim = new RotateAnimation(-180.0f, 0.0f,Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,0.5f);
		arrowDownAnim.setDuration(ROTATE_ANIM_DURATION);
		arrowDownAnim.setFillAfter(true);
		
	}

    @Override
    protected void onPullToRefresh() {
        if (DState.RELEASE_TO_REFRESH == getPreState()) {
        	arrowView.clearAnimation();
        	arrowView.startAnimation(arrowDownAnim);
        }
        arrowView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
        hintView.setText(R.string.pullrefresh_header_pull_to_refresh);
    }

    @Override
    protected void onReleaseToRefresh() {
    	arrowView.clearAnimation();
    	arrowView.startAnimation(arrowUpAnim);
    	hintView.setText(R.string.pullrefresh_release_to_refresh);
    }

    @Override
    protected void onRefreshing() {
    	arrowView.clearAnimation();
    	arrowView.setVisibility(View.INVISIBLE);
    	progressBar.setVisibility(View.VISIBLE);
        hintView.setText(R.string.pullrefresh_header_refresh);
    }
	@Override
	protected void onRefreseSuccess() {
		progressBar.setVisibility(View.INVISIBLE);
		arrowView.setVisibility(View.INVISIBLE);
        hintView.setText(R.string.pullrefresh_refresh_success);
	}
	@Override
	protected void onRefreseFaile() {
		progressBar.setVisibility(View.INVISIBLE);
		arrowView.setVisibility(View.INVISIBLE);
        hintView.setText(R.string.pullrefresh_refresh_faile);
	}
	@Override
	protected View createLoadingView(Context context, AttributeSet attrs) {
		return LayoutInflater.from(context).inflate(R.layout.pullrefresh_header, null);
	}

    /**开始刷新，当下拉松开后被调用*/
	@Override
    public void startRefreseToPull() {
        if (this.isRefreshing()) {
            return;
        }
        this.setState(DState.REFRESHING);
		if (refreshListener != null) {
			// 因为滚动回原始位置的时间是200，我们需要等回滚完后才执行刷新回调
			postDelayed(new Runnable() {
				@Override
				public void run() {
					refreshListener.onPullDownToRefresh();
				}
			}, SCROLL_DURATION);
		}
    }
	@Override
    public void stopRefrese(final View view, final Scroller mScroller,Boolean isRefreseSuccess) {
        if (this.isRefreshing()) {
        	if(isRefreseSuccess==null){
        		this.setState(DState.NONE);
        		resetLayout(view, mScroller);
        	}else if(isRefreseSuccess){
        		this.setState(DState.REFRESHING_SUCCESS);
        	}else{
        		this.setState(DState.REFRESHING_FAIL);
        	}
        	if(this.getState()!=DState.NONE){
        		postDelayed(new Runnable() {
    				@Override
    				public void run() {
    					resetLayout(view, mScroller);
    				}
    			}, 500);
        	}
        }
    }
    /**
     * 拉动Header Layout时调用
     * @param delta 移动的距离
     */
    protected void pullLayout(View view,float delta) {
    	if(!this.isRefreshEnabled()){
    		return;
    	}
        // 向上滑动，并且当前scrollY为0时，不滑动
        int oldScrollY = view.getScrollY();
        if (delta < 0 && (oldScrollY - delta) >= 0) {
        	view.scrollTo(0, 0);
            return;
        }
        // 向下滑动布局
        view.scrollBy(0, -(int)delta);
        // 未处于刷新状态，更新箭头
        int scrollY = Math.abs(view.getScrollY());
        if (!this.isRefreshing()) { 
            if (scrollY > currentViewHeight) {
            	this.setState(DState.RELEASE_TO_REFRESH);
            } else {
            	this.setState(DState.PULL_TO_REFRESH);
            }
        }
    }
    
	/** 重置header */
	protected void resetLayout(View view, Scroller mScroller) {
		int scrollY = Math.abs(view.getScrollY());
		if (scrollY == 0) {
			return;
		}
		if (this.isRefreshing()) {
			// 正在刷新
			if (scrollY < currentViewHeight) {
				// 当前高度小于最小高度，则还原
				mScroller.startScroll(0, scrollY, 0, -scrollY, SCROLL_DURATION);
			} else if (scrollY > currentViewHeight) {
				// 当前高度大于最小高度，则滚动到默认高度
				mScroller.startScroll(0, scrollY, 0, currentViewHeight - scrollY,SCROLL_DURATION);
			} else {
				return;
			}
		} else {
			mScroller.startScroll(0, scrollY, 0, -scrollY, SCROLL_DURATION);
		}
		view.invalidate();
	}
	public void setOnRefreshListener(DOnRefreshListener refreshListener) {
		this.refreshListener = refreshListener;
	}
	@Override
	public int getCurrentViewHeight() {
		int currentViewHeight = 0;
		if(headerContainer != null){

			currentViewHeight=headerContainer.getHeight();
		}
        if (currentViewHeight < 0) {
        	currentViewHeight = 0;
        }
		return currentViewHeight;
	}
	
	@Override
	public void startRefreseToUp() {
		
	}
}
