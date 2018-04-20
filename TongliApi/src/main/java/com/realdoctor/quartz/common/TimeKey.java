package com.realdoctor.quartz.common;

import java.util.UUID;

import org.apache.logging.log4j.ThreadContext;

/**
 * 日志唯一时间标识(一次web请求，产生唯一性key)
 * 
 * @author xl.liu
 */
public class TimeKey {

    /** 时间戳标记  */
    private final static String KEY_PREFIX = "【TimeKey=";

    /** 时间戳标记  */
    private final static String KEY_SUFFIX = "】";

    /**
     * 开始一个新的线程日志记录，在一个处理线程的开始处使用（即外部调用入口）
     */
    public static void start() {
        // 清空栈信息
        ThreadContext.clearAll();
        ThreadContext.push(createTimeKey());
    }

    /**
     * 获取当前线程事件搓
     * 
     * @return
     */
    public static String getTimeKey() {
        return ThreadContext.peek();
    }

    /**
     * 创建事件搓 
     * 
     * @return
     */
    public static String createTimeKey() {
        // 清空栈信息
        String timeKey = KEY_PREFIX + UUID.randomUUID().toString().replace("-", "") + KEY_SUFFIX;
        return timeKey;
    }

    /**
     * 清空时间key
     */
    public static void clear() {
        ThreadContext.pop();
    }
}
