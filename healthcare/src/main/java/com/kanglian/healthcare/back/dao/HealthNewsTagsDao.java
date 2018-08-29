package com.kanglian.healthcare.back.dao;

import java.util.List;
import com.easyway.business.framework.dao.CrudDao;
import com.kanglian.healthcare.back.pojo.HealthNewsTags;

public interface HealthNewsTagsDao extends CrudDao<HealthNewsTags> {

    public List<HealthNewsTags> getByNewsId(Integer newsId);
}
