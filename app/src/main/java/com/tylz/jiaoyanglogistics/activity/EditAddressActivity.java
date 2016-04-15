package com.tylz.jiaoyanglogistics.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tylz.jiaoyanglogistics.R;
import com.tylz.jiaoyanglogistics.base.BaseActivity;
import com.tylz.jiaoyanglogistics.conf.Constants;
import com.tylz.jiaoyanglogistics.conf.NetManager;
import com.tylz.jiaoyanglogistics.model.Address;
import com.tylz.jiaoyanglogistics.model.AddressInfo;
import com.tylz.jiaoyanglogistics.model.AddressModel;
import com.tylz.jiaoyanglogistics.model.AreaInfo;
import com.tylz.jiaoyanglogistics.model.CityInfo;
import com.tylz.jiaoyanglogistics.model.StreetInfo;
import com.tylz.jiaoyanglogistics.util.CommonUitls;
import com.tylz.jiaoyanglogistics.util.ToastUtils;
import com.tylz.jiaoyanglogistics.view.TopMenu;
import com.zcw.togglebutton.ToggleButton;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.DCallback;

import org.angmarch.views.NiceSpinner;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

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

    @Bind(R.id._et_address)
    EditText mEtAddress;

    @Bind(R.id._toggle_button)
    ToggleButton   mToggleButton;
    @Bind(R.id._bt_confirm)
    Button         mBtConfirm;
    @Bind(R.id._container_address)
    RelativeLayout mContainerAddress;
    @Bind(R.id.edit_title_linkman)
    TextView       editTitleLinkman;
    @Bind(R.id.edit_title_mobile)
    TextView       editTitleMobile;
    @Bind(R.id.edit_title_fixmobile)
    TextView       editTitleFixmobile;
    @Bind(R.id.edit_title_area)
    TextView       editTitleArea;
    @Bind(R.id._ns_city)
    NiceSpinner    mNsCity;
    @Bind(R.id._ns_area)
    NiceSpinner    mNsArea;
    @Bind(R.id._ns_district)
    NiceSpinner    mNsDistrict;
    private Address mAddress;
    private boolean isToggle = false; //默认发货地址开关为false
    private AddressInfo      mAddressInfo;
    private List<CityInfo>   mCityInfos;
    private List<AreaInfo>   mAreaInfos;
    private List<StreetInfo> mStreetInfos;

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
        initAddress();

    }

    private void initAddress() {
        mAddressInfo = CommonUitls.parseJson(this);
        //得到所有的城市信息
        mCityInfos = CommonUitls.getAllCityInfo(mAddressInfo);

        //得到要修改的城市下面的所有区域
        mAreaInfos = CommonUitls.getAreaDataByCityId(mAddressInfo, mAddress.city);
        //得到要修改的区域下面的街道
        mStreetInfos = CommonUitls.getStreetDataByAreaId(mAddressInfo,
                                                         mAddress.city,
                                                         mAddress.area);

        mNsCity.attachDataSource(mCityInfos);
        mNsArea.attachDataSource(mAreaInfos);
        mNsDistrict.attachDataSource(mStreetInfos);

        //选中默认的地址
        initDefaultAddress(mAddress);
        //设置三级联动
        setThreeMoveListener();
    }

    /**
     * 设置三级联动的 条目点击事件
     */
    private void setThreeMoveListener() {
        mNsCity.addOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CityInfo cityInfo = mCityInfos.get(position);
                //更新区域数据
                mAreaInfos = CommonUitls.getAreaDataByCityName(mAddressInfo, cityInfo);
                mNsArea.attachDataSource(mAreaInfos);
                //更新街道数据
                mStreetInfos = CommonUitls.getStreetDataByAreaName(mAddressInfo,
                                                                   cityInfo,
                                                                   cityInfo.child.get(0));
                mNsDistrict.attachDataSource(mStreetInfos);
            }
        });
        mNsArea.addOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //得到城市信息
                int      selectedIndex = mNsCity.getSelectedIndex();
                CityInfo cityInfo      = mCityInfos.get(selectedIndex);
                //得到区域信息
                AreaInfo areaInfo = mAreaInfos.get(position);
                mStreetInfos = CommonUitls.getStreetDataByAreaName(mAddressInfo,
                                                                   cityInfo,
                                                                   areaInfo);
                mNsDistrict.attachDataSource(mStreetInfos);
            }
        });
    }

    /**
     * 设置默认的地址
     * @param address 默认地址
     */
    private void initDefaultAddress(Address address) {
        //选中默认城市
        for(int i = 0; i < mCityInfos.size(); i++){
            if(mCityInfos.get(i).id .equals(address.city)){
               mNsCity.setSelectedIndex(i);
            }else{
                continue;
            }
        }
        for(int i = 0; i < mAreaInfos.size(); i++){
            if(mAreaInfos.get(i).id.equals(address.area)){
                mNsArea.setSelectedIndex(i);
            }else {
                continue;
            }
        }
        for(int i = 0; i < mStreetInfos.size(); i++){
            if(mStreetInfos.get(i).id.equals(address.street)){
                mNsDistrict.setSelectedIndex(i);
            }else {
                continue;
            }
        }
    }

    private void setData(Address address) {
        mEtName.setText(address.username);
        mEtMobile.setText(address.mobile);
        mEtFixmobile.setText(address.tel);
        mEtAddress.setText(address.address);
        if (address.status.equals("1")) {
            mToggleButton.setToggleOn(true);
        } else {
            mToggleButton.setToggleOff(true);
        }
    }

    private void init() {
        mAddress = (Address) getIntent().getSerializableExtra(Constants.TAG);
    }

    @OnClick({R.id._bt_confirm})
    public void onClick(View view) {
        switch (view.getId()) {

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

        String address = mEtAddress.getText()
                                   .toString();
        if (isDataEmpty()) {
            return;
        }
        String status = isToggle == true
                        ? "1"
                        : "0";
        CityInfo   cityInfo   = mCityInfos.get(mNsCity.getSelectedIndex());
        AreaInfo   areaInfo   = mAreaInfos.get(mNsArea.getSelectedIndex());
        StreetInfo streetInfo = mStreetInfos.get(mNsDistrict.getSelectedIndex());
        showProgress();
        OkHttpUtils.post()
                   .url(NetManager.User.EDIT_RECEIPT_ADDRESS)
                   .addParams(NetManager.MOBILE, mUser.mobile)
                   .addParams(NetManager.TOKEN, mUser.token)
                   .addParams(NetManager.USERNAME, name)
                   .addParams(NetManager.USER_MOBILE, mobile)
                   .addParams(NetManager.ID, mAddress.id)
                   .addParams(NetManager.TEL, fixMobile)
                   .addParams(NetManager.PROVINCE, cityInfo.pid)
                   .addParams(NetManager.CITY, cityInfo.id)
                   .addParams(NetManager.AREA, areaInfo.id)
                   .addParams(NetManager.STREET, streetInfo.id)
                   .addParams(NetManager.ADDRESS, address)
                   .addParams(NetManager.STATUS, status)
                   .build()
                   .execute(new DCallback<AddressModel>() {
                       @Override
                       public void onError(Call call, Exception e) {
                           connectError();
                       }

                       @Override
                       public void onResponse(AddressModel response) {
                           if (isSuccess(response)) {
                               //保存默认收获地址sp
                               mSpUtils.putReceiveAddress(response.address);
                               Intent data = new Intent();
                               data.putExtra(Constants.TAG, response);
                               setResult(RESULT_CODE_EDIT, data);
                               finish();
                           }
                       }
                   });

    }

    /**
     * 判断数据是否为空
     * @return ture 代表数据为空，false代表数据都不为空
     */
    private boolean isDataEmpty() {
        String linkman = mEtName.getText()
                                .toString();
        String mobile = mEtMobile.getText()
                                 .toString();
        String fixMobile = mEtFixmobile.getText()
                                       .toString();
        String detailAddress = mEtAddress.getText()
                                         .toString();
        if (TextUtils.isEmpty(linkman)) {
            ToastUtils.makePicTextShortToast(this, Constants.ICON_TIP, R.string.hint_input_linkman);
            return true;
        }
        if (TextUtils.isEmpty(mobile)) {
            ToastUtils.makePicTextShortToast(this, Constants.ICON_TIP, R.string.tip_input_mobile);
            return true;
        }
        if (TextUtils.isEmpty(fixMobile)) {
            ToastUtils.makePicTextShortToast(this,
                                             Constants.ICON_TIP,
                                             R.string.hint_input_fix_mobile);
            return true;
        }
        if (TextUtils.isEmpty(detailAddress)) {
            ToastUtils.makePicTextShortToast(this,
                                             Constants.ICON_TIP,
                                             R.string.hint_input_detail_address);
            return true;
        }
        return false;
    }
}
