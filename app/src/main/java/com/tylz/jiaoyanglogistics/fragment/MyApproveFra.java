package com.tylz.jiaoyanglogistics.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.astuetz.LazyViewPager;
import com.astuetz.PagerSlidingTabStripExtends;
import com.tylz.jiaoyanglogistics.R;
import com.tylz.jiaoyanglogistics.base.BaseFragment;
import com.tylz.jiaoyanglogistics.factory.FragmentFactory;
import com.tylz.jiaoyanglogistics.util.UIUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author tylz
 * @time 2016/3/25 0025 14:04
 * @des 我的认证
 *
 * @updateAuthor
 * @updateDate 2016/3/25 0025
 * @updateDes
 */
public class MyApproveFra
        extends BaseFragment
{
    @Bind(R.id.tabs)
    PagerSlidingTabStripExtends mTabs;
    @Bind(R.id.viewpager)
    LazyViewPager               mViewpager;
    private FragmentPagerAdapter mAdapter;
    private String[] mTabTitles = UIUtils.getStrings(R.array.my_approves);

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = View.inflate(mContext, R.layout.fra_my_approve, null);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {

        mAdapter = new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                BaseFragment fragment = FragmentFactory.createFragment("MyApproveFra" + position);
                return fragment;
            }

            @Override
            public int getCount() {
                if (mTabTitles != null) {
                    return mTabTitles.length;
                }
                return 0;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return mTabTitles[position];
            }
        };
        mViewpager.setAdapter(mAdapter);
        mTabs.setLazyViewPager(mViewpager);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

}
