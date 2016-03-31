package com.tylz.jiaoyanglogistics.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hellosliu.easyrecyclerview.EasyRecylerView;
import com.tylz.jiaoyanglogistics.R;
import com.tylz.jiaoyanglogistics.activity.EditAddressActivity;
import com.tylz.jiaoyanglogistics.adapter.AddressInfoAdapter;
import com.tylz.jiaoyanglogistics.base.BaseFragment;
import com.tylz.jiaoyanglogistics.conf.Constants;
import com.tylz.jiaoyanglogistics.conf.ItemClickListener;
import com.tylz.jiaoyanglogistics.factory.ThreadPoolProxyFactory;
import com.tylz.jiaoyanglogistics.manager.ThreadPoolProxy;
import com.tylz.jiaoyanglogistics.model.Address;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author tylz
 * @time 2016/3/25 0025 15:25
 * @des 收货人地址
 *
 * @updateAuthor
 * @updateDate 2016/3/25 0025
 * @updateDes
 */
public class ConSigneesAddressFra
        extends BaseFragment
        implements SwipeRefreshLayout.OnRefreshListener
{
    public static final int REQUESTCODE_CONSIGNE_EDIT = 101;
    @Bind(R.id._recycler_view)
    EasyRecylerView    mRecyclerView;
    @Bind(R.id._swipe_refresh)
    SwipeRefreshLayout mSwipeRefresh;
    private List<Address> mDatas = new ArrayList<Address>();
    private AddressInfoAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = mLayoutInflater.inflate(R.layout.fra_consigness_adress, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setRecyclerView();
    }


    private void setRecyclerView() {
        mSwipeRefresh.setColorSchemeColors(R.color.red, R.color.green, R.color.blue);
        mSwipeRefresh.setOnRefreshListener(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        addDatas(mDatas);
        mAdapter = new AddressInfoAdapter(mContext, mDatas);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setItemClickListner(new ItemClickListener() {
            @Override
            public void onItemClick(View v) {
                Address address = (Address) v.getTag();
                Intent  intent  = new Intent(mContext, EditAddressActivity.class);
                intent.putExtra(Constants.TAG, address);
                startActivityForResult(intent, REQUESTCODE_CONSIGNE_EDIT);
            }
        });
    }

    public void addDatas(List<Address> datas) {
        int size = datas.size();
        for (int i = size; i <= size + 5; i++) {
            Address address = new Address();
            address.address = "宝民一路215号宝通大厦" + i;
            address.name = "小菜皮" + i;
            address.mobile = "电话号码" + i;
            address.area = "广东省深圳市宝安区";
            address.fixMobile = "027-451248888";
            address.isDefault = false;
            if (i == 0) {
                address.isDefault = true;
            }
            mDatas.add(address);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onRefresh() {
        ThreadPoolProxy proxy = ThreadPoolProxyFactory.createNormalThreadPoolProxy();
        proxy.execute(new LoadMore());
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

    private class LoadMore
            implements Runnable
    {

        @Override
        public void run() {
            mSwipeRefresh.setRefreshing(true);
            SystemClock.sleep(2000);
            addDatas(mDatas);
            mHandler.sendEmptyMessage(0);

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUESTCODE_CONSIGNE_EDIT && resultCode == EditAddressActivity.RESULT_CODE_EDIT) {
           if(data != null){
               Address address = (Address) data.getSerializableExtra(Constants.TAG);
               mDatas.set(address.postion,address);
               mAdapter.notifyDataSetChanged();
           }
        }
    }
}
