package com.lanjy.blog.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @项目名称：lanjyblog
 * @包名： com.lanjy.blog.util
 * @类描述：
 * @创建人：lanjy
 * @创建时间：2020/1/13
 */
public class MD5Utils {
    public static String toMD5(String plainText) {
        byte[] secretBytes = null;
        try {
            secretBytes = MessageDigest.getInstance("md5").digest(
                    plainText.getBytes());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("没有md5这个算法！");
        }
        String md5code = new BigInteger(1, secretBytes).toString(16);// 16进制数字
        // 如果生成数字未满32位，需要前面补0
        for (int i = 0; i < 32 - md5code.length(); i++) {
            md5code = "0" + md5code;
        }
        return md5code;
    }

    public static void main(String[] args) {
        System.out.println(toMD5("123456"));
    }
}
