package com.tylz.jiaoyanglogistics.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.tylz.jiaoyanglogistics.conf.Constants;


/**
 * @author cxw
 * @time 2016/3/18 0018 15:02
 * @des 保存一些公有的属性到sp
 * @updateAuthor tylz
 * @updateDate 2016/3/18 0018
 * @updateDes
 */
public class SPUtils {

	private SharedPreferences	mSp;
	private Editor				mEditor;

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
	};

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
	public boolean removeKey(String key){
		return mSp.edit().remove(key).commit();
	}
	/**删除全部Key值,返回boolean是否执行成功！*/
	public boolean clear(){
		return mSp.edit().clear().commit();
	}
}
