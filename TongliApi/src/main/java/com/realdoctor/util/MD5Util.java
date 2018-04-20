package com.realdoctor.util;

import java.security.MessageDigest;
import java.text.MessageFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MD5Util {

    private final static Logger logger = LoggerFactory.getLogger(MD5Util.class);

    /**
     * md5加密
     * 
     * @return
     */
    public static String encrypt(String data) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
            md.update(data.toString().getBytes());
            return bytesToHex(md.digest());
        } catch (Exception ex) {
            logger.info(MessageFormat.format("加密{0}异常", data), ex);
        }
        return null;
    }

    private static String bytesToHex(byte[] ch) {
        StringBuffer ret = new StringBuffer("");
        for (int i = 0; i < ch.length; i++)
            ret.append(byteToHex(ch[i]));
        return ret.toString();
    }

    /**
     * 字节转换为16进制字符串
     */
    private static String byteToHex(byte ch) {
        String str[] = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F" };
        return str[ch >> 4 & 0xF] + str[ch & 0xF];
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        System.err.println(MD5Util.encrypt("123456"));
    }
}
