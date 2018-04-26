package com.realdoctor.back.dal.dao;

import java.util.List;
import java.util.Map;

import com.easyway.business.framework.dao.CrudDao;
import com.realdoctor.back.dal.pojo.Codetable;

public interface CodetableDao extends CrudDao<Codetable> {

    public List<Map<String, Object>> findSectionList(String name);
}
