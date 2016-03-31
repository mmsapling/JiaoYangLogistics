package com.tylz.jiaoyanglogistics.task;

import com.tylz.jiaoyanglogistics.conf.Constants;
import com.tylz.jiaoyanglogistics.util.UIUtils;
import com.tylz.jiaoyanglogistics.view.InnerViewPager;

/**
 * @author tylz
 * @time 2016/3/23 0023 13:19
 * @des  自动轮播任务
 * @des
 * @updateAuthor
 * @updateDate 2016/3/23 0023
 * @updateDes
 */
public class AutoScrollTask implements Runnable {
    private InnerViewPager mViewPager;

    public AutoScrollTask(InnerViewPager viewPager) {
        mViewPager = viewPager;
    }

    public void start() {
        UIUtils.getMainThreadHandler().removeCallbacks(this);
        UIUtils.getMainThreadHandler().postDelayed(this,Constants.TIME_DELAY_LOOP_PIC);
    }

    public void stop() {
      UIUtils.getMainThreadHandler().removeCallbacks(this);
    }

    @Override
    public void run() {
        int currentItem = mViewPager.getCurrentItem();
        currentItem++;
        mViewPager.setCurrentItem(currentItem);
        start();
    }
}
