package com.tylz.jiaoyanglogistics.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.tylz.jiaoyanglogistics.util.ToastUtils;
import com.tylz.jiaoyanglogistics.util.UIUtils;
import com.tylz.jiaoyanglogistics.view.DActionSheetDialog;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = mLayoutInflater.inflate(R.layout.fra_realname_approve, container, false);
        ButterKnife.bind(this, view);
        return view;
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
                break;
        }
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
