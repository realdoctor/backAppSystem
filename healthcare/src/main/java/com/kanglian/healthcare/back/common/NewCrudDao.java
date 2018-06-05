package com.kanglian.healthcare.back.common;

import java.util.List;

import com.easyway.business.framework.dao.CrudDao;
import com.easyway.business.framework.mybatis.query.ConditionQuery;
import com.easyway.business.framework.pojo.BasePojo;

public interface NewCrudDao<T extends BasePojo> extends CrudDao<T> {

    /**
     * 根据条件查询数据库记录列表
     * 
     * @param query
     * @return
     */
    public List<T> frontList(ConditionQuery query);

    /**
     * 根据条件查询数据库记录条数
     * 
     * @param query
     * @return
     */
    public int frontListCnt(ConditionQuery query);
}
