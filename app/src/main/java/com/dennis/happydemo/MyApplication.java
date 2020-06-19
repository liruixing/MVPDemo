package com.dennis.happydemo;

import android.app.Application;

import com.dennis.libcommon.CommonManager;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        CommonManager.getInstance().applicationCreate(this);
    }
}
