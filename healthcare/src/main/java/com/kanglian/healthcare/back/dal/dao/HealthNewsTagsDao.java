package com.kanglian.healthcare.back.dal.dao;

import com.kanglian.healthcare.back.dal.pojo.HealthNewsTags;
import java.util.List;
import com.easyway.business.framework.dao.CrudDao;

public interface HealthNewsTagsDao extends CrudDao<HealthNewsTags> {

    public List<HealthNewsTags> getByNewsId(Integer newsId);
}
