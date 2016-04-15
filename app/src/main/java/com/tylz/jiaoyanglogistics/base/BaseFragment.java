package com.tylz.jiaoyanglogistics.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;

import com.tylz.jiaoyanglogistics.R;
import com.tylz.jiaoyanglogistics.conf.Constants;
import com.tylz.jiaoyanglogistics.model.User;
import com.tylz.jiaoyanglogistics.util.SPUtils;
import com.tylz.jiaoyanglogistics.util.ToastUtils;
import com.tylz.jiaoyanglogistics.view.DProgressDialog;

/**
 * @author tylz
 * @time 2016/3/18 0018 15:02
 * @des 所有Fragment的基类，保存一些公共方法和属性
 * @updateAuthor tylz
 * @updateDate 2016/3/18 0018
 * @updateDes
 */

public abstract class BaseFragment
        extends Fragment
{
    public  SPUtils         mSpUtils;
    public  Activity        mContext;
    public  LayoutInflater  mLayoutInflater;
    private DProgressDialog mProgressDialog;
    public  User            mUser;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSpUtils = new SPUtils(getActivity());
        mContext = getActivity();
        mLayoutInflater = LayoutInflater.from(mContext);
        mUser = mSpUtils.getUser();
    }

    @Override
    public void onDestroyView() {
        closeProgress();
        super.onDestroyView();

    }
    @Override
    public void onStart() {
        super.onStart();
        mUser = mSpUtils.getUser();
    }

    /**
     * 对返回的数据信息进行解析，判断网络是否成功
     * @param model 数据
     * @return true 代表成功 ，false 代表失败
     */
    public boolean isSuccess(BaseModel model) {
        closeProgress();
        if(model == null){
            return false;
        }
        if (model.code != 0 || !TextUtils.isEmpty(model.message)) {
            ToastUtils.makePicTextShortToast(mContext, Constants.ICON_TIP, model.message);
            return false;
        } else {
            return true;
        }
    }

    /**
     * 网络连接失败
     */
    public void connectError() {
        closeProgress();
        ToastUtils.makePicTextShortToast(mContext,
                                         Constants.ICON_ERROR,
                                         R.string.connect_net_error);
    }

    /**
     * 开启进度条
     */
    public void showProgress() {
        mProgressDialog = new DProgressDialog(mContext);
        mProgressDialog.show();
    }

    /**
     * 关闭进度条
     */
    public void closeProgress() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }
}
