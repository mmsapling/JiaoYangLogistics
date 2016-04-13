package com.tylz.jiaoyanglogistics.fragment;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.tylz.jiaoyanglogistics.R;
import com.tylz.jiaoyanglogistics.activity.LoginActivity;
import com.tylz.jiaoyanglogistics.base.BaseCahceFragment;
import com.tylz.jiaoyanglogistics.conf.Constants;
import com.tylz.jiaoyanglogistics.conf.NetManager;
import com.tylz.jiaoyanglogistics.helper.CountDownButtonHelper;
import com.tylz.jiaoyanglogistics.model.Code;
import com.tylz.jiaoyanglogistics.model.User;
import com.tylz.jiaoyanglogistics.util.CommonUitls;
import com.tylz.jiaoyanglogistics.util.ToastUtils;
import com.tylz.jiaoyanglogistics.util.UIUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.DCallback;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * @author tylz
 * @time 2016/3/23 0023 18:11
 * @des 忘记密码
 *
 * @updateAuthor
 * @updateDate 2016/3/23 0023
 * @updateDes
 */
public class RePwdFra
        extends BaseCahceFragment
{
    @Bind(R.id.login_et_mobile)
    EditText mEtMobile;
    @Bind(R.id.login_et_code)
    EditText mEtCode;

    @Bind(R.id.login_et_pwd)
    EditText mEtPwd;
    @Bind(R.id.login_et_cofirm_pwd)
    EditText mEtCofirmPwd;
    @Bind(R.id.login_bt_confirm)
    Button   mBtConfirm;
    @Bind(R.id.login_tv_contact)
    TextView mTvContact;
    private Code mCodeInfo = new Code();

    @Override
    public View initRootView() {
        View view = View.inflate(mContext, R.layout.fra_repwd, null);
        mBtCode = (Button) view.findViewById(R.id.login_bt_code);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        mBtCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postCode();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.login_bt_confirm,
              R.id.login_tv_contact})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_bt_confirm:
                confirm();
                break;
            case R.id.login_tv_contact:
                break;
        }
    }

    /**
     * 确认提交
     */
    private void confirm() {
        //判断数据是否为空
        String mobile = mEtMobile.getText()
                                 .toString();
        String pwd = mEtPwd.getText()
                           .toString();
        String confirmPwd = mEtCofirmPwd.getText()
                                        .toString();
        String code = mEtCode.getText()
                             .toString();
        if (TextUtils.isEmpty(mobile)) {
            ToastUtils.makePicTextShortToast(mContext,
                                             Constants.ICON_TIP,
                                             R.string.tip_input_mobile);
            return;
        }
        if (TextUtils.isEmpty(code)) {
            ToastUtils.makePicTextShortToast(mContext, Constants.ICON_TIP, R.string.tip_input_code);
            return;
        }
        if (TextUtils.isEmpty(pwd) || TextUtils.isEmpty(confirmPwd)) {
            ToastUtils.makePicTextShortToast(mContext, Constants.ICON_TIP, R.string.tip_input_pwd);
            return;
        }
        if (!pwd.equals(confirmPwd)) {
            ToastUtils.makePicTextShortToast(mContext, Constants.ICON_TIP, R.string.tip_pwd_equals);
            return;
        }
        //从服务器获取的验证码为空
        if (TextUtils.isEmpty(mCodeInfo.verifyCode)) {
            ToastUtils.makePicTextShortToast(mContext, Constants.ICON_TIP, R.string.tip_code_error);
            return;
        }
        if (!code.equals(mCodeInfo.verifyCode)) {
            ToastUtils.makePicTextShortToast(mContext, Constants.ICON_TIP, R.string.tip_code_error);
            return;
        }
        showProgress();
        OkHttpUtils.post()
                   .url(NetManager.User.FINDPASSWORD)
                   .addParams(NetManager.MOBILE, mobile)
                   .addParams(NetManager.PASSWORD, pwd)
                   .addParams(NetManager.CODE, code)
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
                               mSpUtils.putBoolean(Constants.IS_LOGIN, false);
                               ToastUtils.makePicTextShortToast(mContext,
                                                                Constants.ICON_SUCCESS,
                                                                R.string.toast_edit_pwd_success);
                               LoginActivity activity = (LoginActivity) getActivity();
                               activity.switchLogin();
                           }
                       }
                   });
    }

    /**
     * 发送验证码请求
     */
    private void postCode() {

        String mobile = mEtMobile.getText()
                                 .toString();
        if (TextUtils.isEmpty(mobile)) {
            ToastUtils.makePicTextShortToast(mContext,
                                             Constants.ICON_TIP,
                                             R.string.tip_input_mobile);
            return;
        }
        getVerificationCode();
        OkHttpUtils.post()
                   .url(NetManager.User.VERIFY)
                   .addParams(NetManager.MOBILE, mobile)
                   .build()
                   .execute(new DCallback<Code>() {
                       @Override
                       public void onError(Call call, Exception e) {
                           connectError();
                       }

                       @Override
                       public void onResponse(Code response) {
                         if(isSuccess(response)){
                             mCodeInfo = response;
                         }
                       }
                   });
    }

    /**
     * 验证码倒计时
     */
    public void getVerificationCode() {
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
                ToastUtils.makePicTextShortToast(mContext, Constants.ICON_SUCCESS, R.string.tip);

            }
        });
        countDownButtonHelper.start();
    }
}
