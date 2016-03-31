package com.tylz.jiaoyanglogistics.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * @author tylz
 * @time 2016/3/21 0021 13:49
 * @des 可以控制是否滚动的viewpager
 *
 * @updateAuthor
 * @updateDate 2016/3/21 0021
 * @updateDes
 */
public class ControlScrollViewPager extends ViewPager {
    private boolean isEnable = true;
    public ControlScrollViewPager(Context context) {
        super(context);
    }

    public ControlScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public void setScrollable(boolean enable){
        isEnable = enable;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if(isEnable){
            return super.onInterceptTouchEvent(ev);
        }else{
            return false;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if(isEnable){
            return super.onTouchEvent(ev);
        }else{
            return false;
        }
    }
}
