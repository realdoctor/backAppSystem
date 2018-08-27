package com.kanglian.healthcare.back.dal.dao;

import com.easyway.business.framework.dao.CrudDao;
import com.kanglian.healthcare.back.dal.pojo.HospitalDeptCategoryItem;

public interface HospitalDeptCategoryItemDao extends CrudDao<HospitalDeptCategoryItem> {

    public void updateNew(HospitalDeptCategoryItem pojo);
}
