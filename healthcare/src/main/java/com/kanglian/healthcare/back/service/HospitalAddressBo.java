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
     * 搜索医院、医生、科室、疾病
     * 
     * @return 医院 | 医生
     */
    public List<HospitalAddress> queryForHospitalAndDoctor(ConditionQuery query) {
        try {
            return dao.queryForHospitalAndDoctor(query);
        } catch (Exception ex) {
            throw new DBException(ex);
        }
    }
}
