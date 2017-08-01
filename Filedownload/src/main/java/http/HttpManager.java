package http;

import android.content.Context;

import com.yi.download.FileStorageManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Yi on 2017/7/27.
 */

public class HttpManager {

    public static final int NETWORK_ERROR_CODE = 1;//请求错误
    public static final int CONTENT_LENGTH_ERROR_CODE = 2;//内容长度错误

    private static final HttpManager instance = new HttpManager();

    private Context context;

    private OkHttpClient mClient;

    public static HttpManager getInstance() {
        return instance;
    }

    private HttpManager() {
        mClient = new OkHttpClient();
    }

    public void init(Context context) {
        this.context = context;
    }

    /**
     * 同步请求
     *
     * @param url
     * @return
     */
    public Response syncRequest(String url) {
        Request request = new Request.Builder().url(url).build();
        try {
            return mClient.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 同步请求
     *
     * @param url
     * @param start 请求范围起点
     * @param end   请求范围终点
     * @return
     */
    public Response syncRequest(String url, long start, long end) {
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Range", "bytes=" + start + "-" + end)//请求头增加 请求范围
                .build();
        try {
            return mClient.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 异步调用
     *
     * @param url
     * @param callback
     */
    public void asyncRequest(final String url, Callback callback) {
        Request request = new Request.Builder().url(url).build();
        mClient.newCall(request).enqueue(callback);
    }

    public void asyncRequest(final String url, final DownloadCallback callback) {
        Request request = new Request.Builder().url(url).build();
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful() && null != callback) {
                    //取得储存路径
                    File file = FileStorageManager.getInstance().getFileByName(url);
                    //定义缓冲区
                    byte[] buffer = new byte[1024 * 500];
                    //定义缓冲长度
                    int length;
                    //定义输出流
                    FileOutputStream fileOut = new FileOutputStream(file);
                    //取得请求返回的输入流对象
                    InputStream inStream = response.body().byteStream();
                    while ((length = inStream.read(buffer, 0, buffer.length)) != -1) {
                        /**
                         * 读取输入流 写入到缓冲区 写入长位置为 0 - 缓冲区长度，
                         * 如果还有没有读取完的数据则 返回 缓冲区的 长度
                         * 如果输入流全部读取完成则 返回-1
                         */
                        //向输出流 写入缓冲的数据
                        fileOut.write(buffer, 0, length);
                        //输出流刷新
                        fileOut.flush();
                    }
//                    fileOut.close();
//                    inStream.close();
                    callback.success(file);//回调返回文件
                } else if (!response.isSuccessful() && null != callback) {
                    callback.fail(NETWORK_ERROR_CODE, "request fail");
                }
            }
        });
    }

}
