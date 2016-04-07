package com.tylz.jiaoyanglogistics.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hellosliu.easyrecyclerview.EasyRecylerView;
import com.tylz.jiaoyanglogistics.R;
import com.tylz.jiaoyanglogistics.base.BaseFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author tylz
 * @time 2016/4/5 0005 16:49
 * @des 兑换记录
 *
 * @updateAuthor
 * @updateDate 2016/4/5 0005
 * @updateDes
 */
public class ExchangeRecorderFra
        extends BaseFragment
{
    @Bind(R.id._tv_avaible_score)
    TextView           mTvAvaibleScore;
    @Bind(R.id._recycler_view)
    EasyRecylerView    mRecyclerView;
    @Bind(R.id._swipe_refresh)
    SwipeRefreshLayout mSwipeRefresh;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = mLayoutInflater.inflate(R.layout.fra_exchange_recorder, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}