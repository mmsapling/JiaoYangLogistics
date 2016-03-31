package com.tylz.jiaoyanglogistics.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.tylz.jiaoyanglogistics.R;
import com.tylz.jiaoyanglogistics.conf.ItemClickListener;
import com.tylz.jiaoyanglogistics.model.Address;

import java.util.List;

/**
 * @author tylz
 * @time 2016/3/29 0029 11:17
 * @des 地址信息适配器
 *
 * @updateAuthor
 * @updateDate 2016/3/29 0029
 * @updateDes 地址信息适配器
 */
public class AddressInfoAdapter
        extends RecyclerView.Adapter<AddressInfoAdapter.ItemHolder>
        implements View.OnClickListener
{
    private Context           mContext;
    private List<Address>     mDataSource;
    private ItemClickListener mItemClickListener;

    public AddressInfoAdapter(Context context, List<Address> data) {
        mContext = context;
        mDataSource = data;
    }

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext)
                                      .inflate(R.layout.item_address_info, parent, false);
        return new ItemHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ItemHolder holder, final int position) {
        Address data = mDataSource.get(position);
        data.postion = position;
        holder.mTvName.setText(data.name);
        holder.mTvMobile.setText(data.mobile);
        holder.mTvAddress.setText(data.address);
        holder.mBtEdit.setTag(data);
        holder.mBtEdit.setOnClickListener(this);
        holder.mBtDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDataSource.remove(position);
                notifyDataSetChanged();
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

    public void setItemClickListner(ItemClickListener listner) {
        mItemClickListener = listner;
    }

    @Override
    public void onClick(View v) {
        if (mItemClickListener != null) {
            mItemClickListener.onItemClick(v);
        }
    }

    public static class ItemHolder
            extends RecyclerView.ViewHolder
    {
        public  TextView mTvName;
        public  TextView mTvMobile;
        public  TextView mTvAddress;
        public  Button   mBtEdit;
        public  Button   mBtDelete;
        public  View     mRootView;

        public ItemHolder(View itemView) {
            super(itemView);
            mTvAddress = (TextView) itemView.findViewById(R.id._item_tv_address);
            mTvMobile = (TextView) itemView.findViewById(R.id._item_tv_mobile);
            mTvName = (TextView) itemView.findViewById(R.id._item_tv_name);
            mBtEdit = (Button) itemView.findViewById(R.id._item_bt_edit);
            mBtDelete = (Button) itemView.findViewById(R.id._item_bt_delete);
        }
    }
}
