package com.kanglian.healthcare.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 日志工具类
 * 
 * @author xl.liu
 */
public final class LogUtil {
    /** logger */
    public static final String  LOG_NAME_SYS     = "HealthCare.Sys";
    public static final String  LOG_NAME_MESSAGE = "HealthCare.Message";
    public static final String  LOG_NAME_TASK    = "HealthCare.Task";
    public static final String  LOG_NAME_PAYMENT = "HealthCare.Pay";

    private static final Logger message;
    private static final Logger pay;
    private static final Logger task;

    static {
        message = LoggerFactory.getLogger(LOG_NAME_MESSAGE);
        pay = LoggerFactory.getLogger(LOG_NAME_PAYMENT);
        task = LoggerFactory.getLogger(LOG_NAME_TASK);

        if (message == null) {
            System.out.println("WARNING: Can not get message logger.");
        }

        if (pay == null) {
            System.out.println("WARNING: Can not get payment logger.");
        }

        if (task == null) {
            System.out.println("WARNING: Can not get task logger.");
        }
    }

    private LogUtil() {

    }

    /**
     * 获取消息输出Log
     * 
     * @return 消息输出Log
     */
    public static Logger getMessageLogger() {
        return message;
    }

    /**
     * 获取支付输出Log
     * 
     * @return 支付输出Log
     */
    public static Logger getPayLogger() {
        return pay;
    }

    /**
     * 获取任务执行Log
     * 
     * @return 任务执行Log
     */
    public static Logger getTaskLogger() {
        return task;
    }
}
