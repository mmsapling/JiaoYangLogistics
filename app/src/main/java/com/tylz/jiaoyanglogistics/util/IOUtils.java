package com.tylz.jiaoyanglogistics.util;

import java.io.Closeable;
import java.io.IOException;
/**
 * @author cxw
 * @time 2016/3/18 0018 15:02
 * @des  关闭流对象的公共方法
 * @updateAuthor tylz
 * @updateDate 2016/3/18 0018
 * @updateDes
 */
public class IOUtils {
	/** 关闭流 */
	public static boolean close(Closeable io) {
		if (io != null) {
			try {
				io.close();
			} catch (IOException e) {
				LogUtils.e(e);
			}
		}
		return true;
	}
}
