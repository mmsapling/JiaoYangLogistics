package com.tylz.jiaoyanglogistics.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.tylz.jiaoyanglogistics.conf.Constants;
import com.tylz.jiaoyanglogistics.model.User;


/**
 * @author cxw
 * @time 2016/3/18 0018 15:02
 * @des 保存一些公有的属性到sp
 * @updateAuthor tylz
 * @updateDate 2016/3/18 0018
 * @updateDes
 */
public class SPUtils {

    private SharedPreferences mSp;
    private Editor            mEditor;

    public SPUtils(Context context) {
        mSp = context.getSharedPreferences(Constants.SPFILENAME, context.MODE_PRIVATE);
        mEditor = mSp.edit();
    }

    /**保存boolean型变量*/
    public void putBoolean(String key, boolean value) {
        if (key != null) {
            mEditor.putBoolean(key, value);
            mEditor.commit();
        }
    }

    ;

    /**保存String型变量*/
    public void putString(String key, String value) {
        if (key != null) {
            mEditor.putString(key, value);
            mEditor.commit();
        }
    }

    /**保存int型变量*/
    public void putInt(String key, int value) {
        if (key != null) {
            mEditor.putInt(key, value);
            mEditor.commit();
        }
    }

    /**保存float型变量*/
    public void putFloat(String key, float value) {
        if (key != null) {
            mEditor.putFloat(key, value);
            mEditor.commit();
        }
    }

    /**得到int值*/
    public int getInt(String key, int defValue) {
        return mSp.getInt(key, defValue);
    }

    /**得到float值*/
    public float getFloat(String key, int defValue) {
        return mSp.getFloat(key, defValue);
    }

    /**得到boolean值*/
    public boolean getBoolean(String key, boolean defValue) {
        return mSp.getBoolean(key, defValue);
    }

    /**得到String值*/
    public String getString(String key, String defValue) {
        return mSp.getString(key, defValue);
    }

    /**删除Key值,返回boolean是否执行成功！*/
    public boolean removeKey(String key) {
        return mSp.edit()
                  .remove(key)
                  .commit();
    }

    /**删除全部Key值,返回boolean是否执行成功！*/
    public boolean clear() {
        return mSp.edit()
                  .clear()
                  .commit();
    }

    /**
     * 存放User信息
     * @param user
     */
    public void putUser(User user) {
        putString("user_id", user.id);
        putString("user_mobile", user.mobile);
        putString("user_username", user.username);
        putString("user_avatar", user.avatar);
        putString("user_point", user.point);
        putString("user_loginIP", user.loginIP);
        putString("user_loginTimes", user.loginTimes);
        putString("user_deviceID", user.deviceID);
        putString("user_nonce", user.nonce);
        putString("user_createdTime", user.createdTime);
        putString("user_password",user.password);
        putString("user_token",user.token);
    }

    /**
     * 取出User信息
     */
    public User getUser() {
        User user = new User();
        user.id = getString("user_id", "");
        user.mobile = getString("user_mobile", "");
        user.username = getString("user_username", "");
        user.avatar = getString("user_avatar", "");
        user.point = getString("user_point", "");
        user.loginIP = getString("user_loginIP", "");
        user.loginTimes = getString("user_loginTimes", "");
        user.deviceID = getString("user_deviceID", "");
        user.nonce = getString("user_nonce", "");
        user.createdTime = getString("user_createdTime", "");
        user.password = getString("user_password","");
        user.token = getString("user_token","");
        return user;
    }

    /**
     * 清除User信息
     */
    public void removeUser(){
        removeKey("user_id");
        removeKey("user_mobile");
        removeKey("user_username");
        removeKey("user_avatar");
        removeKey("user_point");
        removeKey("user_loginIP");
        removeKey("user_loginTimes");
        removeKey("user_deviceID");
        removeKey("user_nonce");
        removeKey("user_createdTime");
        removeKey("user_token");
        removeKey("user_password");
    }
}
