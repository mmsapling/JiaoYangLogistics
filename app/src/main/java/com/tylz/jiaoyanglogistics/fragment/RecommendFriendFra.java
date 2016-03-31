package com.tylz.jiaoyanglogistics.fragment;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.tylz.jiaoyanglogistics.R;
import com.tylz.jiaoyanglogistics.activity.MyActivity;
import com.tylz.jiaoyanglogistics.base.BaseFragment;
import com.tylz.jiaoyanglogistics.model.ContactsInfo;
import com.tylz.jiaoyanglogistics.util.CharacterParser;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author tylz
 * @time 2016/3/25 0025 17:14
 * @des 推荐给好友
 *
 * @updateAuthor
 * @updateDate 2016/3/25 0025
 * @updateDes
 */
public class RecommendFriendFra
        extends BaseFragment
{


    @Bind(R.id._bt_linkman)
    Button   mBtLinkman;
    @Bind(R.id._et_mobile)
    EditText mEtMobile;
    @Bind(R.id._bt_invitation)
    Button   mBtInvitation;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = mLayoutInflater.inflate(R.layout.fra_recommend_friend, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id._bt_linkman,
              R.id._bt_invitation})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id._bt_linkman:
                //打开手机联系人
                MyActivity activity = (MyActivity) mContext;
                activity.switchFriendNumber();
                break;
            case R.id._bt_invitation:
                break;
        }
    }


}