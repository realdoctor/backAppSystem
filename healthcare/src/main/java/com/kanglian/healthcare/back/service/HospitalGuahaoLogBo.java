package com.kanglian.healthcare.back.service;

import com.easyway.business.framework.bo.CrudBo;
import com.kanglian.healthcare.back.dal.pojo.HospitalAddress;
import com.kanglian.healthcare.back.dal.pojo.HospitalGuahaoLog;
import com.kanglian.healthcare.exception.DBException;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.kanglian.healthcare.back.dal.dao.HospitalGuahaoLogDao;

@Service
public class HospitalGuahaoLogBo extends CrudBo<HospitalGuahaoLog, HospitalGuahaoLogDao> {

    @Autowired
    private HospitalAddressBo hospitalAddressBo;
    
    /**
     * 获取挂号单列表
     * 
     * @param hospitalGuahaoLog
     * @return
     */
    public List<HospitalGuahaoLog> getGuahaoLog(HospitalGuahaoLog hospitalGuahaoLog) {
        try {
            return this.dao.getGuahaoLog(hospitalGuahaoLog);
        } catch (Exception ex) {
            throw new DBException(ex);
        }
    }

    /**
     * 我的预约
     * 
     * @param userId
     * @return
     */
    public List<Map<String, String>> myGuahaoOrder(final String userId) {
        try {
            return this.dao.myGuahaoOrder(userId);
        } catch (Exception ex) {
            throw new DBException(ex);
        }
    }
    
    /**
     * 生成预约记录
     * 
     * @param hospitalGuahaoLog
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public void fastorder(HospitalGuahaoLog hospitalGuahaoLog) {
        try {
            this.dao.save(hospitalGuahaoLog);
            HospitalAddress hospitalAddress =
                    hospitalAddressBo.get(Integer.valueOf(hospitalGuahaoLog.getHospitalId()));
            if (hospitalAddress != null) {
                // 更新预约挂号量
                hospitalAddress.setAppointmentNum(hospitalAddress.getAppointmentNum() + 1);
                hospitalAddressBo.update(hospitalAddress);
            }
        } catch (Exception ex) {
            throw new DBException(ex);
        }
    }
}
