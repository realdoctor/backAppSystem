package com.kanglian.healthcare.back.common;

import java.util.List;
import com.easyway.business.framework.mybatis.query.ConditionQuery;

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
    
    /**
     * 查询分页列表
     * 
     * @param query
     * @return
     * @throws Exception
     */
    <T> List<T> pagingList(ConditionQuery query) throws Exception;
}
