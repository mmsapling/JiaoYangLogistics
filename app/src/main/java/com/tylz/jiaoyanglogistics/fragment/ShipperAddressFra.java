package com.tylz.jiaoyanglogistics.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tylz.jiaoyanglogistics.base.BaseFragment;

/**
 * @author tylz
 * @time 2016/3/25 0025 15:25
 * @des  发货人地址
 *
 * @updateAuthor
 * @updateDate 2016/3/25 0025
 * @updateDes
 */
public class ShipperAddressFra
        extends BaseFragment
{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState)
    {
        TextView view = new TextView(mContext);
        view.setText(this.getClass().getSimpleName());
        view.setGravity(Gravity.CENTER);
        init();
        return view;
    }

    private void init() {

    }

}
