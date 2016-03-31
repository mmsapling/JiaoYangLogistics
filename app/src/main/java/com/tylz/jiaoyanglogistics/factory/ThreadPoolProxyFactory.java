package com.tylz.jiaoyanglogistics.factory;

import com.tylz.jiaoyanglogistics.manager.ThreadPoolProxy;

/**
 * @author cxw
 * @time 2016/3/18 0018 15:02
 * @des 创建普通的线程池的代理
 * @des 创建下载的线程池的代理
 * @updateAuthor tylz
 * @updateDate 2016/3/18 0018
 * @updateDes
 */
public class ThreadPoolProxyFactory {

    static ThreadPoolProxy mNormalThreadPoolProxy;    // 只需创建一次即可
    static ThreadPoolProxy mDownloadThreadPoolProxy;    // 只需创建一次即可

    /**
     * 返回普通线程池的代理
     * 双重检查加锁,保证只有第一次实例化的时候才启用同步机制,提高效率
     *
     * @return
     */
    public static ThreadPoolProxy createNormalThreadPoolProxy() {
        if (mNormalThreadPoolProxy == null) {
            synchronized (ThreadPoolProxyFactory.class) {
                if (mNormalThreadPoolProxy == null) {
                    mNormalThreadPoolProxy = new ThreadPoolProxy(5, 5, 3000);
                }
            }
        }
        return mNormalThreadPoolProxy;
    }

    /**
     * 返回下载线程池的代理
     */
    public static ThreadPoolProxy createDownloadThreadPoolProxy() {
        if (mNormalThreadPoolProxy == null) {
            synchronized (ThreadPoolProxyFactory.class) {
                if (mNormalThreadPoolProxy == null) {
                    mDownloadThreadPoolProxy = new ThreadPoolProxy(3, 3, 3000);
                }
            }
        }
        return mDownloadThreadPoolProxy;
    }
}
