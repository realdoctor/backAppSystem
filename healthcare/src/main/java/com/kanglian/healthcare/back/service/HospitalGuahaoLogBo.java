package com.kanglian.healthcare.back.service;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.easyway.business.framework.bo.CrudBo;
import com.easyway.business.framework.pojo.Grid;
import com.kanglian.healthcare.back.common.DaoExecutorAdapter;
import com.kanglian.healthcare.back.common.DaoTemplate;
import com.kanglian.healthcare.back.dal.dao.HospitalGuahaoLogDao;
import com.kanglian.healthcare.back.dal.pojo.Hospital;
import com.kanglian.healthcare.back.dal.pojo.HospitalGuahaoLog;
import com.kanglian.healthcare.exception.DBException;

@Service
public class HospitalGuahaoLogBo extends CrudBo<HospitalGuahaoLog, HospitalGuahaoLogDao> {

    @Autowired
    private HospitalBo hospitalBo;
    
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
    public List<Map<String, Object>> myDoctorOrder(final Integer userId) {
        try {
            return this.dao.myGuahaoOrder(userId);
        } catch (Exception ex) {
            throw new DBException(ex);
        }
    }
    
    /**
     * 医生的被预约
     * 
     * @param userId
     * @return
     */
    public Grid myPatientOrder(final Grid grid) {
        return DaoTemplate.pagingList(new DaoExecutorAdapter() {

            @Override
            @SuppressWarnings("unchecked")
            public List<Map<String, Object>> selectList() throws Exception {
                return getDao().myPatientOrder(grid.buildConditionQuery());
            }

        }, grid.getPageNum(), grid.getPageSize());
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
            Hospital hospital = hospitalBo.get(Integer.valueOf(hospitalGuahaoLog.getHospitalId()));
            if (hospital != null) {
                // 更新预约挂号量
                hospital.setAppointmentNum(hospital.getAppointmentNum() + 1);
                hospitalBo.update(hospital);
            }
        } catch (Exception ex) {
            throw new DBException(ex);
        }
    }
}
