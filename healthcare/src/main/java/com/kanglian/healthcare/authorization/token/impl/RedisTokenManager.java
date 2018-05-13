package com.kanglian.healthcare.authorization.token.impl;

import java.util.concurrent.TimeUnit;
import com.kanglian.healthcare.util.RedisCache;

/**
 * 使用Redis存储Token
 * 
 * @author xl.liu
 */
public class RedisTokenManager extends AbstractTokenManager {

    /**
     * Redis中Key的前缀
     */
    private static final String REDIS_KEY_PREFIX   = "AUTHORIZATION_KEY_";

    /**
     * Redis中Token的前缀
     */
    private static final String REDIS_TOKEN_PREFIX = "AUTHORIZATION_TOKEN_";

    private RedisCache      redisCache;

    public void setRedisCache(RedisCache redisCache) {
        this.redisCache = redisCache;
    }

    @Override
    public void createRelationship(String key, String token) {
        String oldToken = get(formatKey(key));
        if (oldToken != null) {
            delete(formatToken(oldToken));
        }
        set(formatToken(token), key, tokenExpireSeconds);
        set(formatKey(key), token, tokenExpireSeconds);
    }

    @Override
    public void delRelationshipByKey(String key) {
        String token = getToken(key);
        if (token != null) {
            delete(formatKey(key), formatToken(token));
        }
    }

    @Override
    public void delRelationshipByToken(String token) {
        String key = getKey(token);
        delete(formatKey(key), formatToken(token));
    }

    @Override
    public String getKeyByToken(String token) {
        return get(formatToken(token));
    }

    @Override
    protected void flushExpireAfterOperation(String key, String token) {
        expire(formatKey(key), tokenExpireSeconds);
        expire(formatToken(token), tokenExpireSeconds);
    }

    @Override
    public String getToken(String key) {
        return get(formatKey(key));
    }
    
    private String get(String key) {
        Object obj = redisCache.getCacheObject(key);
        if (obj == null) {
            return null;
        }
        return String.valueOf(obj);
    }

    private void set(String key, String value, int expireSeconds) {
        redisCache.setCacheObject(key, value, Long.valueOf(expireSeconds), TimeUnit.SECONDS);
    }

    private void expire(String key, int seconds) {
        redisCache.expire(key, seconds, TimeUnit.SECONDS);
    }

    private void delete(String... keys) {
        for (String key : keys) {
            redisCache.delete(key);
        }
    }

    private String formatKey(String key) {
        return REDIS_KEY_PREFIX.concat(key);
    }

    private String formatToken(String token) {
        return REDIS_TOKEN_PREFIX.concat(token);
    }
}
