package com.kanglian.healthcare.back.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.easyway.business.framework.bo.CrudBo;
import com.easyway.business.framework.mybatis.query.ConditionQuery;
import com.kanglian.healthcare.back.dal.dao.HospitalAddressDao;
import com.kanglian.healthcare.back.dal.pojo.HospitalAddress;
import com.kanglian.healthcare.exception.DBException;

@Service
public class HospitalAddressBo extends CrudBo<HospitalAddress, HospitalAddressDao> {

    /**
     * 按条件查询
     * 
     * @return
     */
    public List<HospitalAddress> queryForList(ConditionQuery query) {
        try {
            return dao.queryForList(query);
        } catch (Exception ex) {
            throw new DBException(ex);
        }
    }
}
