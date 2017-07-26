package com.yi.download;

import android.text.TextUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Yi on 2017/7/26.
 */

public class MD5Utils {

    public static void main(String[] args) {
        System.out.print(generateCode("傅翼"));
    }

    public static String generateCode(String url) {
        //JAVA 没有TextUtils API 因此运行Main方法这里报错
//        if (TextUtils.isEmpty(url)) {
//            return null;
//        }
        StringBuffer stringBuffer = new StringBuffer();
        try {
            //获取md5加密算法 摘要对象
            MessageDigest digest = MessageDigest.getInstance("md5");
            //按照摘要对象设置的加密算法处理内容 需要参数byte[]
            digest.update(url.getBytes());
            //获取加密后的2进制
            byte[] cipher = digest.digest();
            //遍历比特数组
            for (byte b : cipher) {
                //二进制转换为16进制
                String hexStr = Integer.toHexString(b & 0xff);
                //拼接加密后结果 如果长度是1 则 前面拼接"0"
                stringBuffer.append(hexStr.length() == 1 ? "0" + hexStr : hexStr);
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return stringBuffer.toString();
    }
}
