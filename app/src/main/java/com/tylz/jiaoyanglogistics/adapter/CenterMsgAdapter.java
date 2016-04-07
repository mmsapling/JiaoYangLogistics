package com.tylz.jiaoyanglogistics.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tylz.jiaoyanglogistics.R;
import com.tylz.jiaoyanglogistics.model.CenterMsg;

import java.util.List;

/**
 * @author tylz
 * @time 2016/4/5 0005 13:52
 * @des 站内信的adapter
 *
 * @updateAuthor
 * @updateDate 2016/4/5 0005
 * @updateDes
 */
public class CenterMsgAdapter
        extends RecyclerView.Adapter<CenterMsgAdapter.ItemHolder>
{
    private Context         mContext;
    private List<CenterMsg> mDataSource;

    public CenterMsgAdapter(Context context, List<CenterMsg> datas) {
        mContext = context;
        mDataSource = datas;
    }

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(mContext, R.layout.item_center_msg, null);
        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemHolder holder, int position) {
        CenterMsg info = mDataSource.get(position);
        holder.mTvScore.setText(info.score);
        holder.mTvContent.setText(info.content);
        holder.mTvTime.setText(info.time +"");
    }

    @Override
    public int getItemCount() {
        if(mDataSource != null){
            return mDataSource.size();
        }
        return 0;
    }

    public static class ItemHolder
            extends RecyclerView.ViewHolder
    {
        public TextView  mTvContent;
        public TextView  mTvTime;
        public TextView  mTvScore;
        public ItemHolder(View itemView) {
            super(itemView);
            mTvContent = (TextView) itemView.findViewById(R.id.item_tv_content);
            mTvTime = (TextView) itemView.findViewById(R.id.item_tv_time);
            mTvScore = (TextView) itemView.findViewById(R.id.item_tv_score);
        }
    }
}
