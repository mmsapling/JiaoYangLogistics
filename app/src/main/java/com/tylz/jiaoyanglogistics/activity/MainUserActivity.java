package com.tylz.jiaoyanglogistics.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.tylz.jiaoyanglogistics.R;
import com.tylz.jiaoyanglogistics.base.App;
import com.tylz.jiaoyanglogistics.conf.Constants;
import com.tylz.jiaoyanglogistics.fragment.TabHomeFra;
import com.tylz.jiaoyanglogistics.fragment.TabMsgFra;
import com.tylz.jiaoyanglogistics.fragment.TabMyFra;
import com.tylz.jiaoyanglogistics.util.SPUtils;
import com.tylz.jiaoyanglogistics.util.UIUtils;
import com.tylz.jiaoyanglogistics.view.ChangeColorIconWithText;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author tylz
 * @time 2016/3/19 0019 10:02
 * @des 用户端主页面
 * @updateAuthor
 * @updateDate 2016/3/19 0019
 * @updateDes 主页面
 */

public class MainUserActivity
        extends FragmentActivity
        implements ViewPager.OnPageChangeListener, View.OnClickListener
{
    @Bind(R.id.mainuser_viewpager)
    ViewPager               mViewpager;
    @Bind(R.id.mainuser_tab_home)
    ChangeColorIconWithText mTabHome;
    @Bind(R.id.mainuser_tab_msg)
    ChangeColorIconWithText mTabMsg;
    @Bind(R.id.mainuser_tab_my)
    ChangeColorIconWithText mTabMy;
    private List<ChangeColorIconWithText> mTabIndicators = new ArrayList<ChangeColorIconWithText>();
    private List<Fragment>                mTabs          = new ArrayList<Fragment>();
    private FragmentPagerAdapter mAdapter;
    // 再按一次退出应用程序
    private long                 mPreClickTime;
    private SPUtils              mSPUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_user);
        ButterKnife.bind(this);
        //设置不让软键盘顶起最下面的TAB
        this.getWindow()
            .setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        init();
        initView();
        initData();
        initListener();
    }

    /**
     * 初始化数据
     */
    private void init() {
        mSPUtils = new SPUtils(this);
    }

    private void initListener() {
        mViewpager.addOnPageChangeListener(this);
        mTabHome.setOnClickListener(this);
        mTabMsg.setOnClickListener(this);
        mTabMy.setOnClickListener(this);
    }

    private void initData() {
        mTabIndicators.add(mTabHome);
        mTabIndicators.add(mTabMsg);
        mTabIndicators.add(mTabMy);
        TabHomeFra tabHomeFra = new TabHomeFra();
        TabMsgFra  tabMsgFra  = new TabMsgFra();
        TabMyFra   tabMyFra   = new TabMyFra();
        mTabs.add(tabHomeFra);
        mTabs.add(tabMsgFra);
        mTabs.add(tabMyFra);
        mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {

            @Override
            public int getCount() {
                return mTabs.size();
            }

            @Override
            public Fragment getItem(int position) {
                return mTabs.get(position);
            }
        };
        mViewpager.setAdapter(mAdapter);
    }

    private void initView() {
        mTabHome.setIconAlpha(1.0f);
        mViewpager.setOffscreenPageLimit(2);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (positionOffset > 0) {
            ChangeColorIconWithText left = mTabIndicators.get(position);
            ChangeColorIconWithText right = mTabIndicators.get(position + 1);
            left.setIconAlpha(1 - positionOffset);
            right.setIconAlpha(positionOffset);
        }
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(View v) {
        clickTab(v);
    }

    private void clickTab(View v) {
        resetOtherTabs();
        switch (v.getId()) {
            case R.id.mainuser_tab_home:
                mTabIndicators.get(0)
                              .setIconAlpha(1.0f);
                mViewpager.setCurrentItem(0, false);
                break;
            case R.id.mainuser_tab_msg:
                mTabIndicators.get(1)
                              .setIconAlpha(1.0f);
                mViewpager.setCurrentItem(1, false);
                break;
            case R.id.mainuser_tab_my:
                mTabIndicators.get(2)
                              .setIconAlpha(1.0f);
                mViewpager.setCurrentItem(2, false);
                break;
        }
    }

    /**
     * 重置其他tab的颜色
     */
    private void resetOtherTabs() {
        for (int i = 0; i < mTabIndicators.size(); i++) {
            mTabIndicators.get(i)
                          .setIconAlpha(0);
        }
    }

    @Override
    public void onBackPressed() {

        if (System.currentTimeMillis() - mPreClickTime > 2000) {// 两次连续点击的时间间隔>2s
            Toast.makeText(getApplicationContext(), UIUtils.getString(R.string.exit_app), Toast.LENGTH_SHORT)
                 .show();
            mPreClickTime = System.currentTimeMillis();
            return;
        } else {   // 点的快 完全退出
            ((App) getApplication()).closeApplication();
            super.onBackPressed();// finish

        }

    }
    /**
     * 判断是否登陆
     * @return 登陆了返回true，反之返回false
     */
    public boolean isLogin(){

        boolean isLogin = mSPUtils.getBoolean(Constants.IS_LOGIN, false);
        return isLogin;
    }
}
