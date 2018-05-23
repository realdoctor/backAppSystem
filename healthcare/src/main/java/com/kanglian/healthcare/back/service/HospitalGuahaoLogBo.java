package com.kanglian.healthcare.back.service;

import com.easyway.business.framework.bo.CrudBo;
import com.kanglian.healthcare.back.dal.pojo.HospitalGuahaoLog;
import com.kanglian.healthcare.exception.DBException;
import java.util.List;
import org.springframework.stereotype.Service;
import com.kanglian.healthcare.back.dal.dao.HospitalGuahaoLogDao;

@Service
public class HospitalGuahaoLogBo extends CrudBo<HospitalGuahaoLog,HospitalGuahaoLogDao> {

    /**
     * 获取挂号单列表
     * 
     * @param hospitalGuahaoLog
     * @return
     */
    public List<HospitalGuahaoLog> getGuahaoLog(HospitalGuahaoLog hospitalGuahaoLog){
        try {
            return this.dao.getGuahaoLog(hospitalGuahaoLog);
        } catch (Exception ex) {
            throw new DBException(ex);
        }
    }
}
