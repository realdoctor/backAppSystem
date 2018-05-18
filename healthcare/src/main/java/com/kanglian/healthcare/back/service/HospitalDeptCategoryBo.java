package com.kanglian.healthcare.back.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.easyway.business.framework.bo.CrudBo;
import com.kanglian.healthcare.back.dal.dao.HospitalDeptCategoryDao;
import com.kanglian.healthcare.back.dal.pojo.HospitalDeptCategory;
import com.kanglian.healthcare.exception.DBException;

@Service
public class HospitalDeptCategoryBo extends CrudBo<HospitalDeptCategory, HospitalDeptCategoryDao> {
    /**
     * 查询医疗机构各科室分类表
     * 
     * @param sid
     * @return
     */
    public List<HospitalDeptCategory> getHospitalDeptList() {
        try {
            return this.dao.getHospitalDeptList();
        } catch (Exception ex) {
            throw new DBException(ex);
        }
    }

    public List<HospitalDeptCategory> getHospitalDeptCategory(Integer sid) {
        try {
            return this.dao.getHospitalDeptCategory(sid);
        } catch (Exception ex) {
            throw new DBException(ex);
        }
    }
}
