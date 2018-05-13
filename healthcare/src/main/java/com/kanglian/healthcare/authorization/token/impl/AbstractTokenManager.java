package com.kanglian.healthcare.authorization.token.impl;

import com.kanglian.healthcare.authorization.token.TokenManager;

/**
 * Token管理的基础类
 * 
 * @author xl.liu
 */
public abstract class AbstractTokenManager implements TokenManager {

    protected int     tokenExpireSeconds        = 7 * 24 * 3600;

    protected boolean flushExpireAfterOperation = true;

    public void setTokenExpireSeconds(int tokenExpireSeconds) {
        this.tokenExpireSeconds = tokenExpireSeconds;
    }

    public void setFlushExpireAfterOperation(boolean flushExpireAfterOperation) {
        this.flushExpireAfterOperation = flushExpireAfterOperation;
    }

    @Override
    public void delRelationshipByKey(String key) {
        delRelationshipByKey(key);
    }

    @Override
    public String getKey(String token) {
        String key = getKeyByToken(token);
        // 根据设置，在每次有效操作后刷新过期时间
        if (key != null && flushExpireAfterOperation) {
            flushExpireAfterOperation(key, token);
        }
        return key;
    }

    /**
     * 通过Key获得Token
     * 
     * @param key
     * @return
     */
    public abstract String getToken(String key);
    
    /**
     * 通过Token获得Key
     * 
     * @param token
     * @return
     */
    public abstract String getKeyByToken(String token);

    /**
     * 在操作后刷新Token的过期时间
     * 
     * @param key
     * @param token
     */
    protected abstract void flushExpireAfterOperation(String key, String token);
}
