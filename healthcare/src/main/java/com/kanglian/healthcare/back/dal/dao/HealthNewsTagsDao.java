package com.kanglian.healthcare.back.dal.dao;

import com.kanglian.healthcare.back.dal.pojo.HealthNewsTags;
import com.easyway.business.framework.dao.CrudDao;

public interface HealthNewsTagsDao extends CrudDao<HealthNewsTags> {

    public HealthNewsTags getByNewsId(Integer newsId);
}
