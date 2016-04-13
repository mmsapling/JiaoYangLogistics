package com.tylz.jiaoyanglogistics.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.tylz.jiaoyanglogistics.R;
import com.tylz.jiaoyanglogistics.base.BaseFragmentActivity;
import com.tylz.jiaoyanglogistics.conf.Constants;
import com.tylz.jiaoyanglogistics.fragment.LoginFra;
import com.tylz.jiaoyanglogistics.fragment.RePwdFra;
import com.tylz.jiaoyanglogistics.fragment.RegistFra;
import com.tylz.jiaoyanglogistics.util.UIUtils;
import com.tylz.jiaoyanglogistics.view.TopMenu;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author tylz
 * @time 2016/3/23 0023 14:29
 * @des 和登陆有关的操作
 *
 * @updateAuthor
 * @updateDate 2016/3/23 0023
 * @updateDes
 */
public class LoginActivity
        extends BaseFragmentActivity
{
    public static final String TAG_LOGIN  = "login";
    public static final String TAG_REGIST = "regist";
    public static final String TAG_REPWD  = "repwd";
    @Bind(R.id.login_menu)
    TopMenu mLoginMenu;
    private boolean isLogin; //是否登陆 true 登陆， false 注册
    private String isShowing = TAG_LOGIN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initView();
    }


    private void initView() {
        switchLogin();
    }

    /**
     * 切换登陆
     */
    public void switchLogin() {
        isShowing = TAG_LOGIN;
        mLoginMenu.setTitle(UIUtils.getString(R.string.login));
        mLoginMenu.hideLeftView();
        FragmentManager     fm          = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        LoginFra            fragment    = (LoginFra) fm.findFragmentByTag(TAG_LOGIN);
        if (fragment == null) {
            fragment = new LoginFra();
        }
        transaction.replace(R.id.container_login, fragment, TAG_LOGIN);
        transaction.addToBackStack(TAG_LOGIN);
        transaction.commit();
    }

    /**
     * 切换注册
     */
    public void switchRegist() {
        isShowing = TAG_REGIST;
        mLoginMenu.setTitle(UIUtils.getString(R.string.regist));
        mLoginMenu.setLeftIcon(R.drawable.selector_menu_back);
        mLoginMenu.setLeftIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchLogin();
            }
        });
        FragmentManager fm       = getSupportFragmentManager();
        RegistFra       fragment = (RegistFra) fm.findFragmentByTag(TAG_REGIST);
        if (fragment == null) {
            fragment = new RegistFra();
        }
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.container_login, fragment, TAG_REGIST);
        transaction.addToBackStack(TAG_REGIST);
        transaction.commit();
    }

    /**
     * 切换忘记密码
     */
    public void switchRePwd() {
        isShowing = TAG_REPWD;
        mLoginMenu.setTitle(UIUtils.getString(R.string.title_repwd));
        mLoginMenu.setLeftIcon(R.drawable.selector_menu_back);
        mLoginMenu.setLeftIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchLogin();
            }
        });
        FragmentManager fm       = getSupportFragmentManager();
        RePwdFra        fragment = (RePwdFra) fm.findFragmentByTag(TAG_REPWD);
        if (fragment == null) {
            fragment = new RePwdFra();
        }
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.container_login, fragment, TAG_REPWD);
        transaction.addToBackStack(TAG_REPWD);
        transaction.commit();
    }

    /**
     * 根据不同的fragment返回不同的界面
     */
    @Override
    public void onBackPressed() {
        switch (isShowing) {
            case TAG_LOGIN:
                backOrfinish();
                break;
            case TAG_REGIST:
                switchLogin();
                break;
            case TAG_REPWD:
                switchLogin();
                break;
            default:
                finish();
                break;
        }

    }

    /**
     * 判断到底是销毁当前界面还是开启主页面
     */
    private void backOrfinish() {
        boolean isLogin = mSPUtils.getBoolean(Constants.IS_LOGIN, false);
        if (isLogin) {
            //如果登陆了，就返回
            finish();
        } else {
            //没有登陆 跳到首页并销毁当前页面
            Intent intent = new Intent(this, MainUserActivity.class);
            startActivity(intent);
        }
    }
}
