package com.tylz.jiaoyanglogistics.fragment;

import android.content.Intent;
import android.os.Bundle;
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
import com.tylz.jiaoyanglogistics.conf.NetManager;
import com.tylz.jiaoyanglogistics.model.Address;
import com.tylz.jiaoyanglogistics.model.AddressListModel;
import com.tylz.jiaoyanglogistics.model.AddressModel;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.DCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;

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
    public static final int REQUESTCODE_CONSIGNE_EDIT = 2001;
    @Bind(R.id._recycler_view)
    EasyRecylerView    mRecyclerView;
    @Bind(R.id._swipe_refresh)
    SwipeRefreshLayout mSwipeRefresh;
    private List<Address> mDatas = new ArrayList<Address>();
    AddressInfoAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = mLayoutInflater.inflate(R.layout.fra_consigness_adress, container, false);
        ButterKnife.bind(this, view);
        setRecyclerView();
        return view;
    }
    @Override
    public void onResume() {

        super.onResume();
        loadData();
    }

    private void loadData() {
        OkHttpUtils.post()
                   .url(NetManager.User.GET_RECEIPT_ADDRESS_LIST)
                   .addParams(NetManager.TOKEN, mUser.token)
                   .addParams(NetManager.MOBILE, mUser.mobile)
                   .build()
                   .execute(new DCallback<AddressListModel>() {
                       @Override
                       public void onError(Call call, Exception e) {
                           if(mSwipeRefresh.isRefreshing()){
                               mSwipeRefresh.setRefreshing(false);
                           }
                           connectError();
                       }

                       @Override
                       public void onResponse(AddressListModel response) {
                           if (isSuccess(response)) {
                               mSpUtils.putString(Constants.IS_ADDABLE_RECEIVE_ADDRESS,
                                                  response.isAddable);
                               mDatas.clear();
                               mDatas.addAll(response.list);

                               if (mDatas.size() > 0 && mDatas != null) {
                                   mAdapter.notifyDataSetChanged();
                               }
                               if(mSwipeRefresh.isRefreshing()){
                                    mSwipeRefresh.setRefreshing(false);
                               }
                           }
                       }
                   });
    }

    private void setRecyclerView() {
        mSwipeRefresh.setColorSchemeColors(R.color.red, R.color.green, R.color.blue);
        mSwipeRefresh.setOnRefreshListener(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new AddressInfoAdapter(mContext, mDatas,true);
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


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onRefresh() {
        loadData();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //这里的requestCode 和 请求的requestCode 不一致，不知道为什么，但是结果码是一致的
        if (resultCode == EditAddressActivity.RESULT_CODE_EDIT) {
            if (data != null) {
                AddressModel address = (AddressModel) data.getSerializableExtra(Constants.TAG);
                mDatas.set(address.address.postion, address.address);
                mAdapter.notifyDataSetChanged();
            }
        }
    }
}
