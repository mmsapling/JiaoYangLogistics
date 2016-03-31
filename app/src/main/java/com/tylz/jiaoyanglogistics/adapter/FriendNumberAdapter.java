package com.tylz.jiaoyanglogistics.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tylz.jiaoyanglogistics.R;
import com.tylz.jiaoyanglogistics.base.MyBaseApdater;
import com.tylz.jiaoyanglogistics.model.ContactsInfo;

import java.util.List;

/**
 * @author tylz
 * @time 2016/3/31 0031 14:53
 * @des 联系人信息适配器
 *
 * @updateAuthor
 * @updateDate 2016/3/31 0031
 * @updateDes
 */
public class FriendNumberAdapter
        extends MyBaseApdater<ContactsInfo>
{
    /**
     * 通过构造方法,让外部传入具体的数据源
     * @param context
     * @param dataSource
     */
    public FriendNumberAdapter(Context context, List<ContactsInfo> dataSource)
    {
        super(context, dataSource);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            View view = mLayoutInflater.inflate(R.layout.item_friend_number, parent, false);
            holder = new ViewHolder();
            view.setTag(holder);
            holder.ivHead = (ImageView) view.findViewById(R.id.item_frient_civ_head);
            holder.containerContent = (LinearLayout) view.findViewById(R.id.item_frient_container_content);
            holder.tvMobile = (TextView) view.findViewById(R.id.item_frient_tv_mobile);
            holder.tvName = (TextView) view.findViewById(R.id.item_frient_tv_name);
            holder.tvSort = (TextView) view.findViewById(R.id.item_frient_tv_sort);
            holder.tvLine = (TextView) view.findViewById(R.id.item_frient_tv_line);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        ContactsInfo data = (ContactsInfo) getItem(position);
        //根据position获取分类的首字母的Char ascii值
//            int section = getSectionForPosition(position);
//            //如果当前位置等于该分类首字母的char位置，则认为是第一次出现
//            if(position == getPositionForSection(section)){
//                holder.tvSort.setVisibility(View.VISIBLE);
//                String sort =  "☆".equals(data.sortLetter) ?data.sortLetter+ "(管理员)" : data.sortLetter;
//                holder.tvSort.setText(sort);
//                holder.tvLine.setVisibility(View.VISIBLE);
//            }else{
//                holder.tvSort.setVisibility(View.GONE);
//                holder.tvLine.setVisibility(View.GONE);
//            }
        holder.tvName.setText(data.name);
        holder.tvMobile.setText(data.mobile);
        holder.ivHead.setImageBitmap(data.photo);
        return convertView;
    }


    public Object[] getSections() {
        return null;
    }

    /**
     * 根据分类的首字母的char ascii值获取其第一次出现的该首字母的位置
     * @param sectionIndex
     */
    public int getPositionForSection(int sectionIndex) {
        for (int i = 0; i < getCount(); i++) {
            String sortStr = mDataSource.get(i).sortLetter;
            char firshChar = sortStr.toUpperCase()
                                    .charAt(0);
            if (firshChar == sectionIndex) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 根据Listview的当前位置获取分类的首字母Char ascii值
     */
    public int getSectionForPosition(int position) {
        return mDataSource.get(position).sortLetter.charAt(0);
    }

    private static class ViewHolder {
        ImageView    ivHead;
        TextView     tvSort;
        TextView     tvName;
        TextView     tvMobile;
        LinearLayout containerContent;
        TextView     tvLine;
    }

}
