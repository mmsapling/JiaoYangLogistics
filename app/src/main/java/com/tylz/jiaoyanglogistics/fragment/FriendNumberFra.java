package com.tylz.jiaoyanglogistics.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.tylz.jiaoyanglogistics.R;
import com.tylz.jiaoyanglogistics.adapter.FriendNumberAdapter;
import com.tylz.jiaoyanglogistics.base.BaseFragment;
import com.tylz.jiaoyanglogistics.factory.ThreadPoolProxyFactory;
import com.tylz.jiaoyanglogistics.model.ContactsInfo;
import com.tylz.jiaoyanglogistics.util.CommonUitls;
import com.tylz.jiaoyanglogistics.util.LogUtils;
import com.tylz.jiaoyanglogistics.view.CustomEditText;
import com.tylz.jiaoyanglogistics.view.SideBar;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author tylz
 * @time 2016/3/25 0025 15:25
 * @des 联系人界面
 *
 * @updateAuthor
 * @updateDate 2016/3/25 0025
 * @updateDes
 */
public class FriendNumberFra
        extends BaseFragment
{
    private static final int WHAT_LOAD_SUCCESS = 100;
    @Bind(R.id._et_search)
    CustomEditText mEtSearch;
    @Bind(R.id._list_view)
    ListView       mListView;
    @Bind(R.id._side_bar)
    SideBar        mSideBar;
    @Bind(R.id._tv_dialog)
    TextView       mTvDialog;
    private List<ContactsInfo>  mDatas;
    private FriendNumberAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = mLayoutInflater.inflate(R.layout.fra_friend_number, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        showProgress();
        ThreadPoolProxyFactory.createNormalThreadPoolProxy()
                              .execute(new LoadDataTask(mContext));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private class LoadDataTask
            implements Runnable
    {
        private Context mContext;

        public LoadDataTask(Context context) {
            mContext = context;
        }

        @Override
        public void run() {
            List<ContactsInfo> phoneContacts = CommonUitls.getPhoneContacts(mContext);
            LogUtils.d("data.size=" + phoneContacts.size());
            Message msg = Message.obtain();
            msg.obj = phoneContacts;
            msg.what = WHAT_LOAD_SUCCESS;
            mHandler.sendMessage(msg);
        }
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case WHAT_LOAD_SUCCESS:
                    closeProgress();
                    mDatas = (List<ContactsInfo>) msg.obj;
                    LogUtils.d("data.size=" + mDatas.size());
                    processData(mDatas);
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * 处理联系人数据
     * @param datas 联系人信息
     */
    private void processData(List<ContactsInfo> datas) {
        for (int i = 0; i < datas.size(); i++) {
            LogUtils.d(datas.get(i).name + datas.get(i).mobile);
        }
        //给listview 设置适配器
        mAdapter = new FriendNumberAdapter(mContext, datas);
        mListView.setAdapter(mAdapter);
    }

}
