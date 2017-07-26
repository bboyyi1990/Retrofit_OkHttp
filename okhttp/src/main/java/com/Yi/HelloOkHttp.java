package com.Yi;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Yi on 2017/7/14.
 */

public class HelloOkHttp {

    /**
     * 同步网络请求
     */
    public static void sendReqeust() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().
                url("http://www.imooc.com").build();

        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                System.out.print(response.body().string());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void sendAsyncRequest(String url) {
        System.out.print("Current Thread ->" + Thread.currentThread().getName());
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(url)
                .addHeader("User-Agent", "from Yi http") //加入请求头
                .addHeader("Accept", "text/plain,text/html")
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    System.out.print("Response Thread ->" + Thread.currentThread().getName());
//                    System.out.print(response.body().string());
                    for (int i = 0; i < response.headers().size(); i++) {
                        System.out.print(response.headers().name(i) + " : " + response.headers().value(i));
                    }
                }
            }
        });
    }

    public static void queryHttp() {
        OkHttpClient okHttpClient = new OkHttpClient();
        HttpUrl httpUrl = HttpUrl.parse("https://api.heweather.com/x3/weather").
                newBuilder().
                addQueryParameter("city", "beijing").
                addQueryParameter("key", "").
                build();
        Request request = new Request.Builder().url(httpUrl).build();
        String url = httpUrl.toString();
        System.out.print(url);
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    System.out.print(response.body().string());
                }
            }
        });
    }

    public static void postRequest() {
        OkHttpClient okHttpClient = new OkHttpClient();//OKHttp 客户端

        FormBody body = new FormBody.Builder().add("userName", "Yi").add("userAge", "27").build();//post请求体

        Request request = new Request.Builder().url("").post(body).build(); //请求对象

        //客户端请求回调
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    System.out.print(response.body().string());
                }
            }
        });
    }

    /**
     * 文件上传请求
     */
    public static void MulitpartHttp() {
        OkHttpClient okHttpClient = new OkHttpClient();
        //构建图片上传请求体
        RequestBody imageBody = RequestBody.create(MediaType.parse("image/png"), new File("/User/apple/Miku.jpg"));

        //构建多部件请求体
        MultipartBody body = new MultipartBody.Builder().
                setType(MultipartBody.FORM).//选择请求体构建格式表单(如果不指定表单类型则 文本信息无法上传)
                addFormDataPart("name", "Yi").
                addFormDataPart("fileName", "Miku.jpg", imageBody).//传入 图片请求体
                build();

        Request request = new Request.Builder().url("").post(body).build();//构建请求

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    System.out.print(response.body().string());
                }
            }
        });
    }

    /**
     * 缓存请求
     *
     * @throws IOException
     */
    public static void cacheOkHttp() throws IOException {
        //缓存最大空间
        int maxCacheSize = 10 * 1024 * 1024;
        //构建缓存对象
        Cache cache = new Cache(new File("/Users/apple/Desktop/CacheOkHttp"), maxCacheSize);
        //构建 缓存请求业务
        OkHttpClient okHttpClient = new OkHttpClient.Builder().cache(cache).build();
        //构建请求（请求的地址必须是支持缓存的）
        Request request = new Request.Builder().
                url("http://www.qq.com").
//                cacheControl(new CacheControl.Builder().maxStale(365, TimeUnit.DAYS).build()).//设置缓存控制
                build();

        Response response = okHttpClient.newCall(request).execute();

        String body = response.body().string();//
        System.out.print("network response->" + response.networkResponse());
        System.out.print("\ncache response->" + response.cacheResponse());

        System.out.print("\n$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");

        /**
         * 注：发送网络请求的时候一定要读取整个请求返回的内容 否则默认再次发送网络请求
         * 因此两个String 类型的body 进行读取 触发缓存
         */
        Response response1 = okHttpClient.newCall(request).execute();
        String body1 = response1.body().string();//
        System.out.print("\nnetwork response->" + response1.networkResponse());
        System.out.print("\ncache response->" + response1.cacheResponse());
    }

    public static void main(String args[]) throws Exception {
//        sendReqeust();
//        sendAsyncRequest("http://www.imooc.com");
//        queryHttp();
//        postRequest();
        cacheOkHttp();
    }
}
