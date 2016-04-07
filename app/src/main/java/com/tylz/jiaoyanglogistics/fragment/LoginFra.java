package com.tylz.jiaoyanglogistics.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tylz.jiaoyanglogistics.R;
import com.tylz.jiaoyanglogistics.activity.LoginActivity;
import com.tylz.jiaoyanglogistics.activity.MainUserActivity;
import com.tylz.jiaoyanglogistics.base.BaseFragment;
import com.tylz.jiaoyanglogistics.conf.Constants;
import com.tylz.jiaoyanglogistics.conf.NetManager;
import com.tylz.jiaoyanglogistics.model.User;
import com.tylz.jiaoyanglogistics.util.CommonUitls;
import com.tylz.jiaoyanglogistics.util.ToastUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.DCallback;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * @author tylz
 * @time 2016/3/23 0023 15:51
 * @des 登陆
 *
 * @updateAuthor
 * @updateDate 2016/3/23 0023
 * @updateDes
 */
public class LoginFra
        extends BaseFragment
{
    @Bind(R.id.login_et_mobile)
    EditText       mEtMobile;
    @Bind(R.id.login_et_pwd)
    EditText       mEtPwd;
    @Bind(R.id.login_bt_login)
    Button         mBtLogin;
    @Bind(R.id.login_tv_forgetpwd)
    TextView       mTvForgetpwd;
    @Bind(R.id.login_tv_registnow)
    TextView       mTvRegistnow;
    @Bind(R.id.login_container_code)
    RelativeLayout mContainerCode;
    @Bind(R.id.login_line)
    View           mViewLine;
    private LoginActivity mContext;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = (LoginActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = View.inflate(mContext, R.layout.fra_login, null);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        mContainerCode.setVisibility(View.GONE);
        mViewLine.setVisibility(View.GONE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.login_bt_login,
              R.id.login_tv_forgetpwd,
              R.id.login_tv_registnow})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_bt_login:
                login();
                break;
            case R.id.login_tv_forgetpwd:
                mContext.switchRePwd();
                break;
            case R.id.login_tv_registnow:
                mContext.switchRegist();
                break;
        }
    }

    /**
     * 登陆
     */
    private void login() {
        String mobile = mEtMobile.getText()
                                 .toString();
        String pwd = mEtPwd.getText()
                           .toString();

        if (TextUtils.isEmpty(mobile) || TextUtils.isEmpty(pwd)) {
            ToastUtils.makePicTextShortToast(mContext,
                                             Constants.ICON_TIP,
                                             R.string.tip_input_mobile_pwd);
            return;
        }
        showProgress();
        OkHttpUtils.post()
                   .url(NetManager.User.LOGIN)
                   .addParams(NetManager.MOBILE, mobile)
                   .addParams(NetManager.PASSWORD, pwd)
                   .addParams(NetManager.DEVICE_ID, CommonUitls.getDeviceId(mContext))
                   .build()
                   .execute(new DCallback<User>() {
                       @Override
                       public void onError(Call call, Exception e) {
                           connectError();
                       }

                       @Override
                       public void onResponse(User response) {
                           if (isSuccess(response)) {
                               mSpUtils.putBoolean(Constants.IS_LOGIN, true);
                               mSpUtils.putUser(response);
                               Intent intent = new Intent(mContext, MainUserActivity.class);
                               startActivity(intent);
                               mContext.finish();
                           } else {
                               ToastUtils.makePicTextShortToast(mContext,
                                                                Constants.ICON_ERROR,
                                                                response.message);
                           }
                       }
                   });
    }


}
