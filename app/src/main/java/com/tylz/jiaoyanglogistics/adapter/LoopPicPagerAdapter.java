package com.tylz.jiaoyanglogistics.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.tylz.jiaoyanglogistics.base.BasePageAdapter;
import com.tylz.jiaoyanglogistics.conf.NetManager;
import com.tylz.jiaoyanglogistics.model.LoopPic;

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
        extends BasePageAdapter<LoopPic.LoopPicInfo>
{

    public LoopPicPagerAdapter(Context context, List<LoopPic.LoopPicInfo> dataSource) {
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
        LoopPic.LoopPicInfo info = mDataSource.get(position);
        Uri uri = Uri.parse(NetManager.BASE + info.picture);

        ImageView view = new ImageView(mContext);
        view.setScaleType(ImageView.ScaleType.FIT_XY);
        Picasso.with(mContext).load(uri).into(view);
        container.addView(view);
        return view;
    }

}
