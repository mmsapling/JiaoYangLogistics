package com.tylz.jiaoyanglogistics.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;


/**
 * @项目名: 	ZhuanChe
 * @包名:	com.baidu.zhuanche.ui
 * @类名:	NoScrollListView
 * @创建者:	陈选文
 * @创建时间:	2015-12-8	下午8:29:44 
 * @描述:	自定义listview，直接将子类的高度测量出来
 * 
 * @svn版本:	$Rev$
 * @更新人:	$Author$
 * @更新时间:	$Date$
 * @更新描述:	TODO
 */
public class NoScrollListView extends ListView
{

	public NoScrollListView(Context context) {
		super(context,null);
	}

	public NoScrollListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	/** 
     * 设置不滚动 
     */  
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec)  
    {  
            int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,  
                            MeasureSpec.AT_MOST);  
            super.onMeasure(widthMeasureSpec, expandSpec);

    }  
}
