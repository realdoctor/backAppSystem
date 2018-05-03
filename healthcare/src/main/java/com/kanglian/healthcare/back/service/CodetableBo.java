package com.kanglian.healthcare.back.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.easyway.business.framework.bo.CrudBo;
import com.kanglian.healthcare.back.dal.dao.CodetableDao;
import com.kanglian.healthcare.back.dal.pojo.Codetable;
import com.kanglian.healthcare.exception.DBException;

@Service
public class CodetableBo extends CrudBo<Codetable, CodetableDao> {

    public List<Codetable> findSectionList(String name) {
        try {
            return this.dao.findSectionList(name);
        } catch (Exception ex) {
            throw new DBException(ex);
        }
    }

}
