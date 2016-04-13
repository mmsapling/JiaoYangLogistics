package com.tylz.jiaoyanglogistics.fragment;

import android.content.Intent;
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
import com.tylz.jiaoyanglogistics.activity.NewsDetailActivity;
import com.tylz.jiaoyanglogistics.adapter.NewsInfoAdapter;
import com.tylz.jiaoyanglogistics.base.BaseFragment;
import com.tylz.jiaoyanglogistics.conf.Constants;
import com.tylz.jiaoyanglogistics.conf.ItemClickListener;
import com.tylz.jiaoyanglogistics.conf.NetManager;
import com.tylz.jiaoyanglogistics.model.News;
import com.tylz.jiaoyanglogistics.view.TopMenu;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.DCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;

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
        implements SwipeRefreshLayout.OnRefreshListener, ItemClickListener
{
    @Bind(R.id._top_menu)
    TopMenu             mTopMenu;
    @Bind(R.id._recycler_view)
    LoadMoreRecylerView mRecyclerView;
    @Bind(R.id._swipe_refresh)
    SwipeRefreshLayout  mSwipeRefresh;

    private List<News.NewsInfo> mDatas = new ArrayList<News.NewsInfo>();
    private NewsInfoAdapter mAdapter;
    private int mStart  = 0;
    private int mLenght = Constants.SIZE_PAGE;


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
        showProgress();
        refreshOrLoadMore(mStart, mLenght, true);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new NewsInfoAdapter(mContext, mDatas);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                mRecyclerView.onRefreshComplete();
                if (mSwipeRefresh.isRefreshing()) {

                    return;
                }
                if (mDatas.size() < mLenght) {
                    refreshOrLoadMore(mStart, mLenght, false);
                } else {
                    mStart = mStart + mLenght;
                    refreshOrLoadMore(mStart, mLenght, true);
                }

            }

            @Override
            public void onReload() {
                //网络连接失败时的重新加载
            }
        });
    }

    private void initListener() {
        mSwipeRefresh.setColorSchemeColors(R.color.onange, R.color.green, R.color.blue);
        mSwipeRefresh.setOnRefreshListener(this);
        mAdapter.setOnItemClickListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


    @Override
    public void onRefresh() {
        mSwipeRefresh.setRefreshing(true);

        refreshOrLoadMore(0, mStart, false);
    }

    /**
     * 刷新或者加载更多
     * @param startIndex 起始位置
     * @param pageSize 数据个数
     * @param isLoadMore true表示加载更多
     */
    private void refreshOrLoadMore(int startIndex, int pageSize, final boolean isLoadMore) {
        String start  = String.valueOf(startIndex);
        String length = String.valueOf(pageSize);
        OkHttpUtils.post()
                   .url(NetManager.User.NEWS_LIST)
                   .addParams(NetManager.START, start)
                   .addParams(NetManager.LENGTH, length)
                   .build()
                   .execute(new DCallback<News>() {
                       @Override
                       public void onError(Call call, Exception e) {
                           connectError();
                           mSwipeRefresh.setRefreshing(false);
                       }

                       @Override
                       public void onResponse(News response) {
                           if (isSuccess(response)) {
                               if (!isLoadMore) {
                                   mDatas = response.list;
                               } else {
                                   for (News.NewsInfo info : response.list) {
                                       mDatas.add(info);
                                   }
                               }
                               mAdapter = new NewsInfoAdapter(mContext, mDatas);
                               mRecyclerView.setAdapter(mAdapter);
                               mAdapter.setOnItemClickListener(TabMsgFra.this);
                           }
                           mSwipeRefresh.setRefreshing(false);
                       }
                   });
    }

    @Override
    public void onItemClick(View v) {
        News.NewsInfo info = (News.NewsInfo) v.getTag();
        Intent intent = new Intent(mContext, NewsDetailActivity.class);
        intent.putExtra(Constants.TAG,  info);
        startActivity(intent);
    }
}
