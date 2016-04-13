package com.tylz.jiaoyanglogistics.adapter;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tylz.jiaoyanglogistics.R;
import com.tylz.jiaoyanglogistics.conf.ItemClickListener;
import com.tylz.jiaoyanglogistics.conf.NetManager;
import com.tylz.jiaoyanglogistics.model.News;

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

    private Activity            mContext;
    private List<News.NewsInfo> mDataSource;
    private ItemClickListener   mItemClickListener;
    public NewsInfoAdapter(Context context, List<News.NewsInfo> dataSource) {
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
        News.NewsInfo info = mDataSource.get(position);
        holder.mTvTitle.setText(info.title);
        holder.mTvContent.setText(info.remark);
        Uri uri = Uri.parse(NetManager.BASE + info.thumb);
        Picasso.with(mContext)
               .load(uri)
               .resizeDimen(R.dimen.item_msg_icon_width, R.dimen.item_msg_icon_height)
               .into(holder.mIvIcon);
        holder.mRootView.setTag(info);
        holder.mRootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mItemClickListener != null){

                    mItemClickListener.onItemClick(v);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        if (mDataSource != null) {
            return mDataSource.size();
        }
        return 0;
    }

    public void setDatas(List<News.NewsInfo> dataSource) {
        mDataSource = dataSource;
        notifyDataSetChanged();
    }



    /**
     * viewholder
     */
    public static class ItemViewHolder
            extends RecyclerView.ViewHolder
    {
        private Context   mContext;
        private TextView  mTvTitle;
        private TextView  mTvContent;
        private ImageView mIvIcon;
        private View      mRootView;

        public ItemViewHolder(Context context, View itemView) {
            super(itemView);
            mContext = context;
            mRootView = itemView;
            mTvTitle = (TextView) itemView.findViewById(R.id.item_msg_tv_title);
            mTvContent = (TextView) itemView.findViewById(R.id.item_msg_tv_content);
            mIvIcon = (ImageView) itemView.findViewById(R.id.item_msg_iv_icon);
        }
    }
    public void setOnItemClickListener(ItemClickListener listener){
        mItemClickListener = listener;
    }
}
