package com.kanglian.healthcare.back.service;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import com.easyway.business.framework.bo.CrudBo;
import com.easyway.business.framework.mybatis.query.ConditionQuery;
import com.kanglian.healthcare.back.dal.dao.HospitalDeptDao;
import com.kanglian.healthcare.back.dal.pojo.HospitalDept;
import com.kanglian.healthcare.exception.DBException;

@Service
public class HospitalDeptBo extends CrudBo<HospitalDept, HospitalDeptDao> {

    /**
     * 按医院、科室、疾病、获取本院医生[按专家预约]
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

    /**
     * 按医院、科室、疾病获取本院医生[按日期预约]
     * 
     * @param query
     * @return
     */
    public List<HospitalDept> findOrderDateDoctor(ConditionQuery query) {
        try {
            return this.dao.findOrderDateDoctor(query);
        } catch (Exception ex) {
            throw new DBException(ex);
        }
    }
    
    /**
     * 按医院、科室、疾病获取本院医生[按日期预约]
     * 
     * @param query
     * @return
     */
    public List<Map<String, String>> findWorkingDay(ConditionQuery query) {
        try {
            return this.dao.findWorkingDay(query);
        } catch (Exception ex) {
            throw new DBException(ex);
        }
    }
    
    public HospitalDept getHospitalDept(HospitalDept dept) {
        try {
            return this.dao.getHospitalDept(dept);
        } catch (Exception ex) {
            throw new DBException(ex);
        }
    }
    
    public HospitalDept getHospitalDept(Integer hospitalId, String deptCode) {
        try {
            HospitalDept dept = new HospitalDept();
            dept.setHospitalId(hospitalId);
            dept.setDeptCode(deptCode);
            return this.dao.getHospitalDept(dept);
        } catch (Exception ex) {
            throw new DBException(ex);
        }
    }
}
