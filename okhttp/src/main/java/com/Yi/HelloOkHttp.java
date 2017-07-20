package com.Yi;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
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

    public static void main(String args[]) {
//        sendReqeust();
//        sendAsyncRequest("http://www.imooc.com");
//        queryHttp();
        postRequest();
    }
}
