package com.yi.download;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

import http.DownloadCallback;
import http.HttpManager;
import okhttp3.Response;

/**
 * Created by Yi on 2017/8/1.
 */

public class DownloadRunnable implements Runnable {
    private long mStart;
    private long mEnd;
    private String mUrl;
    private DownloadCallback mCallback;

    public DownloadRunnable(long mStart, long mEnd, String mUrl, DownloadCallback mCallback) {
        this.mStart = mStart;
        this.mEnd = mEnd;
        this.mUrl = mUrl;
        this.mCallback = mCallback;
    }

    @Override
    public void run() {
        //获取指定链接 规定范围的请求
        Response response = HttpManager.getInstance().syncRequest(mUrl, mStart, mEnd);
        if (null == response && mCallback != null) {
            mCallback.fail(HttpManager.NETWORK_ERROR_CODE, "网络出问题了");
            return;
        }

        File file = FileStorageManager.getInstance().getFileByName(mUrl);

        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rwd");
            randomAccessFile.seek(mStart);//指定数据写入的偏移位置
            byte[] buffer = new byte[1024 * 500];
            int len;
            InputStream inputStream = response.body().byteStream();
            while ((len = inputStream.read(buffer, 0, buffer.length)) != -1) {
                randomAccessFile.write(buffer, 0, len);
            }
            mCallback.success(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
