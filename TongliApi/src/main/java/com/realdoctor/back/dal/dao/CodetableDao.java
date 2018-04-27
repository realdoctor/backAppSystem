package com.realdoctor.back.dal.dao;

import java.util.List;

import com.easyway.business.framework.dao.CrudDao;
import com.realdoctor.back.dal.pojo.Codetable;

public interface CodetableDao extends CrudDao<Codetable> {

    public List<Codetable> findSectionList(String name);
}
