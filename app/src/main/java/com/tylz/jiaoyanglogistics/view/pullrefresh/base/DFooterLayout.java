package com.tylz.jiaoyanglogistics.view.pullrefresh.base;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Scroller;
import android.widget.TextView;

import com.tylz.jiaoyanglogistics.R;


public class DFooterLayout
        extends DLoadingLayout
{
    private LinearLayout       footerContainer;
    /**进度条*/
    private ProgressBar        progressBar;
    /** 显示的文本 */
    private TextView           hintView;
    private DOnRefreshListener refreshListener;

    public DFooterLayout(Context context) {
        super(context);
        this.initView(context);
    }

    public DFooterLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.initView(context);
    }

    private void initView(Context context) {
        footerContainer = (LinearLayout) findViewById(R.id.pullrefresh_footer_layout);
        progressBar = (ProgressBar) findViewById(R.id.pullrefresh_footer_progressbar);
        hintView = (TextView) findViewById(R.id.pullrefresh_footer_hint_textview);
    }

    @Override
    protected void onPullToRefresh() {
        progressBar.setVisibility(View.GONE);
        hintView.setVisibility(View.VISIBLE);
        hintView.setText(R.string.pullrefresh_footer_pull_to_refresh);
    }

    @Override
    protected void onReleaseToRefresh() {
        progressBar.setVisibility(View.GONE);
        hintView.setVisibility(View.VISIBLE);
        hintView.setText(R.string.pullrefresh_release_to_refresh);
    }

    @Override
    protected void onRefreshing() {
        progressBar.setVisibility(View.VISIBLE);
        hintView.setVisibility(View.VISIBLE);
        hintView.setText(R.string.pullrefresh_footer_refresh);
    }

    @Override
    protected void onNoMoreData() {
        progressBar.setVisibility(View.GONE);
        hintView.setVisibility(View.VISIBLE);
        hintView.setText(R.string.pullrefresh_refresh_not_more);
    }

    @Override
    protected View createLoadingView(Context context, AttributeSet attrs) {
        return LayoutInflater.from(context)
                             .inflate(R.layout.pullrefresh_footer, null);
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
                    refreshListener.onPullUpToRefresh();
                }
            }, SCROLL_DURATION);
        }
    }

    /**开始刷新，加载更多时*/
    @Override
    public void startRefreseToUp() {
        if (this.isRefreshing()) {
            return;
        }
        this.setState(DState.REFRESHING);
        if (refreshListener != null) {
            // 因为滚动回原始位置的时间是50，我们需要等回滚完后才执行刷新回调
            postDelayed(new Runnable() {
                @Override
                public void run() {
                    refreshListener.onPullUpToRefresh();
                }
            }, SCROLL_DURATION1);
        }
    }


    @Override
    public void stopRefrese(final View view, final Scroller mScroller, Boolean isMoreData) {
        if (this.isRefreshing()) {
            if (isMoreData == null || isMoreData) {
                //正常
                this.setState(DState.NONE);
                resetLayout(view, mScroller);
            } else {
                //没有更多数据
                this.setState(DState.NO_MORE_DATA);
            }
            if (this.getState() != DState.NONE) {
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
     * 拉Footer时调用
     *
     * @param delta 移动的距离
     */
    protected void pullLayout(View view, float delta) {
        if (!this.isRefreshEnabled()) {
            return;
        }
        int oldScrollY = view.getScrollY();
        if (delta > 0 && (oldScrollY - delta) <= 0) {
            view.scrollTo(0, 0);
            return;
        }
        view.scrollBy(0, -(int) delta);

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
                mScroller.startScroll(0, -scrollY, 0, scrollY, SCROLL_DURATION);
            } else if (scrollY > currentViewHeight) {
                // 当前高度大于最小高度，则滚动到默认高度
                mScroller.startScroll(0, -scrollY, 0, scrollY - currentViewHeight, SCROLL_DURATION);
            } else {
                return;
            }
        } else {
            mScroller.startScroll(0, -scrollY, 0, scrollY, SCROLL_DURATION);
        }
        view.invalidate();
    }


    public void setOnRefreshListener(DOnRefreshListener refreshListener) {
        this.refreshListener = refreshListener;
    }

    @Override
    public int getCurrentViewHeight() {
        int currentViewHeight = 0;
        if (footerContainer != null) {
            currentViewHeight = footerContainer.getHeight();
        }
        if (currentViewHeight < 0) {
            currentViewHeight = 0;
        }
        return currentViewHeight;
    }


}
