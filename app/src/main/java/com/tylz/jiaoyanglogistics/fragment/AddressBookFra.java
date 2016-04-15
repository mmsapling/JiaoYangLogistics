package com.tylz.jiaoyanglogistics.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
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
import com.tylz.jiaoyanglogistics.util.UIUtils;
import com.tylz.jiaoyanglogistics.view.TopMenu;

import java.util.ArrayList;
import java.util.List;

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

    private static final String TAG_ADD_ADDRESS = "add_address";
    @Bind(R.id.tabs)
    PagerSlidingTabStripExtends mTabs;
    @Bind(R.id.viewpager)
    ViewPager                   mViewpager;
    @Bind(R.id._container_address)
    LinearLayout                mContainerAddress;
    private FragmentPagerAdapter mAdapter;
    private String[] mTabTitles = UIUtils.getStrings(R.array.address_books);
    private MyActivity mActivity;
    private List<Fragment> mFragments = new ArrayList<Fragment>();



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = View.inflate(mContext, R.layout.fra_address_book, null);
        ButterKnife.bind(this, view);
        init();
        mFragments.clear();
        ShipperAddressFra    shipperAddressFra    = new ShipperAddressFra();
        ConSigneesAddressFra conSigneesAddressFra = new ConSigneesAddressFra();
        mFragments.add(shipperAddressFra);
        mFragments.add(conSigneesAddressFra);
        initView();
        return view;
    }

    private void init() {
        mActivity = (MyActivity) getActivity();
        TopMenu topMenu = mActivity.getTopMenu();
        topMenu.setTitle(R.string.address_book);
        topMenu.setLeftIcon(R.drawable.selector_menu_back);
        topMenu.setRightIcon(R.drawable.selector_add_address);
        topMenu.setLeftIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivity.finish();
            }
        });
        topMenu.setRightIconOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                switchAddAddress();
            }
        });
    }


    /*
     * 添加地址
     */
    private void switchAddAddress() {
        int                 currentItem = mViewpager.getCurrentItem();
        FragmentManager     fm          = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        AddAddressFra       fra         = (AddAddressFra) fm.findFragmentByTag(TAG_ADD_ADDRESS);
        if (fra == null) {
            fra = new AddAddressFra();
        }
        //添加参数
        Bundle args = new Bundle();
        args.putInt(Constants.TAG, currentItem);
        fra.setArguments(args);
        transaction.replace(R.id.my_container, fra, TAG_ADD_ADDRESS);
        transaction.addToBackStack(TAG_ADD_ADDRESS);
        transaction.commit();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);

    }

    private void initView() {
        mAdapter = new MyFragmentPageAdapter(getChildFragmentManager());
        mViewpager.setAdapter(mAdapter);
        mTabs.setViewPager(mViewpager);

    }

    private class MyFragmentPageAdapter
            extends FragmentPagerAdapter
    {

        public MyFragmentPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if(mFragments != null){
                return mFragments.get(position);
            }
            return null;
        }

        @Override
        public int getCount() {
            if(mFragments != null){
                return mFragments.size();
            }
            return 0;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTabTitles[position];
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
       for(int i =0; i < mFragments.size();i++){
           mFragments.get(i).onActivityResult(requestCode,resultCode,data);
       }
    }
}
