package com.kanglian.healthcare.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import org.apache.commons.lang.RandomStringUtils;

public final class GeneralKey {

    public static String getGeneralKey() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().replaceAll("-", "");
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
    public static String getOrderNo() {
        int hashCodeV = UUID.randomUUID().toString().hashCode();
        if (hashCodeV < 0) {// 有可能是负数
            hashCodeV = -hashCodeV;
        }
        String machineId = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String orderId = machineId + String.format("%014d", hashCodeV);
        System.out.println(orderId);
        return orderId;
    }
}
