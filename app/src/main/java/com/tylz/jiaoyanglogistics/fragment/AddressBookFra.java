package com.tylz.jiaoyanglogistics.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.astuetz.LazyViewPager;
import com.astuetz.PagerSlidingTabStripExtends;
import com.tylz.jiaoyanglogistics.R;
import com.tylz.jiaoyanglogistics.activity.MyActivity;
import com.tylz.jiaoyanglogistics.base.BaseFragment;
import com.tylz.jiaoyanglogistics.conf.Constants;
import com.tylz.jiaoyanglogistics.factory.FragmentFactory;
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

    private static final String TAG_ADD_ADDRESS = "add_address";
    @Bind(R.id.tabs)
    PagerSlidingTabStripExtends mTabs;
    @Bind(R.id.viewpager)
    LazyViewPager               mViewpager;
    @Bind(R.id._container_address)
    LinearLayout                mContainerAddress;
    private FragmentStatePagerAdapter mAdapter;
    private String[] mTabTitles = UIUtils.getStrings(R.array.address_books);
    private MyActivity mActivity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = View.inflate(mContext, R.layout.fra_address_book, null);
        ButterKnife.bind(this, view);
        init();
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
        mAdapter = new FragmentStatePagerAdapter(getChildFragmentManager()) {
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
            public int getItemPosition(Object object) {
                return PagerAdapter.POSITION_NONE;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return mTabTitles[position];
            }
        };
        mViewpager.setAdapter(mAdapter);
        mTabs.setLazyViewPager(mViewpager);

    }
}
