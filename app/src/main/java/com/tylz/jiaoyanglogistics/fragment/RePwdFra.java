package com.tylz.jiaoyanglogistics.fragment;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
    @Bind(R.id.login_bt_code)
    Button   mBtCode;
    @Bind(R.id.login_et_pwd)
    EditText mEtPwd;
    @Bind(R.id.login_et_cofirm_pwd)
    EditText mEtCofirmPwd;
    @Bind(R.id.login_bt_confirm)
    Button   mBtConfirm;
    @Bind(R.id.login_tv_contact)
    TextView mTvContact;

    @Override
    public View initRootView() {
        View view = View.inflate(mContext, R.layout.fra_repwd, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.login_bt_code,
              R.id.login_bt_confirm,
              R.id.login_tv_contact})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_bt_code:
                getVerificationCode();
                break;
            case R.id.login_bt_confirm:
                Toast.makeText(getActivity(), "cc", Toast.LENGTH_SHORT).show();
                break;
            case R.id.login_tv_contact:
                Toast.makeText(getActivity(), "dd", Toast.LENGTH_SHORT).show();
                break;
        }
    }
    private void getVerificationCode() {
        CountDownButtonHelper countDownButtonHelper = new CountDownButtonHelper(mBtCode,
                                                                                mBtCode.getText()
                                                                                       .toString(),
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
