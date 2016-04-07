package com.tylz.jiaoyanglogistics.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.tylz.jiaoyanglogistics.R;
import com.tylz.jiaoyanglogistics.conf.Constants;
import com.tylz.jiaoyanglogistics.util.SPUtils;
import com.tylz.jiaoyanglogistics.util.ToastUtils;
import com.tylz.jiaoyanglogistics.view.DProgressDialog;

import java.lang.ref.WeakReference;

/**
 * @author tylz
 * @time 2016/3/24 0024 13:39
 * @des 可以缓存的fragment
 *
 * @updateAuthor
 * @updateDate 2016/3/24 0024
 * @updateDes
 */
public abstract class BaseCahceFragment
        extends Fragment
{
    public  SPUtils             mSpUtils;
    public  Activity            mContext;
    public  WeakReference<View> mRootView;
    private View                mView;
    public  Button              mBtCode;
    private DProgressDialog     mProgressDialog;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSpUtils = new SPUtils(getActivity());
        mContext = getActivity();
        mView = new View(mContext);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState)
    {

        if (mRootView == null || mRootView.get() == null || TextUtils.isEmpty(mBtCode.getText())) {
            mView = initRootView();
            mRootView = new WeakReference<View>(mView);
        } else {
             mView = mRootView.get();
        }
        return mView;
    }

    public abstract View initRootView();

    /**
     * 对返回的数据信息进行解析，判断网络是否成功
     * @param model 数据
     * @return  true 代表成功 ，false 代表失败
     */
    public boolean isSuccess(BaseModel model){
        closeProgress();
        if(model.code != 0 || !TextUtils.isEmpty(model.message)){
           return false;
        }else{
            return true;
        }
    }
    /**
     * 网络连接失败
     */
    public void connectError(){
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
        if(mProgressDialog != null){
            mProgressDialog.dismiss();
        }
    }



}