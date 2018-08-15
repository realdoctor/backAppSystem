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
    public static final String  LOG_NAME_PAYMENT = "HealthCare.Pay";
    public static final String  LOG_NAME_TASK    = "HealthCare.Task";

    private static final Logger pay;
    private static final Logger task;

    static {
        pay = LoggerFactory.getLogger(LOG_NAME_PAYMENT);
        task = LoggerFactory.getLogger(LOG_NAME_TASK);

        if (pay == null) {
            System.out.println("WARNING: Can not get pay logger.");
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
