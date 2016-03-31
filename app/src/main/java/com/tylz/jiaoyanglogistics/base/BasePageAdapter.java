package com.tylz.jiaoyanglogistics.base;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class BasePageAdapter<T>
        extends PagerAdapter
{
    public List<T> mDataSource = new ArrayList<T>();
    public Context mContext;
    public BasePageAdapter(Context context,List<T> dataSource){
        super();
        mContext = context;
        mDataSource = dataSource;
    }
    @Override
    public int getCount() {
        if(mDataSource != null){
            return mDataSource.size();
        }
        return 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        return null;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
