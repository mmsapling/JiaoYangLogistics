package com.tylz.jiaoyanglogistics.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tylz.jiaoyanglogistics.util.SPUtils;

import java.lang.ref.WeakReference;

/**
 * @author tylz
 * @time 2016/3/24 0024 13:39
 * @des 可以缓存的fragment
 *
 * @updateAuthor
 * @updateDate 2016/3/24 0024
 * @updateDes
 */
public abstract class BaseCahceFragment
        extends Fragment
{
    public SPUtils             mSpUtils;
    public Activity            mContext;
    public WeakReference<View> mRootView;
    private View               mView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSpUtils = new SPUtils(getActivity());
        mContext = getActivity();
        mView = new View(mContext);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState)
    {

        if (mRootView == null || mRootView.get() == null) {
            mView = initRootView();
            mRootView = new WeakReference<View>(mView);
        } else {
            mView = mRootView.get();

        }
        return mView;
    }

    public abstract View initRootView();
}