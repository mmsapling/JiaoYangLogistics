package com.tylz.jiaoyanglogistics.view.pullrefresh.base;

public enum DState {
	
	NONE,

	/**
	 * 正在下拉,显示 下拉可以刷新
	 */
	PULL_TO_REFRESH,

	/**
	 *正在下拉,显示 释放刷新
	 */
	RELEASE_TO_REFRESH,

	/**
	 * 正在加载中
	 */
	REFRESHING,
	/**
	 * 加载成功
	 */
	REFRESHING_SUCCESS,
	/**
	 * 加载失败
	 */
	REFRESHING_FAIL,

	/**
	 * 没有更多数据
	 */
	NO_MORE_DATA,
}
