package com.tylz.jiaoyanglogistics.view.pullrefresh.base;

import android.view.View;
import android.widget.Scroller;

/**
 * 下拉刷新和上拉加载更多的界面接口
 * 
 * @author jason
 */
public interface DLoading {
    /**
     * 设置当前状态，派生类应该根据这个状态的变化来改变View的变化
     * @param state 状态
     */
    public void setState(DState state);
    
    /**
     * 得到当前的状态
     * @return 状态
     */
    public DState getState();
    /**
     * 获取当前刷新View 高度
     * @return 状态
     */
    public int getCurrentViewHeight();
    
    /**开始刷新，当下拉松开后被调用*//*
    public void startRefrese();*/
    
    /**开始刷新，当下拉松开后被调用*/
    public void startRefreseToPull();
    
    /**开始刷新，上拉加载更多时候*/
    public void startRefreseToUp();
    
    /**取消刷新，当下拉松开后被调用
     * @param isRefreseSuccess:true：刷新成功 false:刷新失败  null:不显示刷新成功失败提示
     * */
    public void stopRefrese(View view, Scroller mScroller, Boolean isRefreseSuccess);
}
