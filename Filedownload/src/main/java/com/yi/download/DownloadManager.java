package com.yi.download;

import android.util.TimeUtils;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import http.DownloadCallback;
import http.HttpManager;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by Yi on 2017/8/1.
 */

public class DownloadManager {
    private static final int MAX_THREAD = 2;
    private static final DownloadManager sManager = new DownloadManager();

    private static final ThreadPoolExecutor sThreadPool = new ThreadPoolExecutor(
            MAX_THREAD,//核心线程数
            MAX_THREAD,//最大线程数
            60,//线程存活时间
            TimeUnit.MILLISECONDS,//时间单元秒
            new SynchronousQueue<Runnable>(),//同步队列
            new ThreadFactory() {//线程工厂
                private AtomicInteger mInteger = new AtomicInteger(1);

                @Override
                public Thread newThread(Runnable r) {
                    Thread thread = new Thread(r, "download thread #" + mInteger.getAndIncrement());
                    return thread;
                }
            });

    public static DownloadManager getInstance() {
        return sManager;
    }

    private DownloadManager() {
    }


    public void download(final String url, final DownloadCallback callback) {
        HttpManager.getInstance().asyncRequest(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful() && null != callback) {
                    callback.fail(HttpManager.NETWORK_ERROR_CODE, "网络出问题了");
                    return;
                }
                long length = response.body().contentLength();
                if (length == -1) { //如果服务器不支持content length字段返回-1
                    callback.fail(HttpManager.CONTENT_LENGTH_ERROR_CODE, "content length -1");
                    return;
                }

                processDownload(url, length,callback);
            }
        });
    }

    private void processDownload(String url, long length, DownloadCallback callback) {

        //单个线程现在数据长度
        //加入100 个字节长度 第一个线程下载 0-49 第二个线程 50- 99
        long threadDonwloadSize = length / MAX_THREAD;
        for (int i = 0; i < MAX_THREAD; i++) {
            long startSize = i * threadDonwloadSize;//起始位置
            long endSize = (i + 1) * threadDonwloadSize - 1;//结束位置
            sThreadPool.execute(new DownloadRunnable(startSize, endSize, url, callback));//指定每个线程下载的起始位置，终点位置 ，资源地址， 以及完成回调
        }
    }
}
