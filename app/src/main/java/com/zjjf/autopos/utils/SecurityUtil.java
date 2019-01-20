package com.zjjf.autopos.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Yekai on 2017/6/5.
 */

public class SecurityUtil {


    public static void main(String[] args) {
        String s = md5_32Bit("123");
        String s1 = md5_16Bit("123");
        System.out.println("32位加密后的结果:" + s);
        System.out.println("16位加密后的结果:" + s1);
    }

    public static String md5_32Bit(String str) {
        if (str == null || "".equals(str)) {
            throw new RuntimeException("MD5 string cannot be empty");
        }
        byte[] hash;
        try {
            hash = MessageDigest.getInstance("MD5").digest(str.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Huh, MD5 should be supported?", e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Huh, UTF-8 should be supported?", e);
        }
        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10) hex.append("0");
            hex.append(Integer.toHexString(b & 0xFF));
        }
        return hex.toString();
    }

    public static String md5_16Bit(String str) {
        return md5_32Bit(str).substring(8, 24);
    }
}
