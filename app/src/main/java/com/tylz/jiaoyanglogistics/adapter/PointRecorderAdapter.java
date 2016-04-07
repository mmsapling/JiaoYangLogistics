package com.tylz.jiaoyanglogistics.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tylz.jiaoyanglogistics.R;
import com.tylz.jiaoyanglogistics.model.PointRecorderInfo;

import java.util.List;

/**
 * @author tylz
 * @time 2016/4/5 0005 17:11
 * @des 积分明细 和兑换记录 适配器
 *
 * @updateAuthor
 * @updateDate 2016/4/5 0005
 * @updateDes
 */
public class PointRecorderAdapter
        extends RecyclerView.Adapter<PointRecorderAdapter.ItemHolder>
{
    private Context                 mContext;
    private List<PointRecorderInfo> mDataSource;

    public PointRecorderAdapter(Context context, List<PointRecorderInfo> datas) {
        mContext = context;
        mDataSource = datas;
    }

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(mContext, R.layout.item_point_recorder, null);
        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemHolder holder, int position) {
        PointRecorderInfo info = mDataSource.get(position);
        holder.mTvTime.setText("time" + info);
        holder.mTvContent.setText(info.content);
    }

    @Override
    public int getItemCount() {
        if (mDataSource != null) {
            return mDataSource.size();
        }
        return 0;
    }

    public static class ItemHolder
            extends RecyclerView.ViewHolder
    {
        public TextView mTvContent;
        public TextView mTvTime;

        public ItemHolder(View itemView) {
            super(itemView);
            mTvContent = (TextView) itemView.findViewById(R.id.item_tv_content);
            mTvTime = (TextView) itemView.findViewById(R.id.item_tv_time);
        }
    }
}
