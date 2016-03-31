package com.tylz.jiaoyanglogistics.view.pullrefresh;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Adapter;
import android.widget.ListView;

import com.tylz.jiaoyanglogistics.view.pullrefresh.base.DRefreshBase;


public class DListView extends DRefreshBase<ListView> {
	public DListView(Context context) {
		super(context);
	}
	public DListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
	public DListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
	@Override
	protected ListView createRefreshableView(Context context, AttributeSet attrs) {
		ListView listView = new ListView(context);
		listView.setSelector(new ColorDrawable(Color.TRANSPARENT));
		listView.setDivider(new ColorDrawable(Color.TRANSPARENT));
		listView.setVerticalScrollBarEnabled(false);
		listView.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		return listView;
	}
	@Override
	protected boolean isReadyForPullDown() {
		 return this.isFirstItemVisible();
	}
	@Override
	protected boolean isReadyForPullUp() {
		return this.isLastItemVisible();
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
     * 
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
