package com.tylz.jiaoyanglogistics.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.tylz.jiaoyanglogistics.R;
import com.tylz.jiaoyanglogistics.base.BaseModel;
import com.tylz.jiaoyanglogistics.conf.Constants;
import com.tylz.jiaoyanglogistics.conf.ItemClickListener;
import com.tylz.jiaoyanglogistics.conf.NetManager;
import com.tylz.jiaoyanglogistics.model.Address;
import com.tylz.jiaoyanglogistics.model.IsOk;
import com.tylz.jiaoyanglogistics.model.User;
import com.tylz.jiaoyanglogistics.util.SPUtils;
import com.tylz.jiaoyanglogistics.util.ToastUtils;
import com.tylz.jiaoyanglogistics.util.UIUtils;
import com.tylz.jiaoyanglogistics.view.DAlertDialog;
import com.tylz.jiaoyanglogistics.view.DProgressDialog;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.DCallback;

import java.util.List;

import okhttp3.Call;

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
    private boolean           isReceive; //true 代表是收货人信息，false为发货人信息
    private User              mUser;

    public AddressInfoAdapter(Context context, List<Address> data, boolean isReceive) {
        mContext = context;
        mDataSource = data;
        this.isReceive = isReceive;
        mUser = new SPUtils(context).getUser();
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
        holder.mTvName.setText(data.username);
        holder.mTvMobile.setText(data.mobile);
        holder.mTvAddress.setText(data.address);
        holder.mBtEdit.setTag(data);
        holder.mBtEdit.setOnClickListener(this);
        holder.mBtDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new DAlertDialog(mContext).builder()
                                          .setTitle(UIUtils.getString(R.string.tip))
                                          .setMsg(UIUtils.getString(R.string.tip_delete))
                                          .setPositiveButton(new View.OnClickListener() {
                                              @Override
                                              public void onClick(View v) {
                                                  if (isReceive) {
                                                      //刪除收货人地址
                                                      deleteReceive(position);
                                                  } else {
                                                      //删除发货人地址
                                                      deleteSend(position);
                                                  }
                                              }
                                          })
                                          .setNegativeButton(new View.OnClickListener() {
                                              @Override
                                              public void onClick(View v) {
                                                  //空实现
                                              }
                                          })
                                          .show();


            }
        });
    }

    private void deleteSend(final int position) {
        Address address = mDataSource.get(position);
        showProgress();
        OkHttpUtils.post()
                   .url(NetManager.User.DELETE_DELIVERY_ADDRESS)
                   .addParams(NetManager.MOBILE, mUser.mobile)
                   .addParams(NetManager.TOKEN, mUser.token)
                   .addParams(NetManager.ID, address.id)
                   .build()
                   .execute(new DCallback<IsOk>() {
                       @Override
                       public void onError(Call call, Exception e) {
                           connectError();
                       }

                       @Override
                       public void onResponse(IsOk response) {
                           if (isSuccess(response)) {
                               mDataSource.remove(position);
                               notifyDataSetChanged();
                               ToastUtils.makePicTextShortToast(mContext,
                                                                Constants.ICON_SUCCESS,
                                                                R.string.success_delete_receive_address);
                           }
                       }
                   });
    }

    private void deleteReceive(final int position) {
        Address address = mDataSource.get(position);
        showProgress();
        OkHttpUtils.post()
                   .url(NetManager.User.DELETE_RECEIPT_ADDRESS)
                   .addParams(NetManager.MOBILE, mUser.mobile)
                   .addParams(NetManager.TOKEN, mUser.token)
                   .addParams(NetManager.ID, address.id)
                   .build()
                   .execute(new DCallback<IsOk>() {
                       @Override
                       public void onError(Call call, Exception e) {
                           connectError();
                       }

                       @Override
                       public void onResponse(IsOk response) {
                           if (isSuccess(response)) {
                               mDataSource.remove(position);
                               notifyDataSetChanged();
                               ToastUtils.makePicTextShortToast(mContext,
                                                                Constants.ICON_SUCCESS,
                                                                R.string.success_delete_receive_address);
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
        public TextView mTvName;
        public TextView mTvMobile;
        public TextView mTvAddress;
        public Button   mBtEdit;
        public Button   mBtDelete;
        public View     mRootView;

        public ItemHolder(View itemView) {
            super(itemView);
            mTvAddress = (TextView) itemView.findViewById(R.id._item_tv_address);
            mTvMobile = (TextView) itemView.findViewById(R.id._item_tv_mobile);
            mTvName = (TextView) itemView.findViewById(R.id._item_tv_name);
            mBtEdit = (Button) itemView.findViewById(R.id._item_bt_edit);
            mBtDelete = (Button) itemView.findViewById(R.id._item_bt_delete);
        }
    }

    /**
     * 对返回的数据信息进行解析，判断网络是否成功
     * @param model 数据
     * @return true 代表成功 ，false 代表失败
     */
    public boolean isSuccess(BaseModel model) {
        closeProgress();
        if (model.code != 0 || !TextUtils.isEmpty(model.message)) {
            ToastUtils.makePicTextShortToast(mContext, Constants.ICON_TIP, model.message);
            return false;
        } else {
            return true;
        }
    }

    /**
     * 网络连接失败
     */
    public void connectError() {
        closeProgress();
        ToastUtils.makePicTextShortToast(mContext,
                                         Constants.ICON_ERROR,
                                         R.string.connect_net_error);
    }

    /**
     * 开启进度条
     */
    public void showProgress() {
        mProgressDialog = new DProgressDialog(mContext);
        mProgressDialog.show();
    }

    /**
     * 关闭进度条
     */
    public void closeProgress() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }

    private DProgressDialog mProgressDialog;
}
