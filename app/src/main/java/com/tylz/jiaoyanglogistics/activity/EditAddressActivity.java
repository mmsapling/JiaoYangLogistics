package com.tylz.jiaoyanglogistics.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tylz.jiaoyanglogistics.R;
import com.tylz.jiaoyanglogistics.base.BaseActivity;
import com.tylz.jiaoyanglogistics.conf.Constants;
import com.tylz.jiaoyanglogistics.model.Address;
import com.tylz.jiaoyanglogistics.util.ToastUtils;
import com.tylz.jiaoyanglogistics.view.TopMenu;
import com.zcw.togglebutton.ToggleButton;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author tylz
 * @time 2016/3/29 0029 14:49
 * @des 编辑地址
 *
 * @updateAuthor
 * @updateDate 2016/3/29 0029
 * @updateDes
 */
public class EditAddressActivity
        extends BaseActivity
{

    public static final int RESULT_CODE_EDIT = 1002;
    @Bind(R.id._top_menu)
    TopMenu mTopMenu;

    @Bind(R.id._et_name)
    EditText mEtName;

    @Bind(R.id._et_mobile)
    EditText mEtMobile;

    @Bind(R.id._et_fixmobile)
    EditText mEtFixmobile;

    @Bind(R.id._tv_area)
    TextView mTvArea;

    @Bind(R.id._et_address)
    EditText mEtAddress;

    @Bind(R.id._toggle_button)
    ToggleButton   mToggleButton;
    @Bind(R.id._bt_confirm)
    Button         mBtConfirm;
    @Bind(R.id._container_address)
    RelativeLayout mContainerAddress;
    private Address mAddress;
    private boolean isToggle = false; //默认发货地址开关为false

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        setContentView(R.layout.activity_edit_address);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        /*菜单设置*/
        mTopMenu.setTitle(R.string.edit_address);
        mTopMenu.setLeftIcon(R.drawable.selector_menu_back);
        mTopMenu.setLeftIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        /*滑动快关监听*/
        mToggleButton.setOnToggleChanged(new ToggleButton.OnToggleChanged() {
            @Override
            public void onToggle(boolean on) {
                isToggle = on;
            }
        });
        /**
         * 初始化数据
         */
        setData(mAddress);


    }

    private void setData(Address address) {
        mEtName.setText(address.name);
        mEtMobile.setText(address.mobile);
        mEtFixmobile.setText(address.fixMobile);
        mTvArea.setText(address.area);
        mEtAddress.setText(address.address);
        if (address.isDefault) {
            mToggleButton.setToggleOn(true);
        } else {
            mToggleButton.setToggleOff(true);
        }
    }

    private void init() {
        mAddress = (Address) getIntent().getSerializableExtra(Constants.TAG);
    }

    @OnClick({R.id._bt_confirm,
              R.id._container_address})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id._container_address:
                break;
            case R.id._bt_confirm:
                confirmEditAddress();
                break;
            default:
                finish();
                break;
        }
    }

    /**
     * 确认修改地址
     */
    private void confirmEditAddress() {
        String name = mEtName.getText()
                             .toString();
        String mobile = mEtMobile.getText()
                                 .toString();
        String fixMobile = mEtFixmobile.getText()
                                       .toString();
        String area = mTvArea.getText()
                             .toString();
        String address = mEtAddress.getText()
                                   .toString();
        //判断是否为空
        boolean isEmpty = TextUtils.isEmpty(name) || TextUtils.isEmpty(mobile) || TextUtils.isEmpty(
                fixMobile) || TextUtils.isEmpty(area) || TextUtils.isEmpty(address);
        if(isEmpty){
            ToastUtils.makePicTextShortToast(this,Constants.ICON_TIP,R.string.page_address_tip);
            return;
        }
        mAddress.name = name;
        mAddress.mobile = mobile;
        mAddress.fixMobile = fixMobile;
        mAddress.area = area;
        mAddress.address = address;
        mAddress.isDefault = isToggle;
        Intent data = new Intent();
        data.putExtra(Constants.TAG,mAddress);
        setResult(RESULT_CODE_EDIT, data);
        finish();
    }

}
