package com.tylz.jiaoyanglogistics.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tylz.jiaoyanglogistics.R;
import com.tylz.jiaoyanglogistics.activity.MyActivity;
import com.tylz.jiaoyanglogistics.activity.SettingActivity;
import com.tylz.jiaoyanglogistics.base.BaseFragment;
import com.tylz.jiaoyanglogistics.conf.Constants;
import com.tylz.jiaoyanglogistics.view.CircleImageView;
import com.tylz.jiaoyanglogistics.view.ItemView;
import com.tylz.jiaoyanglogistics.view.TopMenu;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author tylz
 * @time 2016/3/20 0020 16:48
 * @des 我的
 * @updateAuthor tylz
 * @updateDate 2016/3/20 0020
 * @updateDes 我的
 */
public class TabMyFra
        extends BaseFragment
{
    @Bind(R.id.tv_name)
    TextView mTvName;
    @Bind(R.id.tv_account_name)
    TextView mTvAccountName;
    @Bind(R.id.tv_mobile)
    TextView mTvMobile;

    @Bind(R.id.my_top_menu)
    TopMenu         mTopMenu;
    @Bind(R.id.civ_head_icon)
    CircleImageView mCivHeadIcon;
    @Bind(R.id.my_owner_approve)
    ItemView        mOwnerApprove;
    @Bind(R.id.my_address_book)
    ItemView        mAddressBook;
    @Bind(R.id.my_point)
    ItemView        mPoint;
    @Bind(R.id.my_remmcond_friend)
    ItemView        mRemmcondFriend;
    @Bind(R.id.my_center_msg)
    ItemView        mCenterMsg;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = View.inflate(mContext, R.layout.fra_my, null);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        mTopMenu.setTitle(R.string.user_center);
        mTopMenu.setRightIcon(R.drawable.selector_setting);
        mTopMenu.setRightIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //去设置界面
                Intent intent = new Intent(mContext, SettingActivity.class);
                startActivity(intent);
            }
        });
        //初始化数据
        mOwnerApprove.setIcon(R.mipmap.user_certain);
        mOwnerApprove.setText(R.string.owner_approve);
        mAddressBook.setIcon(R.mipmap.address);
        mAddressBook.setText(R.string.address_book);
        mPoint.setIcon(R.mipmap.grade);
        mPoint.setText(R.string.my_points);
        mRemmcondFriend.setIcon(R.mipmap.to_friends);
        mRemmcondFriend.setText(R.string.recomment_friend);
        mCenterMsg.setIcon(R.mipmap.letter);
        mCenterMsg.setText(R.string.msg_center);
        mCenterMsg.isShowLine(false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


    @OnClick({R.id.civ_head_icon,
              R.id.my_owner_approve,
              R.id.my_address_book,
              R.id.my_point,
              R.id.my_remmcond_friend,
              R.id.my_center_msg})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.civ_head_icon:
                break;
            case R.id.my_owner_approve:
                startMyActivity(MyActivity.TAG_OWNER_APPROVE);
                break;
            case R.id.my_address_book:
                startMyActivity(MyActivity.TAG_ADDRESS_BOOK);
                break;
            case R.id.my_point:
                startMyActivity(MyActivity.TAG_MY_POINT);
                break;
            case R.id.my_remmcond_friend:
                startMyActivity(MyActivity.TAG_RECOMMEND_FRIEND);
                break;
            case R.id.my_center_msg:
                startMyActivity(MyActivity.TAG_CENTER_MSG);
                break;
        }
    }

    /**
     * 根据tag的不一样，开启不同的界面
     * @param tag fragment标签
     */
    private void startMyActivity(String tag) {
        Intent intent = new Intent(mContext, MyActivity.class);
        intent.putExtra(Constants.TAG, tag);
        startActivity(intent);
    }

}
