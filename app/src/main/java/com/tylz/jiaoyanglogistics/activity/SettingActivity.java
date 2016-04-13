package com.tylz.jiaoyanglogistics.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.tylz.jiaoyanglogistics.R;
import com.tylz.jiaoyanglogistics.base.App;
import com.tylz.jiaoyanglogistics.base.BaseActivity;
import com.tylz.jiaoyanglogistics.conf.Constants;
import com.tylz.jiaoyanglogistics.util.CommonUitls;
import com.tylz.jiaoyanglogistics.util.ToastUtils;
import com.tylz.jiaoyanglogistics.util.UIUtils;
import com.tylz.jiaoyanglogistics.view.DAlertDialog;
import com.tylz.jiaoyanglogistics.view.ItemView;
import com.tylz.jiaoyanglogistics.view.TopMenu;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author tylz
 * @time 2016/3/25 0025 17:55
 * @des 设置界面
 *
 * @updateAuthor
 * @updateDate 2016/3/25 0025
 * @updateDes
 */
public class SettingActivity
        extends BaseActivity
{

    @Bind(R.id._topMenu)
    TopMenu  mTopMenu;
    @Bind(R.id._iv_edit_pwd)
    ItemView mIvEditPwd;
    @Bind(R.id._iv_grade)
    ItemView mIvGrade;
    @Bind(R.id._iv_feedback)
    ItemView mIvFeedback;
    @Bind(R.id._iv_update)
    ItemView mIvUpdate;
    @Bind(R.id._iv_about)
    ItemView mIvAbout;
    @Bind(R.id._bt_logout)
    Button   mBtLogout;
    private String[] mDatas = UIUtils.getStrings(R.array.settings);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        initView();
    }

    /**
     * 初始化视图
     */
    private void initView() {
        mTopMenu.setTitle(R.string.setting);
        mTopMenu.setLeftIcon(R.drawable.selector_menu_back);
        mTopMenu.setLeftIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mIvEditPwd.setTextAndHideIcon(mDatas[0]);
        mIvGrade.setTextAndHideIcon(mDatas[1]);
        mIvFeedback.setTextAndHideIcon(mDatas[2]);
        mIvUpdate.setTextAndHideIcon(mDatas[3]);
        mIvUpdate.setRightTextAndHideArrow(CommonUitls.getVersionName(this));
        mIvAbout.setTextAndHideIcon(mDatas[4]);
        mIvAbout.setRightTextAndHideArrow("");
        mIvAbout.isShowLine(false);
    }

    @OnClick({R.id._bt_logout,
              R.id._iv_edit_pwd,
              R.id._iv_grade,
              R.id._iv_feedback,
              R.id._iv_update,
              R.id._iv_about})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id._iv_edit_pwd:
                Intent intent1 = new Intent(this,PwdEditActivity.class);
                startActivity(intent1);
                break;
            case R.id._iv_grade:
                ToastUtils.makePicTextShortToast(this,Constants.ICON_TIP,R.string.tip_no_support);
                break;
            case R.id._iv_feedback:
                Intent intent3 = new Intent(this,FeedBackActivity.class);
                startActivity(intent3);
                break;
            case R.id._iv_update:
                ToastUtils.makePicTextShortToast(this,Constants.ICON_TIP,R.string.tip_update);
                break;
            case R.id._iv_about:
                Intent intent5 = new Intent(this,AboutUsActivity.class);
                startActivity(intent5);
                break;
            case R.id._bt_logout:
                logout();
                break;
        }
    }

    /**
     * 退出登陆
     * 移除用户信息
     */
    private void logout() {

        new DAlertDialog(this).builder()
                                  .setTitle(UIUtils.getString(R.string.tip))
                                  .setMsg(UIUtils.getString(R.string.confirm_logout))
                                  .setPositiveButton(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View v) {
                                          ((App) getApplication()).closeApplication();
                                          mSpUtils.putBoolean(Constants.IS_LOGIN, false);
                                          mSpUtils.removeUser();
                                          Intent intent = new Intent(SettingActivity.this, LoginActivity.class);
                                          startActivity(intent);

                                      }
                                  })
                                  .setNegativeButton(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View v) {
                                          //空实现
                                      }
                                  })
                                  .show();


    }


}
