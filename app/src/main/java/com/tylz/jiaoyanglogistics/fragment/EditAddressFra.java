package com.tylz.jiaoyanglogistics.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tylz.jiaoyanglogistics.R;
import com.tylz.jiaoyanglogistics.base.BaseFragment;

/**
 * @author tylz
 * @time 2016/3/29 0029 13:44
 * @des 修改地址
 *
 * @updateAuthor
 * @updateDate 2016/3/29 0029
 * @updateDes
 */
public class EditAddressFra extends BaseFragment{


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = mLayoutInflater.inflate(R.layout.fra_edit_address, container, false);
        return view;
    }


}
