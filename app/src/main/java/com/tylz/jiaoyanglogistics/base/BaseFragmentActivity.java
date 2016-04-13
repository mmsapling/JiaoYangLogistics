package com.tylz.jiaoyanglogistics.base;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.tylz.jiaoyanglogistics.util.SPUtils;

/**
 * @author tylz
 * @time 2016/3/23 0023 15:37
 * @des FragmentActivity的基类
 *
 * @updateAuthor
 * @updateDate 2016/3/23 0023
 * @updateDes
 */
public class BaseFragmentActivity extends FragmentActivity {
    public SPUtils mSPUtils;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSPUtils = new SPUtils(this);
    }
}
