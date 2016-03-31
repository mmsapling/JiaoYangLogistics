package com.tylz.jiaoyanglogistics.base;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.tylz.jiaoyanglogistics.R;

/**
 * @author tylz
 * @time 2016/3/28 0028 10:31
 * @des 上拉加载更多Holder
 *
 * @updateAuthor
 * @updateDate 2016/3/28 0028
 * @updateDes
 */
public class RefreshItemHolder{
    public static final int LOADMORE_LOADING = 0;
    public static final int LOADMORE_ERROR = 1;
    public static final int LOADMORE_NONE = 2;
    private final Activity       mContext;
    private final LayoutInflater mLayoutInflater;
    private final ViewGroup      mParent;
    private final View           mRootView;
    private       LinearLayout   mContainerLoadMore;
    private       LinearLayout   mContainerReload;

    public RefreshItemHolder(Activity activity, ViewGroup parent) {
        mContext = activity;
        mLayoutInflater = LayoutInflater.from(activity);
        mParent = parent;
        mRootView = initView();
    }

    private View initView() {
        View rootView = mLayoutInflater.inflate(R.layout.item_loadmore, mParent, false);
        mContainerLoadMore = (LinearLayout) rootView.findViewById(R.id.item_container_loading);
        mContainerReload = (LinearLayout) rootView.findViewById(R.id.item_container_reload);

        return rootView;
    }

    public View getRootView() {
        return mRootView;
    }
    public void loadMore(int status){
        //隐藏两个视图
        mContainerLoadMore.setVisibility(View.GONE);
        mContainerReload.setVisibility(View.GONE);
        switch (status){
            case LOADMORE_LOADING:
                mContainerLoadMore.setVisibility(View.VISIBLE);
                break;
            case LOADMORE_ERROR:
                mContainerReload.setVisibility(View.VISIBLE);
                break;
            case LOADMORE_NONE:
                break;
        }
    }
}
