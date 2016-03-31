package com.tylz.jiaoyanglogistics.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;


/**
 * @author cxw
 * @time 2016/3/18 0018 15:02
 * @des 里层ViewPager,左右滑动的时候,请求父容器不拦截
 * @updateAuthor tylz
 * @updateDate 2016/3/18 0018
 * @updateDes
 */
public class InnerViewPager extends ViewPager {

	private float	mDownX;
	private float	mDownY;

	public InnerViewPager(Context context) {
		super(context);
	}

	public InnerViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	// 1.
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		return super.dispatchTouchEvent(ev);
	}

	// 2.
	@Override
	public boolean onInterceptHoverEvent(MotionEvent event) {
		return super.onInterceptHoverEvent(event);
	}

	// 3.
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			mDownX = ev.getRawX();
			mDownY = ev.getRawY();
			break;
		case MotionEvent.ACTION_MOVE:
			float moveX = ev.getRawX();
			float moveY = ev.getRawY();

			int diffX = (int) (moveX - mDownX+.5f);
			int diffY = (int) (moveY - mDownY+.5f);

            if(Math.abs(diffX)>Math.abs(diffY)){//左右滑动
                getParent().requestDisallowInterceptTouchEvent(true);
            }else{
                getParent().requestDisallowInterceptTouchEvent(false);
            }

			break;
		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_CANCEL:

			break;

		default:
			break;
		}
		return super.onTouchEvent(ev);
	}
}
