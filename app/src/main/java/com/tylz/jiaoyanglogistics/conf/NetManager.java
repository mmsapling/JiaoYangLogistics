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
    String BASE          = "http://192.168.1.80/";
    String SERVER_USER   = BASE + "jiaoyang/index.php/User/";
    String SERVER_DRIVER = BASE + "jiaoyang/index.php/Driver/";


    public interface User {
        String LOGIN  = SERVER_USER + "Login/login"; //登陆
        String VERIFY = SERVER_USER + "Login/verify"; //验证码

        String REGIST         = SERVER_USER + "Login/register"; //注册
        String FINDPASSWORD   = SERVER_USER + "Login/findPassword"; //注意，忘记密码，不是找回密码
        String MODIFYPASSWORD = SERVER_USER + "User/modifyPassword"; //注意，修改密码，不是忘记密码
    }

    String MOBILE    = "mobile";//手机号
    String PASSWORD  = "password";//密码
    String DEVICE_ID = "deviceID";//设备号
    String CODE      = "code";//验证码
}
