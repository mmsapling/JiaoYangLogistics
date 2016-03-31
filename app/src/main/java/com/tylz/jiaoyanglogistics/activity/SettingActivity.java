package com.tylz.jiaoyanglogistics.activity;

import android.os.Bundle;
import android.view.View;

import com.tylz.jiaoyanglogistics.R;
import com.tylz.jiaoyanglogistics.base.BaseFragmentActivity;
import com.tylz.jiaoyanglogistics.util.UIUtils;
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
        extends BaseFragmentActivity
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
        mIvAbout.setTextAndHideIcon(mDatas[4]);
        mIvAbout.isShowLine(false);
    }

    @OnClick({R.id._iv_edit_pwd,
              R.id._iv_grade,
              R.id._iv_feedback,
              R.id._iv_update,
              R.id._iv_about})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id._iv_edit_pwd:
                break;
            case R.id._iv_grade:
                break;
            case R.id._iv_feedback:
                break;
            case R.id._iv_update:
                break;
            case R.id._iv_about:
                break;
        }
    }


}
