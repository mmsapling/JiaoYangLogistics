package com.tylz.jiaoyanglogistics.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hellosliu.easyrecyclerview.EasyRecylerView;
import com.tylz.jiaoyanglogistics.R;
import com.tylz.jiaoyanglogistics.adapter.CenterMsgAdapter;
import com.tylz.jiaoyanglogistics.base.BaseFragment;
import com.tylz.jiaoyanglogistics.factory.ThreadPoolProxyFactory;
import com.tylz.jiaoyanglogistics.model.CenterMsg;
import com.tylz.jiaoyanglogistics.util.UIUtils;
import com.tylz.jiaoyanglogistics.view.DEmptyView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author tylz
 * @time 2016/3/25 0025 15:25
 * @des 站内信
 *
 * @updateAuthor
 * @updateDate 2016/3/25 0025
 * @updateDes
 */
public class CenterMsgFra
        extends BaseFragment
        implements SwipeRefreshLayout.OnRefreshListener
{
    @Bind(R.id._recycler_view)
    EasyRecylerView    mRecyclerView;
    @Bind(R.id._swipe_refresh)
    SwipeRefreshLayout mSwipeRefresh;
    List<CenterMsg> mDatas = new ArrayList<CenterMsg>();
    private CenterMsgAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = mLayoutInflater.inflate(R.layout.fra_center_msg, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setRecyclerView();
    }

    private void setRecyclerView() {
        DEmptyView emptyView = new DEmptyView(mContext);
        mRecyclerView.showEmptyView(emptyView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        addData();
        mAdapter = new CenterMsgAdapter(mContext, mDatas);
        mRecyclerView.setAdapter(mAdapter);

        mSwipeRefresh.setOnRefreshListener(this);
    }

    public void addData() {
        int size = mDatas.size();
        for (int i = 0; i < size + 5; i++){
            CenterMsg msg = new CenterMsg();
            msg.content = UIUtils.getString(R.string.long_temp_text);
            msg.time = System.currentTimeMillis();
            msg.score = "xxoo" + i;
            mDatas.add(msg);
        }
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onRefresh() {
        ThreadPoolProxyFactory.createNormalThreadPoolProxy().execute(new LoadMore());
    }
    private class LoadMore implements Runnable{

        @Override
        public void run() {
            mSwipeRefresh.setRefreshing(true);
            addData();
            mHandler.sendEmptyMessage(0);
        }
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    mSwipeRefresh.setRefreshing(false);
                    mAdapter.notifyDataSetChanged();
                    break;
            }
        }
    };
}
