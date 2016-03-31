package com.tylz.jiaoyanglogistics.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.tylz.jiaoyanglogistics.base.BasePageAdapter;
import com.tylz.jiaoyanglogistics.conf.Constants;
import com.tylz.jiaoyanglogistics.util.ToastUtils;

import java.util.List;

/**
 * @author tylz
 * @time 2016/3/23 0023 12:41
 * @des
 *
 * @updateAuthor
 * @updateDate 2016/3/23 0023
 * @updateDes
 */
public class LoopPicPagerAdapter
        extends BasePageAdapter<ImageView>
{

    public LoopPicPagerAdapter(Context context, List<ImageView> dataSource) {
        super(context, dataSource);
    }

    @Override
    public int getCount() {
        if (mDataSource != null) {
            return Integer.MAX_VALUE;
        }
        return 0;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        position = position % mDataSource.size();
        final int location = position;
        ImageView view = mDataSource.get(position);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.makePicTextLongToast((Activity) mContext, Constants.ICON_SUCCESS,"你点击的是第" + location + "张图！");
            }
        });
        container.addView(view);
        return view;
    }
}
