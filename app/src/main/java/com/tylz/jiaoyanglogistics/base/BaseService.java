package com.tylz.jiaoyanglogistics.base;

import android.app.Service;


public abstract class BaseService extends Service {

    @Override
    public void onCreate() {
        super.onCreate();

        ((App) getApplication()).addService(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        ((App) getApplication()).removeService(this);
    }
}
