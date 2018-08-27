package com.kanglian.healthcare.util;

import java.security.MessageDigest;
import org.apache.commons.codec.digest.DigestUtils;

public class MD5Util {

    private static final String hexDigits[] =
            {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F"};

    /**
     * md5加密
     * 
     * @return
     */
    public static String encrypt(String data) {
        return encrypt(data, null);
    }

    public static String encrypt(String origin, String charset) {
        String resultString = null;
        try {
            resultString = new String(origin);
            MessageDigest md = MessageDigest.getInstance("MD5");
            if (charset == null || "".equals(charset))
                resultString = byteArrayToHexString(md.digest(resultString.getBytes()));
            else
                resultString = byteArrayToHexString(md.digest(resultString.getBytes(charset)));
        } catch (Exception exception) {
        }
        return resultString;
    }

    private static String byteArrayToHexString(byte ch[]) {
        StringBuffer resultSb = new StringBuffer();
        for (int i = 0; i < ch.length; i++)
            resultSb.append(byteToHexString(ch[i]));

        return resultSb.toString();
    }

    private static String byteToHexString(byte ch) {
        return hexDigits[ch >> 4 & 0xF] + hexDigits[ch & 0xF];
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        System.err.println(MD5Util.encrypt("123456"));
        String sign = DigestUtils.sha1Hex("123456");
        System.err.println(sign);
    }
}
