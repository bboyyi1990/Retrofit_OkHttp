package com.yi.retrofit_okhttp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

/** 窗口化Activity
 * Created by Yi on 2017/7/27.
 */

public class WindowActivity extends FragmentActivity {
    private static final String TAG = WindowActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏 第一种方法

        setContentView(R.layout.activity_window);
    }
}
