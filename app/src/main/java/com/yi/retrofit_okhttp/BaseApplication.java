package com.yi.retrofit_okhttp;

import android.app.Application;

import com.yi.download.FileStorageManager;

/**
 * Created by Yi on 2017/7/26.
 */

public class BaseApplication extends Application {
    private static final String TAG = BaseApplication.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();
        FileStorageManager.getInstance().init(this);
    }
}
