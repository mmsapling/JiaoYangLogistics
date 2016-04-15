package com.tylz.jiaoyanglogistics.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tylz.jiaoyanglogistics.R;
import com.tylz.jiaoyanglogistics.base.BaseFragment;
import com.tylz.jiaoyanglogistics.conf.Constants;
import com.tylz.jiaoyanglogistics.conf.NetManager;
import com.tylz.jiaoyanglogistics.model.AuthInfoModel;
import com.tylz.jiaoyanglogistics.util.ToastUtils;
import com.tylz.jiaoyanglogistics.util.UIUtils;
import com.tylz.jiaoyanglogistics.view.DActionSheetDialog;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.DCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;
import okhttp3.Call;

/**
 * @author tylz
 * @time 2016/3/25 0025 15:25
 * @des 实名认证fragment
 *
 * @updateAuthor
 * @updateDate 2016/3/25 0025
 * @updateDes
 */
public class RealNameApproveFra
        extends BaseFragment
{
    private final int             REQUEST_CODE_CAMERA  = 1000;
    private final int             REQUEST_CODE_GALLERY = 1001;
    private       List<PhotoInfo> mPhotoList           = new ArrayList<PhotoInfo>(); //用于特殊作用
    @Bind(R.id._tv_status)
    TextView  mTvStatus;
    @Bind(R.id._et_real_name)
    EditText  mEtRealName;
    @Bind(R.id._et_id)
    EditText  mEtId;
    @Bind(R.id._iv_idcard)
    ImageView mIvIdcard;
    @Bind(R.id._bt_status)
    Button    mBtStatus;
    private String[] mStatusArr   = UIUtils.getStrings(R.array.status);
    private String[] mBtStatusArr = UIUtils.getStrings(R.array.bt_status);

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = mLayoutInflater.inflate(R.layout.fra_realname_approve, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        mTvStatus.setText(mStatusArr[3]);
        mBtStatus.setText(mBtStatusArr[3]);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getDataFromNet();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id._iv_idcard,
              R.id._bt_status})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id._iv_idcard:
                selectIcon();
                break;
            case R.id._bt_status:
                confirmCheck();
                break;
        }
    }

    private void confirmCheck() {
        if(isDataEmpty()){
            return;
        }
        showProgress();
        String photoPath = mPhotoList.get(0)
                                     .getPhotoPath();
        File photo = new File(photoPath);
        OkHttpUtils.post().url(NetManager.User.SAVE_USER_AUTH)
                .addParams(NetManager.MOBILE,mUser.mobile)
                .addParams(NetManager.TOKEN,mUser.token)
                .addParams(NetManager.USERNAME,mEtRealName.getText().toString())
                .addParams(NetManager.IDNO,mEtId.getText().toString())
                .addFile(NetManager.PICTURE,photo.getName(),photo)
                .build().execute(new DCallback<AuthInfoModel>() {
            @Override
            public void onError(Call call, Exception e) {
                connectError();
            }

            @Override
            public void onResponse(AuthInfoModel response) {
                if(isSuccess(response)){
                    initData(response.authInfo);
                }
            }
        });

    }

    private boolean isDataEmpty() {
        String realName = mEtRealName.getText()
                              .toString();
        String id = mEtId.getText()
                        .toString();
        if(TextUtils.isEmpty(realName)){
            ToastUtils.makePicTextShortToast(mContext,Constants.ICON_TIP,R.string.hint_realname);
            return true;
        }
        if(TextUtils.isEmpty(id)){
            ToastUtils.makePicTextShortToast(mContext, Constants.ICON_TIP, R.string.hint_input_idcard_num);
            return true;
        }
        if(mPhotoList == null || mPhotoList.size() <= 0){
            ToastUtils.makePicTextShortToast(mContext, Constants.ICON_TIP, R.string.hint_input_select_icon);
            return true;
        }
        return false;

    }

    private void getDataFromNet() {
        showProgress();
        OkHttpUtils.post()
                   .url(NetManager.User.USER_AUTH_INFO)
                   .addParams(NetManager.MOBILE, mUser.mobile)
                   .addParams(NetManager.TOKEN, mUser.token)
                   .build()
                   .execute(new DCallback<AuthInfoModel>() {
                       @Override
                       public void onError(Call call, Exception e) {
                           connectError();
                       }

                       @Override
                       public void onResponse(AuthInfoModel response) {
                           if (isSuccess(response)) {
                               initData(response.authInfo);
                           }
                       }
                   });
    }

    /**
     * 初始化数据
     * @param authInfo 认证信息
     */
    private void initData(AuthInfoModel.AuthInfo authInfo) {
        if(authInfo == null){
            authInfo = new AuthInfoModel.AuthInfo();
        }
        if (TextUtils.isEmpty(authInfo.username) || TextUtils.isEmpty(authInfo.IDNO)) {
            authInfo.status = "3";
        }
        mEtRealName.setText(authInfo.username);
        mEtId.setText(authInfo.IDNO);
        mTvStatus.setText(mStatusArr[Integer.parseInt(authInfo.status)]);
        int index = Integer.parseInt(authInfo.status);
        mBtStatus.setText(mBtStatusArr[index]);
        switch (index) {
            case 0: //等待审核
                setViewEnable(false);
                mBtStatus.setEnabled(false);
                break;
            case 1: // 审核通过
                setViewEnable(false);
                mBtStatus.setEnabled(false);
                break;
            case 2: // 审核未通过
                setViewEnable(true);
                mBtStatus.setEnabled(true);
                break;
            case 3: //从来没提交过
                setViewEnable(true);
                mBtStatus.setEnabled(true);
                break;
        }
        //加载图片
        if (!TextUtils.isEmpty(authInfo.picture)) {
            Uri uri = Uri.parse(NetManager.BASE + authInfo.picture);
            Picasso.with(mContext)
                   .load(uri)
                   .into(mIvIdcard);
        }

    }

    /**
     * 设置控件是否能用
     * @param enable
     */
    private void setViewEnable(boolean enable) {
        mEtRealName.setEnabled(enable);
        mEtId.setEnabled(enable);
        mIvIdcard.setEnabled(enable);
    }

    /**
     * 选择照片
     */
    private void selectIcon() {
        new DActionSheetDialog(mContext).builder()
                                        .setTitle(UIUtils.getString(R.string.please_select))
                                        .setCancelable(false)
                                        .setCanceledOnTouchOutside(false)
                                        .addSheetItem(UIUtils.getString(R.string.take_photo),
                                                      DActionSheetDialog.SheetItemColor.Blue,
                                                      new DActionSheetDialog.OnSheetItemClickListener() {
                                                          @Override
                                                          public void onClick(int which) {
                                                              selectIcon(which);
                                                          }
                                                      })
                                        .addSheetItem(UIUtils.getString(R.string.open_photo),
                                                      DActionSheetDialog.SheetItemColor.Blue,
                                                      new DActionSheetDialog.OnSheetItemClickListener() {
                                                          @Override
                                                          public void onClick(int which) {
                                                              selectIcon(which);
                                                          }
                                                      })
                                        .show();

    }

    /**
     * 根据点击事件来判断是拍照还是打开相册
     * @param which  事件的标志
     */
    private void selectIcon(int which) {
        switch (which) {
            case 1: //拍照
                GalleryFinal.openCamera(REQUEST_CODE_CAMERA, mOnHanlderResultCallback);
                break;
            case 2: //打开相册
                GalleryFinal.openGallerySingle(REQUEST_CODE_GALLERY, mOnHanlderResultCallback);
                break;
        }
    }

    /**
     * 处理图片结果
     */
    private GalleryFinal.OnHanlderResultCallback mOnHanlderResultCallback = new GalleryFinal.OnHanlderResultCallback() {
        @Override
        public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
            if (resultList != null) {
                mPhotoList.clear();
                mPhotoList.addAll(resultList);
                PhotoInfo photoInfo = resultList.get(0);

                Picasso.with(mContext)
                       .load(new File(photoInfo.getPhotoPath()))
                       .resize(UIUtils.dp2Px((int) getResources().getDimension(R.dimen.icon_width)),
                               UIUtils.dp2Px((int) getResources().getDimension(R.dimen.icon_height)))
                       .into(mIvIdcard);


            }
        }

        @Override
        public void onHanlderFailure(int requestCode, String errorMsg) {
            ToastUtils.makePicTextShortToast(mContext, Constants.ICON_ERROR, errorMsg);
        }
    };
}
