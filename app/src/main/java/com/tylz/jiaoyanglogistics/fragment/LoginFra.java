package com.tylz.jiaoyanglogistics.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tylz.jiaoyanglogistics.R;
import com.tylz.jiaoyanglogistics.activity.LoginActivity;
import com.tylz.jiaoyanglogistics.base.BaseFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author tylz
 * @time 2016/3/23 0023 15:51
 * @des
 *
 * @updateAuthor
 * @updateDate 2016/3/23 0023
 * @updateDes
 */
public class LoginFra
        extends BaseFragment
{
    @Bind(R.id.login_et_mobile)
    EditText mEtMobile;
    @Bind(R.id.login_et_pwd)
    EditText mEtPwd;
    @Bind(R.id.login_et_code)
    EditText mEtCode;
    @Bind(R.id.login_bt_code)
    Button   mBtCode;
    @Bind(R.id.login_bt_login)
    Button   mBtLogin;
    @Bind(R.id.login_tv_forgetpwd)
    TextView mTvForgetpwd;
    @Bind(R.id.login_tv_registnow)
    TextView mTvRegistnow;
    @Bind(R.id.login_container_code)
    RelativeLayout mContainerCode;
    @Bind(R.id.login_line)
    View mViewLine;
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

    @OnClick({R.id.login_bt_code,
              R.id.login_bt_login,
              R.id.login_tv_forgetpwd,
              R.id.login_tv_registnow})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_bt_code:
                break;
            case R.id.login_bt_login:
                break;
            case R.id.login_tv_forgetpwd:
                mContext.switchRePwd();
                break;
            case R.id.login_tv_registnow:
                mContext.switchRegist();
                break;
        }
    }


}
