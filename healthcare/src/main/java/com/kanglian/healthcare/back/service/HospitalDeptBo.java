package com.kanglian.healthcare.back.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.easyway.business.framework.bo.CrudBo;
import com.easyway.business.framework.mybatis.query.ConditionQuery;
import com.kanglian.healthcare.back.dal.dao.HospitalDeptDao;
import com.kanglian.healthcare.back.dal.pojo.HospitalDept;
import com.kanglian.healthcare.exception.DBException;

@Service
public class HospitalDeptBo extends CrudBo<HospitalDept, HospitalDeptDao> {

    /**
     * 按医院、科室、疾病、获取本院医生
     * 
     * @param hospitalId
     * @param deptName
     * @return
     */
    public List<HospitalDept> findDeptDoctor(ConditionQuery query) {
        try {
            return this.dao.findDeptDoctor(query);
        } catch (Exception ex) {
            throw new DBException(ex);
        }
    }
}
