package com.ooteco.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by shenyu on 2018/02/12.
 */
public class MD5Util {
    private static MessageDigest digest = newDigest();

    private static MessageDigest newDigest() {
        try {
            return MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static String encode(byte[] bytes) {
        return byte2Hex(digest.digest(bytes));
    }

    public static String encode(String origin) {
        return byte2Hex(digest.digest(origin.getBytes()));
    }

    public static String encode(String origin, String charset) {
        try {
            return byte2Hex(digest.digest(origin.getBytes(charset)));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "";
        }
    }

    private static String byte2Hex(byte b[]) {
        StringBuffer sb = new StringBuffer(b.length * 2);
        for (int i = 0; i < b.length; i++) {
            int v = b[i] & 255;
            if (v < 16)
                sb.append('0');
            sb.append(Integer.toHexString(v));
        }
        return sb.toString().toLowerCase();
    }

}
