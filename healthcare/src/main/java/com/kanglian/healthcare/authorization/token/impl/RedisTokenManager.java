package com.kanglian.healthcare.authorization.token.impl;

import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.kanglian.healthcare.authorization.Constants;
import com.kanglian.healthcare.util.RedisCacheManager;

/**
 * 使用Redis存储Token
 * 
 * @author xl.liu
 */
@Component
public class RedisTokenManager extends AbstractTokenManager {

    private RedisCacheManager redisCacheManager;

    @Autowired
    public void setRedisCacheManager(RedisCacheManager redisCacheManager) {
        this.redisCacheManager = redisCacheManager;
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
        Object obj = redisCacheManager.getCacheObject(key);
        if (obj == null) {
            return null;
        }
        return String.valueOf(obj);
    }

    private void set(String key, String value, int expireSeconds) {
        redisCacheManager.setCacheObject(key, value, Long.valueOf(expireSeconds), TimeUnit.SECONDS);
    }

    private void expire(String key, int seconds) {
        redisCacheManager.expire(key, seconds, TimeUnit.SECONDS);
    }

    private void delete(String... keys) {
        for (String key : keys) {
            redisCacheManager.delete(key);
        }
    }

    private String formatKey(String key) {
        return Constants.REDIS_KEY_PREFIX.concat(key);
    }

    private String formatToken(String token) {
        return Constants.REDIS_TOKEN_PREFIX.concat(token);
    }
}
