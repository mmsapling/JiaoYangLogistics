package com.tylz.jiaoyanglogistics.conf;

import com.tylz.jiaoyanglogistics.R;
import com.tylz.jiaoyanglogistics.util.LogUtils;

/**
 * @author tylz
 * @time 2016/3/18 0018 15:07
 * @des 项目一些属性配置
 * @updateAuthor ${author}
 * @updateDate 2016/3/18 0018
 * @updateDes ${TODO}
 */
public interface Constants {
    /**
     * 配置日志输出级别
     */
    int     DEBUGLEVEL                = LogUtils.LEVEL_DEBUG;
    /**
     * 配置日志输出TAG名称
     */
    String  TAG                       = "tylz";
    /**
     * SharedPreferences的文件名
     */
    String  SPFILENAME                = "tylz";
    /**
     * 提示的3中图片
     */
    int     ICON_ERROR                = R.mipmap.tip_error;
    int     ICON_TIP                  = R.mipmap.tip_waring;
    int     ICON_SUCCESS              = R.mipmap.tip_success;
    int     ICON_DEFAULT              = R.mipmap.default_driver;
    int     ICON_DEFAUL_ERROR         = R.mipmap.tip_error;
    /**
     * 引导页面数据
     */
    long    SPLASH_ANIMATION_DURATION = 2000;
    long    SPLASH_SLEEP              = 2000;
    /**
     * 是否是第一次启动
     */
    String  IS_FIRST_ENTER            = "is_first_enter";
    boolean IS_FIRST_ENTER_DEFAULT    = false; //不是第一次启动了 false
    /**
     * 是否是司机登陆 是 true ，否false
     */
    String  IS_DRIVER                 = "is_driver";
    /**
     * 点的大小 10dp 间距 8dp
     */
    int     POINT_SIZE                = 10;
    int     POINT_MARGIN              = 8;
    /**
     * 是否登陆
     */
    String  IS_LOGIN                  = "is_login";
    String  MOBILE                    = "15019443112"; //默认客服电话
    /**
     * 轮播图延迟时间
     */
    long    TIME_DELAY_LOOP_PIC       = 2000;
    int     TIME_GETCODE_MAX          = 60;//60秒
    int     TIME_GETCODE_INTERVAL     = 1;// 倒计时的时间间隔 1秒
    /**
     * 默认每页的数据为10条
     */
    int     SIZE_PAGE                 = 10;
    /**
     * 数据类型和编码
     */
    String  MIME_TYPE                 = "text/html";
    String  ENCODING                  = "UTF-8";
    /**
     * 地址的json文件名
     */
    String  JSON_ADDRESS_DATA_NAME1   = "address_data_json1.txt";
    String  JSON_ADDRESS_DATA_NAME    = "address_data_json1.txt";
    /**
     * 能否添加地址
     */
    String  IS_ADDABLE_RECEIVE_ADDRESS        = "1";
    String  IS_ADDABLE_SEND_ADDRESS        = "1";
}
