package com.tylz.jiaoyanglogistics.view.pullrefresh;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ScrollView;

import com.tylz.jiaoyanglogistics.view.pullrefresh.base.DRefreshBase;


/**
 * 封装了ScrollView的下拉刷新
 * @author jason
 */
public class DScrollView extends DRefreshBase<ScrollView> {
   
    public DScrollView(Context context) {
    	super(context);
    }
    public DScrollView(Context context, AttributeSet attrs) {
    	super(context, attrs);
    }
    public DScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
    @Override
    protected ScrollView createRefreshableView(Context context, AttributeSet attrs) {
        ScrollView scrollView = new ScrollView(context);
        return scrollView;
    }

    @Override
    protected boolean isReadyForPullDown() {
        return refreshableView.getScrollY() == 0;
    }
   
    @Override
    protected boolean isReadyForPullUp() {
        View scrollViewChild = refreshableView.getChildAt(0);
        if (null != scrollViewChild) {
            return refreshableView.getScrollY() >= (scrollViewChild.getHeight() - getHeight());
        }
        return false;
    }
}
