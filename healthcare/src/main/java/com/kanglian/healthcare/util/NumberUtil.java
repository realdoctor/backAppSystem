package com.kanglian.healthcare.util;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class NumberUtil {

    /**
     * 数字转换为字符串
     * 
     * @param num 数字
     * @return 字符串,如果 num 为空, 返回空字符串
     */
    public static String num2Str(Object num) {
        String str = null;

        if (num == null) {
            str = "";
        } else {
            str = String.valueOf(num);
        }
        return str;
    }

    /**
     * 生成唯一订单号
     * 
     * @return
     */
    public static String getOrderNo() {
        int hashCodeV = UUID.randomUUID().toString().hashCode();
        if (hashCodeV < 0) {// 有可能是负数
            hashCodeV = -hashCodeV;
        }
        String machineId = new SimpleDateFormat("yyyyMMdd").format(new Date());
        String orderId = machineId + String.format("%015d", hashCodeV);
        return orderId;
    }

    /**
     * 产生num位的随机数
     * 
     * @return
     */
    public static String getRandByNum(int num) {
        String length = "1";
        for (int i = 0; i < num; i++) {
            length += "0";
        }
        Random rad = new Random();
        String result = rad.nextInt(Integer.parseInt(length)) + "";
        if (result.length() != num) {
            return getRandByNum(num);
        }
        return result;
    }

    public static double add(double v1, double v2) {// 加法
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.add(b2).doubleValue();
    }

    public static double sub(double v1, double v2) {// 减法
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.subtract(b2).doubleValue();
    }

    public static double mul(double v1, double v2) {// 乘法
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.multiply(b2).doubleValue();
    }

    public static double div(double v1, double v2) {// 除法
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.divide(b2, 3, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 检查是否是数字
     * 
     * @param value
     * @return
     */
    public static boolean isNumber(String value) {
        String patternStr = "^\\d+$";
        Pattern p = Pattern.compile(patternStr, Pattern.CASE_INSENSITIVE); // 忽略大小写;
        Matcher m = p.matcher(value);
        return m.find();
    }

}
