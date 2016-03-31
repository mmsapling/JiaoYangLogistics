package com.tylz.jiaoyanglogistics.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;

import com.tylz.jiaoyanglogistics.util.SPUtils;
import com.tylz.jiaoyanglogistics.view.DProgressDialog;

/**
 * @author tylz
 * @time 2016/3/18 0018 15:02
 * @des 所有Fragment的基类，保存一些公共方法和属性
 * @updateAuthor tylz
 * @updateDate 2016/3/18 0018
 * @updateDes
 */

public abstract class BaseFragment
        extends Fragment
{
    public  SPUtils         mSpUtils;
    public  Activity        mContext;
    public  LayoutInflater  mLayoutInflater;
    private DProgressDialog mProgressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSpUtils = new SPUtils(getActivity());
        mContext = getActivity();
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    public void showProgress() {
        mProgressDialog = new DProgressDialog(mContext);
        mProgressDialog.show();
    }

    public void closeProgress() {
        if(mProgressDialog != null){
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void onDestroyView() {
        closeProgress();
        super.onDestroyView();

    }
}
