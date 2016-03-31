package com.tylz.jiaoyanglogistics.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author cxw
 * @time 2016/3/18 0018 15:02
 * @des ListView适配器的基类，保存一些公共方法和属性
 * @updateAuthor tylz
 * @updateDate 2016/3/18 0018
 * @updateDes
 */

public class MyBaseApdater<ITEMBEANTYPE>
        extends BaseAdapter
{
    protected List<ITEMBEANTYPE> mDataSource = new ArrayList<ITEMBEANTYPE>();
    protected     Context        mContext;
    protected final LayoutInflater mLayoutInflater;

    /**
     * 通过构造方法,让外部传入具体的数据源
     */
    public MyBaseApdater(Context context, List<ITEMBEANTYPE> dataSource) {
        super();
        mDataSource = dataSource;
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        if (mDataSource != null) {
            return mDataSource.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (mDataSource != null) {
            return mDataSource.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO
        return null;
    }

    public void addAllList(Collection<? extends ITEMBEANTYPE> collection) {
        this.mDataSource.addAll(collection);
        this.notifyDataSetChanged();
    }

    public void clearAllList() {
        this.mDataSource.clear();
        this.notifyDataSetChanged();
    }

    public void setList(List<ITEMBEANTYPE> list) {
        this.mDataSource = list;
        this.notifyDataSetChanged();
    }

    public List<ITEMBEANTYPE> getList() {
        return this.mDataSource;
    }

    public void add(ITEMBEANTYPE object) {
        this.mDataSource.add(object);
        this.notifyDataSetChanged();
    }
}
