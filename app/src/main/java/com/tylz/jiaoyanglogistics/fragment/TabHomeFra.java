package com.tylz.jiaoyanglogistics.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tylz.jiaoyanglogistics.R;
import com.tylz.jiaoyanglogistics.activity.MainUserActivity;
import com.tylz.jiaoyanglogistics.base.BaseFragment;

/**
 * @author tylz
 * @time 2016/3/20 0020 16:48
 * @des 首页
 * @updateAuthor tylz
 * @updateDate 2016/3/20 0020
 * @updateDes 首页
 */
public class TabHomeFra
        extends BaseFragment
{

    public static final String TAG_ONLINE_ORDER = "online_order";
    public static final String TAG_USER_HOME = "user_home";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = (MainUserActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState)
    {

        View view = View.inflate(getActivity(), R.layout.fra_user_home, null);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        switchTab(TAG_USER_HOME);
    }

    public void switchTab(String tag) {
        Fragment fragment = null;
        switch (tag){
            case TAG_ONLINE_ORDER:
                fragment = new OnlineOrderFra();
                break;
            case TAG_USER_HOME:
                fragment = new TabHomeInfoFra();
                break;
        }
        FragmentManager     fm          = getChildFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.user_home_container, fragment, tag);
        transaction.commit();
    }
}
