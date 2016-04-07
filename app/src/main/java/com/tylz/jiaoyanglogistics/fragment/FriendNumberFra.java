package com.tylz.jiaoyanglogistics.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.tylz.jiaoyanglogistics.R;
import com.tylz.jiaoyanglogistics.adapter.FriendNumberAdapter;
import com.tylz.jiaoyanglogistics.base.BaseFragment;
import com.tylz.jiaoyanglogistics.conf.Constants;
import com.tylz.jiaoyanglogistics.factory.ThreadPoolProxyFactory;
import com.tylz.jiaoyanglogistics.model.ContactsInfo;
import com.tylz.jiaoyanglogistics.util.CommonUitls;
import com.tylz.jiaoyanglogistics.util.LogUtils;
import com.tylz.jiaoyanglogistics.util.PinyinComparator;
import com.tylz.jiaoyanglogistics.util.ToastUtils;
import com.tylz.jiaoyanglogistics.view.CustomEditText;
import com.tylz.jiaoyanglogistics.view.SideBar;

import java.util.ArrayList;
import java.util.Collections;
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
@SuppressWarnings("unchecked")
public class FriendNumberFra
        extends BaseFragment
        implements TextWatcher, AdapterView.OnItemClickListener
{
    private static final int WHAT_LOAD_SUCCESS = 100;
    private static final int WHAT_LOAD_FAIL    = 101;
    @Bind(R.id._et_search)
    CustomEditText mEtSearch;
    @Bind(R.id._list_view)
    ListView       mListView;
    @Bind(R.id._side_bar)
    SideBar        mSideBar;
    @Bind(R.id._tv_dialog)
    TextView       mTvDialog;
    private List<ContactsInfo> mDatas = new ArrayList<ContactsInfo>();
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
        init();
        showProgress();
        ThreadPoolProxyFactory.createNormalThreadPoolProxy()
                              .execute(new LoadDataTask(mContext));
    }

    private void init() {
        mSideBar.setTextView(mTvDialog);
        mSideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {
            @Override
            public void onTouchingLetterChanged(String s) {
                //该字母首次出现的位置
                int position = mAdapter.getPositionForSection(s.charAt(0));
                if (position != -1) {
                    mListView.setSelection(position);
                }

            }
        });
        mEtSearch.addTextChangedListener(this);
        mListView.setOnItemClickListener(this);
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
            if (phoneContacts != null) {
                LogUtils.d("data.size=" + phoneContacts.size());
                Message msg = Message.obtain();
                msg.obj = phoneContacts;
                msg.what = WHAT_LOAD_SUCCESS;
                mHandler.sendMessage(msg);
            } else {
                mHandler.sendEmptyMessage(WHAT_LOAD_FAIL);
            }

        }
    }

    /**
     * 根据字符过滤数据
     * @param filterStr  过滤的字符
     * @param datas  原始数据
     */
    private void filterData(String filterStr, List<ContactsInfo> datas) {
        List<ContactsInfo> filterDatas = new ArrayList<ContactsInfo>();
        if(TextUtils.isEmpty(filterStr)){
            filterDatas = datas;
        }else{
            filterDatas.clear();
            for(ContactsInfo info : datas){
                String name = info.name;
                String first_pingyin = info.sortLetter;
                if(name.indexOf(filterStr)!= -1 || first_pingyin.indexOf(filterStr) != -1){
                    filterDatas.add(info);
                }
            }
            Collections.sort(filterDatas,new PinyinComparator());
        }
        mAdapter.setList(filterDatas);
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case WHAT_LOAD_SUCCESS:
                    closeProgress();
                    mDatas = (List<ContactsInfo>) msg.obj;
                    processData();
                    break;
                case WHAT_LOAD_FAIL:
                    closeProgress();
                    processData();
                default:
                    break;
            }
        }
    };

    /**
     * 处理联系人数据
     */
    private void processData() {
        Collections.sort(mDatas,new PinyinComparator());

        //给listview 设置适配器
        mAdapter = new FriendNumberAdapter(mContext, mDatas);
        mListView.setAdapter(mAdapter);
    }
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

        filterData(s.toString(), mDatas);
    }


    @Override
    public void afterTextChanged(Editable s) {

    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ContactsInfo info = (ContactsInfo) mAdapter.getItem(position);
        // TODO: 2016/4/1 0001
        ToastUtils.makePicTextShortToast(mContext, Constants.ICON_TIP,"信息=" + info.name);
    }
}
