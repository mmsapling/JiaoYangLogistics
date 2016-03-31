package com.tylz.jiaoyanglogistics.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tylz.jiaoyanglogistics.R;
import com.tylz.jiaoyanglogistics.base.MyBaseApdater;

import java.util.List;

/**
 * @author tylz
 * @time 2016/3/26 0026 16:04
 * @des
 *
 * @updateAuthor
 * @updateDate 2016/3/26 0026
 * @updateDes
 */
public class TempAdapter extends MyBaseApdater<String> {
    /**
     * 通过构造方法,让外部传入具体的数据源
     * @param context
     * @param dataSource
     */
    public TempAdapter(Context context, List<String> dataSource) {
        super(context, dataSource);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView == null){
            holder = new ViewHolder();
            View view = View.inflate(mContext, R.layout.item_temp, null);
            view.setTag(holder);
            holder.mTvTemp = (TextView) view.findViewById(R.id.item_tv_temp);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        holder.mTvTemp.setText(mDataSource.get(position));
        return convertView;
    }
    private static class ViewHolder{
        TextView mTvTemp;
    }
}
