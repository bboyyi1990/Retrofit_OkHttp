package com.yi.download;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.IOException;

/**
 * 文件存储管理类
 * Created by Yi on 2017/7/26.
 */

public class FileStorageManager {

    private static final FileStorageManager instance = new FileStorageManager();

    private Context mContext;

    public static FileStorageManager getInstance() {
        return instance;
    }

    private FileStorageManager() {
    }

    public void init(Context context) {
        this.mContext = context;
    }

    /**
     * 获取本读储存文件
     *
     * @param url
     * @return
     */
    public File getFileByName(String url) {
        File root;//声明文件存储根目录
        //判断设备环境外部挂载（SD卡）是否安装
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            root = mContext.getExternalCacheDir();//取得设备外部挂载文件
        } else {
            root = mContext.getCacheDir();//获取手机缓存文件
        }

        String fileName = MD5Utils.generateCode(url);//文件名MD5加密
        File file = new File(root, fileName);//在根目录下创建 子文件
        if (!file.exists()) {
            try {
                file.createNewFile();//如果文件不存在则创建子目录文件
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }
}
