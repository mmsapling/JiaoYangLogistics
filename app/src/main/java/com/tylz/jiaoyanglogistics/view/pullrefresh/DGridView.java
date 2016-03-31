package com.tylz.jiaoyanglogistics.view.pullrefresh;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Adapter;
import android.widget.GridView;

import com.tylz.jiaoyanglogistics.view.pullrefresh.base.DRefreshBase;


/**
 * 这个类实现了GridView下拉刷新，上加载更多和滑到底部自动加载
 * @author jason
 */
public class DGridView extends DRefreshBase<GridView> {
    
    public DGridView(Context context) {
    	super(context);
    }
    public DGridView(Context context, AttributeSet attrs) {
    	super(context, attrs);
    }
    public DGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
    @Override
    protected GridView createRefreshableView(Context context, AttributeSet attrs) {
        GridView gridView = new GridView(context);
        gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
        gridView.setVerticalScrollBarEnabled(false);
        return gridView;
    }
    
    @Override
    protected boolean isReadyForPullUp() {
        return isLastItemVisible();
    }
    @Override
    protected boolean isReadyForPullDown() {
        return isFirstItemVisible();
    }

    /**
     * 判断第一个child是否完全显示出来
     * @return true完全显示出来，否则false
     */
    private boolean isFirstItemVisible() {
        final Adapter adapter = refreshableView.getAdapter();

        if (null == adapter || adapter.isEmpty()) {
            return true;
        }
        int mostTop = (refreshableView.getChildCount() > 0) ? refreshableView.getChildAt(0).getTop() : 0;
        if (mostTop >= 0) {
            return true;
        }

        return false;
    }

    /**
     * 判断最后一个child是否完全显示出来
     * @return true完全显示出来，否则false
     */
    private boolean isLastItemVisible() {
        final Adapter adapter = refreshableView.getAdapter();
        if (null == adapter || adapter.isEmpty()) {
            return true;
        }
        final int lastItemPosition = adapter.getCount() - 1;
        final int lastVisiblePosition = refreshableView.getLastVisiblePosition();
        /**
         * This check should really just be: lastVisiblePosition == lastItemPosition, but ListView
         * internally uses a FooterView which messes the positions up. For me we'll just subtract
         * one to account for it and rely on the inner condition which checks getBottom().
         */
        if (lastVisiblePosition >= lastItemPosition - 1) {
            final int childIndex = lastVisiblePosition - refreshableView.getFirstVisiblePosition();
            final int childCount = refreshableView.getChildCount();
            final int index = Math.min(childIndex, childCount - 1);
            final View lastVisibleChild = refreshableView.getChildAt(index);
            if (lastVisibleChild != null) {
                return lastVisibleChild.getBottom() <= refreshableView.getBottom();
            }
        }
        return false;
    }
}
