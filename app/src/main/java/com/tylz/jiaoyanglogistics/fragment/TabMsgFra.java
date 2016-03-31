package com.tylz.jiaoyanglogistics.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hellosliu.easyrecyclerview.LoadMoreRecylerView;
import com.hellosliu.easyrecyclerview.listener.OnRefreshListener;
import com.tylz.jiaoyanglogistics.R;
import com.tylz.jiaoyanglogistics.adapter.NewsInfoAdapter;
import com.tylz.jiaoyanglogistics.adapter.TempAdapter;
import com.tylz.jiaoyanglogistics.base.BaseFragment;
import com.tylz.jiaoyanglogistics.conf.Constants;
import com.tylz.jiaoyanglogistics.util.ToastUtils;
import com.tylz.jiaoyanglogistics.view.TopMenu;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author tylz
 * @time 2016/3/20 0020 16:48
 * @des 消息
 * @updateAuthor tylz
 * @updateDate 2016/3/20 0020
 * @updateDes 消息
 */
public class TabMsgFra
        extends BaseFragment
        implements SwipeRefreshLayout.OnRefreshListener
{
    @Bind(R.id._top_menu)
    TopMenu             mTopMenu;
    @Bind(R.id._recycler_view)
    LoadMoreRecylerView mRecyclerView;
    @Bind(R.id._swipe_refresh)
    SwipeRefreshLayout  mSwipeRefresh;

    private List<String> mDatas = new ArrayList<String>();
    private NewsInfoAdapter mAdapter;
    private TempAdapter     mTempAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = mLayoutInflater.inflate(R.layout.fra_msg, container, false);
        ButterKnife.bind(this, view);
        initView();

        return view;
    }

    private void initView() {
        mTopMenu.setTitle(R.string.news_information);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setRecycleView();
        initListener();
    }

    private void setRecycleView() {
        addDatas(mDatas);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mAdapter = new NewsInfoAdapter(getActivity(), mDatas);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        mRecyclerView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                ToastUtils.makePicTextShortToast(mContext,Constants.ICON_SUCCESS,R.string.tip);
                addDatas(mDatas);
                mAdapter.notifyDataSetChanged();
                mRecyclerView.onRefreshComplete();
            }

            @Override
            public void onReload() {
                //网络连接失败时的重新加载
                ToastUtils.makePicTextShortToast(mContext,Constants.ICON_ERROR,R.string.tip);
                addDatas(mDatas);
                mAdapter.notifyDataSetChanged();
                mRecyclerView.onRefreshComplete();
            }
        });
    }

    private void initListener() {
        mSwipeRefresh.setColorSchemeColors(R.color.onange, R.color.green, R.color.blue);

        mSwipeRefresh.setOnRefreshListener(this);

    }



    private void addDatas(List<String> datas) {
        int size = datas.size();
        for (int i = size; i < size + 5; i++) {
            datas.add(Constants.TAG + i);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


    @Override
    public void onRefresh() {
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {

                addDatas(mDatas);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                mAdapter.notifyDataSetChanged();
                mSwipeRefresh.setRefreshing(false);
            }
        }.execute();

    }
}
