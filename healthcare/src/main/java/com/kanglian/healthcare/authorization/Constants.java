package com.kanglian.healthcare.authorization;

/**
 * 全局配置常量
 * 
 * @author xl.liu
 */
public class Constants {
    /**
     * 存储当前登录用户id的字段名
     */
    public static final String CURRENT_USER_ID       = "CURRENT_USER_ID";

    /**
     * token有效期
     */
    public static final int    TOKEN_EXPIRES_SECONDS = 7 * 24 * 3600;

    /**
     * 存放Authorization的header字段
     */
    public static final String AUTHORIZATION         = "Authorization";

    /**
     * Redis中Key的前缀
     */
    public static final String REDIS_KEY_PREFIX      = "AUTHORIZATION_KEY_";

    /**
     * Redis中Token的前缀
     */
    public static final String REDIS_TOKEN_PREFIX    = "AUTHORIZATION_TOKEN_";
    
    public static String formatKey(String key) {
        return Constants.REDIS_KEY_PREFIX.concat(key);
    }

    public static String formatToken(String token) {
        return Constants.REDIS_TOKEN_PREFIX.concat(token);
    }
}
