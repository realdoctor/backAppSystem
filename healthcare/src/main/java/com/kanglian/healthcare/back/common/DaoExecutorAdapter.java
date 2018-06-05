package com.kanglian.healthcare.back.common;

import java.util.List;

/**
 * Dao执行适配器
 * 
 * @author xl.liu
 */
public abstract class DaoExecutorAdapter implements IDaoExecutor {
    /**
     * 执行操作
     */
    @Override
    public void execute() throws Exception {}

    /**
     * 选择单个实体
     */
    @Override
    public <T> T selectOne() throws Exception {
        return null;
    }

    /**
     * 选择实体集合
     */
    @Override
    public <T> List<T> selectList() throws Exception {
        return null;
    }
}
