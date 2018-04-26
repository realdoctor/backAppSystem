package com.realdoctor.util;

import java.util.Calendar;
import java.util.Map;
import java.util.Set;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 单内存缓存管理器 可设置过期时间，单位毫秒 使用Timer定时清理过期数据，每分钟清理一次，可修改清理周期
 * 
 * @author xl.liu
 */
public class CacheManager {

    @SuppressWarnings("rawtypes")
    private final static Map<String, Cache>       cache = new ConcurrentHashMap<>();
    private final static ScheduledExecutorService exec  = Executors.newScheduledThreadPool(1);

    /**
     * 启动定时任务清理过期缓存，避免内存溢出
     */
    static {
        // 从现在开始1分钟之后，每隔10分钟钟执行一次job
        exec.scheduleWithFixedDelay(new CleanTask(cache), 1, 10, TimeUnit.MINUTES);
    }

    /**
     * 设置缓存，不过期
     * 
     * @param key
     * @param t
     */
    public static <T> void set(String key, T t) {
        cache.put(key, new Cache<>(t, 0));
    }

    /**
     * 设置缓存，指定过期时间expire(单位毫秒)
     * 
     * @param key
     * @param t
     * @param expire 过期时间
     */
    public static <T> void set(String key, T t, long expire) {
        cache.put(key, new Cache<>(t, expire));
    }

    /**
     * 根据key获取指定缓存
     * 
     * @param key
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T get(String key) {
        Cache<T> data = cache.get(key);
        if (null == data) {
            return null;
        }
        if (data.isExpire()) {
            remove(key);
            return null;
        }
        return data.getData();
    }

    /**
     * 移除指定key缓存
     * 
     * @param key
     */
    public static void remove(String key) {
        cache.remove(key);
    }

    /**
     * 移除所有缓存
     */
    public static void removeAll() {
        cache.clear();
    }

    private static class Cache<T> {

        // 缓存数据
        private T    data;
        // 过期时间(单位，毫秒)
        private long expireTime;

        public Cache(T t, long expire){
            this.data = t;
            if (expire <= 0) {
                this.expireTime = 0L;
            } else {
                this.expireTime = Calendar.getInstance().getTimeInMillis() + expire;
            }
        }

        /**
         * 判断缓存数据是否过期
         * 
         * @return true表示过期，false表示未过期
         */
        public boolean isExpire() {
            if (expireTime <= 0) {
                return false;
            }
            if (expireTime > Calendar.getInstance().getTimeInMillis()) {
                return false;
            }
            return true;
        }

        public T getData() {
            return data;
        }
    }

    /**
     * 清理过期数据定时任务
     * 
     * @author xl.liu
     */
    private static class CleanTask extends TimerTask {

        @SuppressWarnings("rawtypes")
        Map<String, Cache> cache;

        @SuppressWarnings("rawtypes")
        public CleanTask(Map<String, Cache> cache){
            this.cache = cache;
        }

        @Override
        public void run() {
            Set<String> keys = cache.keySet();
            for (String key : keys) {
                Cache<?> data = cache.get(key);
                if (data.expireTime <= 0) {
                    continue;
                }
                if (data.expireTime > Calendar.getInstance().getTimeInMillis()) {
                    continue;
                }
                cache.remove(key);
            }
        }
    }
}
