package com.kanglian.healthcare.back.dao;

import com.easyway.business.framework.dao.CrudDao;
import com.kanglian.healthcare.back.pojo.HospitalDeptCategoryItem;

public interface HospitalDeptCategoryItemDao extends CrudDao<HospitalDeptCategoryItem> {

    public void updateNew(HospitalDeptCategoryItem pojo);
}
