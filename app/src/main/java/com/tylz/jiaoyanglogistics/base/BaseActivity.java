package com.tylz.jiaoyanglogistics.base;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

import com.tylz.jiaoyanglogistics.util.SPUtils;

/**
 * @author tylz
 * @time 2016/3/18 0018 15:02
 * @des 所有Activity的基类，保存一些公共方法和属性
 * @updateAuthor tylz
 * @updateDate 2016/3/18 0018
 * @updateDes
 */
public class BaseActivity extends Activity {
    public SPUtils mSpUtils;
    // 再按一次退出应用程序
    private long							mPreClickTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        mSpUtils = new SPUtils(this);
        ((App)getApplication()).addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ((App)getApplication()).removeActivity(this);
    }


}
