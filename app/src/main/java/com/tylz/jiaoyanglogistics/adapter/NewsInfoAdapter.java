package com.tylz.jiaoyanglogistics.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tylz.jiaoyanglogistics.R;

import java.util.List;

/**
 * @author tylz
 * @time 2016/3/26 0026 14:19
 * @des 新闻资讯适配器
 *
 * @updateAuthor
 * @updateDate 2016/3/26 0026
 * @updateDes
 */
public class NewsInfoAdapter
        extends RecyclerView.Adapter<NewsInfoAdapter.ItemViewHolder>
{

    private Activity     mContext;
    private List<String> mDataSource;

    public NewsInfoAdapter(Context context, List<String> dataSource) {
        mContext = (Activity) context;
        mDataSource = dataSource;
    }

    @Override
    public NewsInfoAdapter.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = View.inflate(mContext, R.layout.item_msg, null);
        return new ItemViewHolder(mContext, itemView);
    }

    @Override
    public void onBindViewHolder(NewsInfoAdapter.ItemViewHolder holder, int position) {

        holder.mTvTitle.setText(mDataSource.get(position));

    }

    @Override
    public int getItemCount() {
        if (mDataSource != null) {
            return mDataSource.size();
        }
        return 0;
    }


    /**
     * viewholder
     */
    public static class ItemViewHolder
            extends RecyclerView.ViewHolder
    {
        private Context  mContext;
        private TextView mTvTitle;
        private TextView mTvContent;
        private ImageView mIvIcon;
        public ItemViewHolder(Context context, View itemView) {
            super(itemView);
            mContext = context;
            mTvTitle = (TextView) itemView.findViewById(R.id.item_msg_tv_title);
            mTvContent = (TextView) itemView.findViewById(R.id.item_msg_tv_content);
            mIvIcon = (ImageView) itemView.findViewById(R.id.item_msg_iv_icon);
        }
    }
}
