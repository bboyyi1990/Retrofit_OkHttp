package com.yi.retrofit_okhttp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

import com.yi.download.DownloadManager;
import com.yi.download.Logger;

import java.io.File;

import http.DownloadCallback;
import http.HttpManager;

/**
 * Created by Yi on 2017/7/27.
 */

public class HttpActivity extends BaseActivity {
    private static final String TAG = HttpActivity.class.getSimpleName();
    ImageView iv;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http);
        iv = (ImageView) findViewById(R.id.http_image);

//        File file = FileStorageManager.getInstance().getFileByName("傅翼");
//        Logger.e(TAG, "path -> " + file.getAbsolutePath());//获取创建的绝对路径
//        downloadSingleThread();
        downloadMultiThreading();
    }

    /**
     * 单线程下载
     */
    private void downloadSingleThread() {
        HttpManager.getInstance().asyncRequest("http://szimg.mukewang.com/595e01c50001d24805400300-360-202.jpg"
                , new DownloadCallback() {
                    @Override
                    public void success(File file) {
                        Logger.e(TAG, "success ! file path ->" + file.getAbsolutePath());
                        final Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                iv.setImageBitmap(bitmap);
                            }
                        });
                    }

                    @Override
                    public void fail(int errorCode, String errorMessage) {
                        Logger.e(TAG, "fail code ->" + errorMessage + " \n  message ->" + errorMessage);

                    }

                    @Override
                    public void progress(int progress) {

                    }
                });

    }

    /**
     * 多线程下载
     */
    private void downloadMultiThreading() {
        DownloadManager.getInstance().download("http://szimg.mukewang.com/595e01c50001d24805400300-360-202.jpg", new DownloadCallback() {
            @Override
            public void success(File file) {
                final Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        iv.setImageBitmap(bitmap);
                    }
                });
                Logger.e(TAG, "success" + file.getAbsolutePath());
            }

            @Override
            public void fail(int errorCode, String errorMessage) {
                Logger.e(TAG, "fail" + errorCode + "  " + errorMessage);
            }

            @Override
            public void progress(int progress) {

            }
        });
    }
}
