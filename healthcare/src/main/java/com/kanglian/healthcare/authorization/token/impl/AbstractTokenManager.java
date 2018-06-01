package com.kanglian.healthcare.authorization.token.impl;

import com.kanglian.healthcare.authorization.token.TokenManager;

/**
 * Token管理的基础类
 * 
 * @author xl.liu
 */
public abstract class AbstractTokenManager implements TokenManager {

    protected boolean flushExpireAfterOperation = true;
    protected boolean singleToken               = false;

    public void setFlushExpireAfterOperation(boolean flushExpireAfterOperation) {
        this.flushExpireAfterOperation = flushExpireAfterOperation;
    }

    public void setSingleToken(boolean singleToken) {
        this.singleToken = singleToken;
    }

    @Override
    public void delRelationshipByKey(String key) {
        delSingleRelationshipByKey(key);
    }

    /**
     * 一个用户只能绑定一个Token时通过Key删除关联关系
     * 
     * @param key
     */
    protected abstract void delSingleRelationshipByKey(String key);

    @Override
    public void createRelationship(String key, String token) {
        // 根据设置的每个用户是否只允许绑定一个Token
        if (singleToken) {
            createSingleRelationship(key, token);
        } else {
            createMultipleRelationship(key, token);
        }
    }

    /**
     * 一个用户可以绑定多个Token时创建关联关系
     * 
     * @param key
     * @param token
     */
    protected abstract void createMultipleRelationship(String key, String token);

    /**
     * 一个用户只能绑定一个Token时创建关联关系
     * 
     * @param key
     * @param token
     */
    protected abstract void createSingleRelationship(String key, String token);

    /**
     * 在操作后刷新Token的过期时间
     * 
     * @param key
     * @param token
     */
    protected abstract void flushExpireAfterOperation(String key, String token);

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
     * 通过Token获得Key
     * 
     * @param token
     * @return
     */
    protected abstract String getKeyByToken(String token);

}
