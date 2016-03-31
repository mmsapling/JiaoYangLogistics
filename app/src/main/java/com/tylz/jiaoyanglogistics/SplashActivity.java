package com.tylz.jiaoyanglogistics;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.tylz.jiaoyanglogistics.activity.GuideActivity;
import com.tylz.jiaoyanglogistics.activity.MainUserActivity;
import com.tylz.jiaoyanglogistics.base.BaseActivity;
import com.tylz.jiaoyanglogistics.conf.Constants;
import com.tylz.jiaoyanglogistics.factory.ThreadPoolProxyFactory;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author cxw
 * @time 2016/3/18 0018 15:02
 * @des 引导页
 * @updateAuthor tylz
 * @updateDate 2016/3/18 0018
 * @updateDes
 */
public class SplashActivity
        extends BaseActivity
{

    @Bind(R.id.splash_root)
    ImageView      mSplashRoot;
    @Bind(R.id.splash_bg)
    RelativeLayout mSplashBg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {

        //动画
        //1.旋转动画
        RotateAnimation rotateAnimation = new RotateAnimation(0,
                                                              360,
                                                              Animation.RELATIVE_TO_SELF,
                                                              0.5f,
                                                              Animation.RELATIVE_TO_SELF,
                                                              0.5f);
        //2.缩放动画(缩放的是比例)
        ScaleAnimation scaleAnimation = new ScaleAnimation(0.9f,
                                                           1f,
                                                           0.9f,
                                                           1f,
                                                           Animation.RELATIVE_TO_PARENT,
                                                           0.5f,
                                                           Animation.RELATIVE_TO_PARENT,
                                                           0.5f);
        //3.透明动画
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.6f, 1f);
        //4.动画集合
        AnimationSet animationSet = new AnimationSet(true);
        // animationSet.addAnimation(rotateAnimation);
        animationSet.addAnimation(scaleAnimation);
        animationSet.addAnimation(alphaAnimation);
        animationSet.setDuration(Constants.SPLASH_ANIMATION_DURATION);
        animationSet.setAnimationListener(new SplashAnimationListener());
        mSplashRoot.startAnimation(animationSet);
        mSplashBg.startAnimation(alphaAnimation);
    }

    private class SplashAnimationListener
            implements Animation.AnimationListener
    {


        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            //动画完成后，如果是第一次使用应用，那么就进入引导页面，否则进入主页
            ThreadPoolProxyFactory.createNormalThreadPoolProxy()
                                  .execute(new SpalshWaitTask());
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }

    private class SpalshWaitTask
            implements Runnable
    {

        @Override
        public void run() {
            SystemClock.sleep(Constants.SPLASH_SLEEP);
            boolean isFirstEnter = mSpUtils.getBoolean(Constants.IS_FIRST_ENTER,
                                                       Constants.IS_FIRST_ENTER_DEFAULT);
            if (isFirstEnter) {
                //第一次启动-->到引导页面
                Intent intent = new Intent(SplashActivity.this, GuideActivity.class);
                startActivity(intent);
                finish();
            } else {
                /**
                 *  1进入主页
                 *  2.判断是司机还是用户，第一次选择司机，那么以后都进入到司机页面，用户也是一样
                 */
                boolean isDriver = mSpUtils.getBoolean(Constants.IS_DRIVER, false);
                if (isDriver) {
                    //是司机进入，那么进入到司机端
                } else {
                    //进入到用户端
                    Intent intent = new Intent(SplashActivity.this, MainUserActivity.class);
                    startActivity(intent);
                }
                finish();
            }
        }
    }
}
