package com.Yi;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Yi on 2017/7/14.
 */

public class HelloOkHttp {
    public static void main(String args[]) {

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().
                url("http://www.imooc.com").build();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()){
                System.out.print(response.body().string());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
