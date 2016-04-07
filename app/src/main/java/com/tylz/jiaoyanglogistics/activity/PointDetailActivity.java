package com.tylz.jiaoyanglogistics.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.hellosliu.easyrecyclerview.EasyRecylerView;
import com.tylz.jiaoyanglogistics.R;
import com.tylz.jiaoyanglogistics.adapter.PointRecorderAdapter;
import com.tylz.jiaoyanglogistics.base.BaseActivity;
import com.tylz.jiaoyanglogistics.factory.ThreadPoolProxyFactory;
import com.tylz.jiaoyanglogistics.model.PointRecorderInfo;
import com.tylz.jiaoyanglogistics.util.UIUtils;
import com.tylz.jiaoyanglogistics.view.TopMenu;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author tylz
 * @time 2016/4/5 0005 17:03
 * @des 兑换记录
 *
 * @updateAuthor
 * @updateDate 2016/4/5 0005
 * @updateDes
 */
public class PointDetailActivity
        extends BaseActivity
        implements SwipeRefreshLayout.OnRefreshListener
{
    @Bind(R.id._tv_avaible_score)
    TextView           mTvAvaibleScore;
    @Bind(R.id._recycler_view)
    EasyRecylerView    mRecyclerView;
    @Bind(R.id._swipe_refresh)
    SwipeRefreshLayout mSwipeRefresh;
    @Bind(R.id._top_menu)
    TopMenu            mTopMenu;
    List<PointRecorderInfo> mDataSource = new ArrayList<>();
    private PointRecorderAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exchange_recorder);
        ButterKnife.bind(this);
        initView();
        setRecyclerView();
    }

    private void setRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        addData();
        mAdapter = new PointRecorderAdapter(this,mDataSource);
        mRecyclerView.setAdapter(mAdapter);
        mSwipeRefresh.setOnRefreshListener(this);
    }

    private void initView() {
        mTopMenu.setTitle(R.string.exchange_recorder);
        mTopMenu.setLeftIcon(R.drawable.selector_menu_back);
        mTopMenu.setLeftIconOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mTopMenu.setRightText(R.string.exchange);
    }

    private void addData() {
        int size = mDataSource.size();
        for (int i = 0; i < size + 5; i++) {
            PointRecorderInfo info = new PointRecorderInfo();
            info.content = UIUtils.getString(R.string.short_temp_text);
            info.time = new Date().getTime();
            mDataSource.add(info);
        }
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
            switch (msg.what) {
                case 0:
                    mSwipeRefresh.setRefreshing(false);
                    mAdapter.notifyDataSetChanged();
                    break;
            }
        }
    };
}
