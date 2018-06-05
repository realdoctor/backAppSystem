package com.kanglian.healthcare.back.common;

import java.util.List;

/**
 * DAO执行器接口
 * 
 * @author xl.liu
 */
public interface IDaoExecutor {
    /**
     * 执行操作
     * 
     * @throws Exception
     */
    void execute() throws Exception;

    /**
     * 选择单个实体
     * 
     * @return
     * @throws Exception
     */
    <T> T selectOne() throws Exception;

    /**
     * 选择实体集合
     * 
     * @return
     * @throws Exception
     */
    <T> List<T> selectList() throws Exception;
}
