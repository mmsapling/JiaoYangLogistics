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
public class Address
        implements Serializable
{
    public String id;
    public String username;
    public String address; //详细地址
    public String mobile; //手机

    public String province; //省
    public String street; //街道
    public String city; //城市
    public String area; //所在地区
    public String status;//是否为默认地址
    public String tel;
    public int    postion; //记录位置
}
