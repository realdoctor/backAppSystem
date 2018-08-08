package com.kanglian.healthcare.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang.RandomStringUtils;

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
     * 生成不重复的20位数字
     * 
     * @return
     */
    public static String getNewId() {
        long now = System.currentTimeMillis();
        // 获取4位年份数字
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
        // 获取时间戳
        String time = dateFormat.format(now);
        // 获取三位随机数
        String ran = RandomStringUtils.randomNumeric(3);
        StringBuilder buff = new StringBuilder();
        buff.append(time);
        buff.append(now);
        buff.append(ran);
        return buff.toString();
    }
    
    /**
     * 生成唯一订单号
     * 
     * @return
     */
    public static String genOrderNo() {
        int hashCodeV = UUID.randomUUID().toString().hashCode();
        if (hashCodeV < 0) {// 有可能是负数
            hashCodeV = -hashCodeV;
        }
        String machineId = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String orderId = machineId + String.format("%015d", hashCodeV);
        System.out.println(orderId);
        return orderId;
    }
    
    /**
     * 2018072300001000880016404998
     * 前六位数（20180723）是年月日格式化：yyyyMMdd

              中间的8位数（00001000）是：00001000，固定4个0+1000

              在后两位（88）：随机生成一个两位数

              在后两位（00）：又是固定的两个0

              接下来的6位数是（164049）：时分秒的格式化HHmmss

              最后两位是（98）：又是随机生成
     * @return
     */
    public static String getOrderNo() {
        String date = new SimpleDateFormat("yyyyMMdd").format(new Date());
        String seconds = new SimpleDateFormat("HHmmss").format(new Date());
        StringBuffer buff = new StringBuffer();
        buff.append(date);
        buff.append("00001000");
        buff.append(RandomStringUtils.randomNumeric(2));
        buff.append("00");
        buff.append(seconds);
        buff.append(RandomStringUtils.randomNumeric(2));
        return buff.toString();
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

    public static Long valueOf(Long value) {
        if (value == null) {
            return new Long(0);
        }

        return value;
    }

    public static Integer valueOf(Integer value) {
        if (value == null) {
            return new Integer(0);
        }

        return value;
    }

    /**
     * 保留两位小数
     * 
     * @param value
     * @return
     */
    public static Double valueOf(Double value) {
        if (value == null) {
            return new Double(0);
        }
        BigDecimal b = new BigDecimal(value);
        double f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        return f1;
    }

    public static Long valueOf(String value) {
        if (value == null || value.length() == 0) {
            return new Long(0);
        }

        return Long.valueOf(value);
    }

    /**
     * 字符串转换为Integer
     * 
     * @param str 字符串
     * @return Integer, str为null时返回0
     */
    public static Integer getInteger(Object obj) {
        return getInteger(obj, 0);
    }

    /**
     * 字符串转换为Integer
     * 
     * @param str 字符串
     * @param def 默认值
     * @return Integer, 字符串为null时返回def
     */
    public static Integer getInteger(Object obj, int def) {
        String str = obj == null ? "" : obj.toString();

        Integer i = null;

        if (str.trim().length() == 0) {
            i = new Integer(def);
        } else {
            try {
                i = Integer.valueOf(str);
            } catch (Exception e) {
            }
        }

        return i == null ? new Integer(def) : i;
    }

    /**
     * 字符串转换为Long
     * 
     * @param str 字符串
     * @return Long, str为null时返回0
     */
    public static Long getLong(Object obj) {
        return getLong(obj, 0);
    }

    /**
     * 字符串转换为Long
     * 
     * @param str 字符串
     * @param def 默认值
     * @return Long, 字符串为null时返回def
     */
    public static Long getLong(Object obj, long def) {
        String str = obj == null ? "" : obj.toString();

        Long l = null;

        if (str.trim().length() == 0) {
            l = new Long(def);
        } else {
            try {
                l = Long.valueOf(str);
            } catch (Exception e) {
            }
        }

        return l == null ? new Long(def) : l;
    }

    /**
     * 字符串转换为Integer
     * 
     * @param str 字符串
     * @return Integer, str为null时返回0
     */
    public static int getIntegerValue(Object obj) {
        return getIntegerValue(obj, 0);
    }

    /**
     * 字符串转换为Integer
     * 
     * @param str 字符串
     * @param def 默认值
     * @return Integer, 字符串为null时返回def
     */
    public static int getIntegerValue(Object obj, int def) {
        return getInteger(obj, def).intValue();
    }

    /**
     * 字符串转换为Long
     * 
     * @param str 字符串
     * @return Long, str为null时返回0
     */
    public static long getLongValue(Object obj) {
        return getLongValue(obj, 0);
    }

    /**
     * 字符串转换为Long
     * 
     * @param str 字符串
     * @param def 默认值
     * @return Long, 字符串为null时返回def
     */
    public static long getLongValue(Object obj, long def) {
        return getLong(obj, def).longValue();
    }

    /**
     * Double保留两位小数
     * 
     * @param f
     * @return
     */
    public static String getDoubleValue(Double f) {
        DecimalFormat df = new DecimalFormat("#.00");
        return df.format(f);
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
    
    /**
     * 价格校验
     * 
     * @param price
     * @return
     */
    public static boolean checkPrice(String price) {
        Pattern pattern = Pattern.compile("^(\\-)?\\d+(\\.\\d{1,2})?$");
        Matcher match = pattern.matcher(price);
        return match.matches();
    }
    
    public static int[] stringToInt(String[] arrs) {
        int[] ints = new int[arrs.length];
        for (int i = 0; i < arrs.length; i++) {
            ints[i] = Integer.parseInt(arrs[i]);
        }
        return ints;
    }
}
