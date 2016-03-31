package com.tylz.jiaoyanglogistics.fragment;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.tylz.jiaoyanglogistics.R;
import com.tylz.jiaoyanglogistics.base.BaseCahceFragment;
import com.tylz.jiaoyanglogistics.conf.Constants;
import com.tylz.jiaoyanglogistics.helper.CountDownButtonHelper;
import com.tylz.jiaoyanglogistics.util.ToastUtils;
import com.tylz.jiaoyanglogistics.util.UIUtils;

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
public class RegistFra
        extends BaseCahceFragment
{
    @Bind(R.id.login_et_mobile)
    EditText mEtMobile;
    @Bind(R.id.login_et_pwd)
    EditText mEtPwd;
    @Bind(R.id.login_et_code)
    EditText mEtCode;
   // @Bind(R.id.login_bt_code)
    Button   mBtCode;
    @Bind(R.id.login_bt_login)
    Button   mBtLogin;
    @Bind(R.id.login_tv_forgetpwd)
    TextView mTvForgetpwd;
    @Bind(R.id.login_tv_registnow)
    TextView mTvRegistnow;


    @Override
    public View initRootView() {
        View view = View.inflate(mContext, R.layout.fra_login, null);
        mBtCode = (Button) view.findViewById(R.id.login_bt_code);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {

        mBtLogin.setText(UIUtils.getString(R.string.create_account));
        mTvForgetpwd.setVisibility(View.GONE);
        mTvRegistnow.setVisibility(View.GONE);
        mBtCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getVerificationCode();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);

    }

    @OnClick({
              R.id.login_bt_login,
              R.id.login_tv_forgetpwd,
              R.id.login_tv_registnow})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_bt_code:
                getVerificationCode();
                break;
            case R.id.login_bt_login:
                break;
            case R.id.login_tv_forgetpwd:
                break;
            case R.id.login_tv_registnow:
                break;
        }
    }

    private void getVerificationCode() {
        String code = mBtCode.getText()
                             .toString();
        CountDownButtonHelper countDownButtonHelper = new CountDownButtonHelper(mBtCode,
                                                                                code,
                                                                                UIUtils.getString(R.string.reget_code),
                                                                                Constants.TIME_GETCODE_MAX,
                                                                                Constants.TIME_GETCODE_INTERVAL);
        countDownButtonHelper.setOnFinishListener(new CountDownButtonHelper.OnFinishListener() {
            @Override
            public void finish() {
                //释放弱引用
                mRootView.clear();
                mRootView = null;
                ToastUtils.makePicTextShortToast(mContext,
                                                 Constants.ICON_SUCCESS,
                                                 R.string.tip);

            }
        });
        countDownButtonHelper.start();
    }


}
