package com.tylz.jiaoyanglogistics.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.tylz.jiaoyanglogistics.base.BasePageAdapter;

import java.util.List;

public class GuidePageAdapter
        extends BasePageAdapter<ImageView>
{

    public GuidePageAdapter(Context context, List<ImageView> dataSource) {
        super(context, dataSource);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView view = mDataSource.get(position);
        container.addView(view);
        return view;
    }
}
