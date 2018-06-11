package com.kanglian.healthcare.authorization.token.impl;

import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.kanglian.healthcare.authorization.AuthConfig;
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
    protected void createSingleRelationship(String key, String token) {
        String oldToken = get(formatKey(key));
        if (oldToken != null) {
            delete(formatToken(oldToken));
        }
        set(formatToken(token), key, AuthConfig.TOKEN_EXPIRES_SECONDS);
        set(formatKey(key), token, AuthConfig.TOKEN_EXPIRES_SECONDS);
    }

    @Override
    protected void createMultipleRelationship(String key, String token) {
        set(formatToken(token), key, AuthConfig.TOKEN_EXPIRES_SECONDS);
    }

    @Override
    protected void delSingleRelationshipByKey(String key) {
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
        if (singleToken) {
            expire(formatKey(key), AuthConfig.TOKEN_EXPIRES_SECONDS);
        }
        expire(formatToken(token), AuthConfig.TOKEN_EXPIRES_SECONDS);
    }

    /**
     * @param key
     * @return
     */
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

    public String getToken(String key) {
        return get(formatKey(key));
    }

    public String formatKey(String key) {
        return AuthConfig.formatKey(key);
    }

    public String formatToken(String token) {
        return AuthConfig.formatToken(token);
    }

}
