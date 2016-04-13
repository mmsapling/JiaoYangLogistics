package com.tylz.jiaoyanglogistics.base;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Window;

import com.tylz.jiaoyanglogistics.R;
import com.tylz.jiaoyanglogistics.conf.Constants;
import com.tylz.jiaoyanglogistics.model.User;
import com.tylz.jiaoyanglogistics.util.SPUtils;
import com.tylz.jiaoyanglogistics.util.ToastUtils;
import com.tylz.jiaoyanglogistics.view.DProgressDialog;

/**
 * @author tylz
 * @time 2016/3/18 0018 15:02
 * @des 所有Activity的基类，保存一些公共方法和属性
 * @updateAuthor tylz
 * @updateDate 2016/3/18 0018
 * @updateDes
 */
public class BaseActivity
        extends Activity
{
    public  SPUtils         mSpUtils;
    // 再按一次退出应用程序
    private DProgressDialog mProgressDialog;
    public  User            mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        mSpUtils = new SPUtils(this);
        ((App) getApplication()).addActivity(this);
        mUser = mSpUtils.getUser();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ((App) getApplication()).removeActivity(this);
    }

    /**
     * 对返回的数据信息进行解析，判断网络是否成功
     * @param model 数据
     * @return true 代表成功 ，false 代表失败
     */
    public boolean isSuccess(BaseModel model) {
        closeProgress();
        if (model.code != 0 || !TextUtils.isEmpty(model.message)) {
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
        ToastUtils.makePicTextShortToast(this, Constants.ICON_ERROR, R.string.connect_net_error);
    }

    /**
     * 开启进度条
     */
    public void showProgress() {
        mProgressDialog = new DProgressDialog(this);
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
