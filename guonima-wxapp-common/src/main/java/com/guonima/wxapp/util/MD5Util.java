package com.guonima.wxapp.util;

import java.security.MessageDigest;

/**
 * @author guonima
 * @create 2017-04-13 15:39
 */
public class MD5Util {

    private static final String encryptModel = "MD5";

    /**
     * 32位小写md5加密
     *
     * @param str
     * @return
     */
    public static String md5(String str) {
        return encrypt(encryptModel, str);
    }

    public static String encrypt(String algorithm, String str) {
        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            md.update(str.getBytes());
            StringBuffer sb = new StringBuffer();
            byte[] bytes = md.digest();
            for (int i = 0; i < bytes.length; i++) {
                int b = bytes[i] & 0xFF;
                if (b < 0x10) {
                    sb.append('0');
                }
                sb.append(Integer.toHexString(b));
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
