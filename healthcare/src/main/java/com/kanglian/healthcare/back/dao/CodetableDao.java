package com.kanglian.healthcare.back.dao;

import java.util.List;

import com.easyway.business.framework.dao.CrudDao;
import com.kanglian.healthcare.back.pojo.Codetable;

public interface CodetableDao extends CrudDao<Codetable> {

    public List<Codetable> findSectionList(String name);
}
