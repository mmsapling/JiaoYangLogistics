package com.tylz.jiaoyanglogistics.model;

import java.io.Serializable;

/**
 * @author tylz
 * @time 2016/3/29 0029 11:14
 * @des 地址信息
 *
 * @updateAuthor
 * @updateDate 2016/3/29 0029
 * @updateDes 地址信息
 */
public class Address implements Serializable{
    public String name;
    public String address; //详细地址
    public String mobile; //手机
    public String fixMobile; //固定电话
    public String area; //所在地区
    public boolean isDefault;//是否为默认地址
    public int postion; //记录位置
}