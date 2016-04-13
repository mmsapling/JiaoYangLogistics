package com.tylz.jiaoyanglogistics.conf;

/**
 * @author tylz
 * @time 2016/4/5 0005 09:53
 * @des 网络接口地址管理
 *
 * @updateAuthor
 * @updateDate 2016/4/5 0005
 * @updateDes
 */
public interface NetManager {
    String BASE          = "http://192.168.1.222";
    String SERVER_USER   = BASE + "/jiaoyang/index.php/User/";
    String SERVER_DRIVER = BASE + "/jiaoyang/index.php/Driver/";


    public interface User {
        String LOGIN  = SERVER_USER + "Login/login"; //登陆
        String VERIFY = SERVER_USER + "Login/verify"; //验证码

        String REGIST                   = SERVER_USER + "Login/register"; //注册
        String FINDPASSWORD             = SERVER_USER + "Login/findPassword"; //注意，忘记密码，不是找回密码
        String MODIFYPASSWORD           = SERVER_USER + "User/modifyPassword"; //注意，修改密码，不是忘记密码
        String NEWS_LIST                = SERVER_USER + "Common/newsList";//新闻列表
        String NEWS                     = SERVER_USER + "Common/news";//新闻详情
        String CUSTOMER_SERVICE_PHONE   = SERVER_USER + "Common/customerServicePhone";//一键客服
        String SLIDERS                  = SERVER_USER + "Common/sliders";//轮播图
        String GET_RECEIPT_ADDRESS_LIST = SERVER_USER + "User/getReceiptAddressList";//收获人地址列表
        String MODIFY_AVATAR            = SERVER_USER + "User/modifyAvatar";//修改头像
        String MODIFY_USERNAME          = SERVER_USER + "User/modifyUsername";//修改昵称
        String USER_FEEDBACK            = SERVER_USER + "User/userFeedback";//意见反馈
        String ADD_RECEIPT_ADDRESS      = SERVER_USER + "User/addReceiptAddress";//添加用户收获地址
        String ADD_DELIVERY_ADDRESS      = SERVER_USER + "User/addDeliveryAddress";//添加用户发获地址
        String DELETE_RECEIPT_ADDRESS      = SERVER_USER + "User/deleteReceiptAddress";//删除用户收获地址
        String DELETE_DELIVERY_ADDRESS      = SERVER_USER + "User/deleteDeliveryAddress";//删除用户发获地址

    }

    String MOBILE        = "mobile";//手机号
    String PASSWORD      = "password";//密码
    String DEVICE_ID     = "deviceID";//设备号
    String CODE          = "code";//验证码
    String OLD_PASSWORLD = "oldPassword"; //旧密码
    String TOKEN         = "token";//口令
    String START         = "start";//起始id
    String LENGTH        = "length";//数据个数
    String ID            = "id";
    String AVATAR        = "avatar";
    String USERNAME      = "username";
    String CONTENT       = "content";
    String USER_MOBILE   = "userMobile";//用户手机号
    String TEL           = "tel";//电话
    String PROVINCE      = "province";//省
    String CITY          = "city";//市
    String AREA          = "area";//区
    String STREET        = "street";//街道
    String ADDRESS       = "address";//详细地址
    String STATUS        = "status";//是否为默认地址


}
