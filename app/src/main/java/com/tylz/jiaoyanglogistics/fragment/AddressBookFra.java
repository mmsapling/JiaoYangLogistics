package com.tylz.jiaoyanglogistics.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.astuetz.PagerSlidingTabStripExtends;
import com.tylz.jiaoyanglogistics.R;
import com.tylz.jiaoyanglogistics.activity.MyActivity;
import com.tylz.jiaoyanglogistics.base.BaseFragment;
import com.tylz.jiaoyanglogistics.conf.Constants;
import com.tylz.jiaoyanglogistics.factory.FragmentFactory;
import com.tylz.jiaoyanglogistics.util.ToastUtils;
import com.tylz.jiaoyanglogistics.util.UIUtils;
import com.tylz.jiaoyanglogistics.view.TopMenu;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author tylz
 * @time 2016/3/25 0025 16:43
 * @des 地址薄
 *
 * @updateAuthor
 * @updateDate 2016/3/25 0025
 * @updateDes
 */
public class AddressBookFra
        extends BaseFragment
{

    @Bind(R.id.tabs)
    PagerSlidingTabStripExtends mTabs;
    @Bind(R.id.viewpager)
    ViewPager                   mViewpager;
    @Bind(R.id._container_address)
    LinearLayout                mContainerAddress;
    private FragmentPagerAdapter mAdapter;
    private             String[] mTabTitles = UIUtils.getStrings(R.array.address_books);
    public static final String   TAG        = "edit";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = View.inflate(mContext, R.layout.fra_address_book, null);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private void initView() {

        mAdapter = new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                BaseFragment fragment = FragmentFactory.createFragment("AddressBookFra" + position);
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
        mTabs.setViewPager(mViewpager);
        MyActivity activity = (MyActivity) mContext;
        TopMenu    topMenu  = activity.getTopMenu();
        topMenu.setRightIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.makePicTextShortToast(mContext,
                                                 Constants.ICON_SUCCESS,
                                                 R.string.address_book);
            }
        });
    }



}
