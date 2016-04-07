package com.tylz.jiaoyanglogistics.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;

import com.tylz.jiaoyanglogistics.R;
import com.tylz.jiaoyanglogistics.base.BaseFragmentActivity;
import com.tylz.jiaoyanglogistics.conf.Constants;
import com.tylz.jiaoyanglogistics.fragment.AddressBookFra;
import com.tylz.jiaoyanglogistics.fragment.CenterMsgFra;
import com.tylz.jiaoyanglogistics.fragment.FriendNumberFra;
import com.tylz.jiaoyanglogistics.fragment.MyApproveFra;
import com.tylz.jiaoyanglogistics.fragment.MyPointFra;
import com.tylz.jiaoyanglogistics.fragment.RecommendFriendFra;
import com.tylz.jiaoyanglogistics.view.TopMenu;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author tylz
 * @time 2016/3/24 0024 17:16
 * @des 我的主界面
 *
 * @updateAuthor
 * @updateDate 2016/3/24 0024
 * @updateDes
 */
public class MyActivity
        extends BaseFragmentActivity
{
    public static final  String TAG_OWNER_APPROVE    = "owner_approve";
    public static final  String TAG_ADDRESS_BOOK     = "address_book";
    public static final  String TAG_MY_POINT         = "my_point";
    public static final  String TAG_RECOMMEND_FRIEND = "recommend_friend";
    public static final  String TAG_CENTER_MSG       = "center_msg";
    private static final String TAG_FRIEND_NUMBER    = "friend_number";
    @Bind(R.id.my_top_menu)
    TopMenu     mTopMenu;
    @Bind(R.id.my_container)
    FrameLayout mContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        ButterKnife.bind(this);
        String tag = getIntent().getStringExtra(Constants.TAG);
        initView(tag);
    }

    private void initView(String tag) {
        switch (tag) {
            case TAG_OWNER_APPROVE:
                switchOwnerApprove();
                break;
            case TAG_ADDRESS_BOOK:
                switchAddressBook();
                break;
            case TAG_MY_POINT:
                switchMyPoint();
                break;
            case TAG_CENTER_MSG:
                switchCenterMsg();
                break;
            case TAG_RECOMMEND_FRIEND:
                switchRecommendFriend();
                break;
        }

    }

    /**
     * 切换站内信
     */
    private void switchCenterMsg() {
        mTopMenu.setTitle(R.string.msg_center);
        mTopMenu.setLeftIcon(R.drawable.selector_menu_back);
        mTopMenu.setLeftIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        FragmentManager     fm          = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        CenterMsgFra        fra         = (CenterMsgFra) fm.findFragmentByTag(TAG_CENTER_MSG);
        if (fra == null) {
            fra = new CenterMsgFra();
        }
        transaction.replace(R.id.my_container, fra, TAG_CENTER_MSG);
        transaction.commit();
    }

    /**
     * 推荐给好友
     */
    private void switchRecommendFriend() {
        mTopMenu.setTitle(R.string.recomment_friend);
        mTopMenu.setLeftIcon(R.drawable.selector_menu_back);
        mTopMenu.setLeftIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        FragmentManager     fm          = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        RecommendFriendFra  fra         = (RecommendFriendFra) fm.findFragmentByTag(
                TAG_RECOMMEND_FRIEND);
        if (fra == null) {
            fra = new RecommendFriendFra();
        }
        transaction.replace(R.id.my_container, fra, TAG_RECOMMEND_FRIEND);
        transaction.commit();
    }

    /**
     * 切换我的积分
     */
    private void switchMyPoint() {
        mTopMenu.setTitle(R.string.my_points);
        mTopMenu.setLeftIcon(R.drawable.selector_menu_back);
        mTopMenu.setLeftIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        FragmentManager     fm          = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        MyPointFra          fra         = (MyPointFra) fm.findFragmentByTag(TAG_MY_POINT);
        if (fra == null) {
            fra = new MyPointFra();
        }
        transaction.replace(R.id.my_container, fra, TAG_MY_POINT);
        transaction.commit();
    }

    /**
     * 切换地址薄
     */
    private void switchAddressBook() {
        mTopMenu.setTitle(R.string.address_book);
        mTopMenu.setLeftIcon(R.drawable.selector_menu_back);
        mTopMenu.setRightIcon(R.drawable.selector_add_address);
        mTopMenu.setLeftIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mTopMenu.setRightIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 2016/3/25 0025
            }
        });
        FragmentManager     fm          = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        AddressBookFra      fra         = (AddressBookFra) fm.findFragmentByTag(TAG_ADDRESS_BOOK);
        if (fra == null) {
            fra = new AddressBookFra();
        }
        transaction.replace(R.id.my_container, fra, TAG_ADDRESS_BOOK);
        transaction.commit();
    }

    /**
     * 切换我的认证
     */
    private void switchOwnerApprove() {
        mTopMenu.setTitle(R.string.my_approve);
        mTopMenu.setLeftIcon(R.drawable.selector_menu_back);
        mTopMenu.setLeftIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        FragmentManager     fm          = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        MyApproveFra        fra         = (MyApproveFra) fm.findFragmentByTag(TAG_OWNER_APPROVE);
        if (fra == null) {
            fra = new MyApproveFra();
        }
        transaction.replace(R.id.my_container, fra, TAG_OWNER_APPROVE);
        transaction.commit();
    }

    /**
     * 切换到联系人
     */
    public void switchFriendNumber() {
        final FragmentManager fm = getSupportFragmentManager();
        mTopMenu.setTitle(R.string.friend_list);
        mTopMenu.setLeftIcon(R.drawable.selector_menu_back);
        mTopMenu.setLeftIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               back();
            }
        });

        FragmentTransaction transaction = fm.beginTransaction();
        FriendNumberFra     fra         = (FriendNumberFra) fm.findFragmentByTag(TAG_FRIEND_NUMBER);
        if (fra == null) {
            fra = new FriendNumberFra();
        }
        transaction.replace(R.id.my_container, fra, TAG_FRIEND_NUMBER);
        transaction.addToBackStack(TAG_FRIEND_NUMBER);
        transaction.commit();
    }

    public TopMenu getTopMenu() {
        return mTopMenu;
    }

    @Override
    public void onBackPressed() {
        back();
    }

    private void back() {
        FragmentManager fm = getSupportFragmentManager();

        int count = fm.getBackStackEntryCount();
        if (count > 0) {
            FragmentManager.BackStackEntry entryat = fm.getBackStackEntryAt(count - 1);
            if (entryat.getName()
                       .equals(TAG_FRIEND_NUMBER))
            {
                mTopMenu.setTitle(R.string.recomment_friend);
            }
            fm.popBackStack();

        } else {
            finish();
        }
    }
}
