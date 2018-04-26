package com.realdoctor.back.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.easyway.business.framework.bo.CrudBo;
import com.realdoctor.back.dal.dao.CodetableDao;
import com.realdoctor.back.dal.pojo.Codetable;
import com.realdoctor.exception.DBException;

@Service
public class CodetableBo extends CrudBo<Codetable, CodetableDao> {

    public List<Map<String, Object>> findSectionList(String name) {
        try {
            return this.dao.findSectionList(name);
        } catch (Exception ex) {
            throw new DBException(ex);
        }
    }

}
