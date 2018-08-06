package com.kanglian.healthcare.back.service;

import com.easyway.business.framework.bo.CrudBo;
import com.kanglian.healthcare.back.dal.dao.HospitalDoctorDao;
import org.springframework.stereotype.Service;
import com.kanglian.healthcare.back.dal.pojo.HospitalDoctor;
import com.kanglian.healthcare.exception.DBException;

@Service
public class HospitalDoctorBo extends CrudBo<HospitalDoctor,HospitalDoctorDao> {

    public HospitalDoctor getHospitalDoctor(Integer hospitalId, Integer deptId, String doctorCode) {
        try {
            HospitalDoctor doctor = new HospitalDoctor();
            doctor.setHospitalId(hospitalId);
            doctor.setDeptId(deptId);
            doctor.setDoctorCode(doctorCode);
            return this.dao.getHospitalDoctor(doctor);
        } catch (Exception ex) {
            throw new DBException(ex);
        }
    }
    
    public HospitalDoctor getHospitalDoctor(HospitalDoctor doctor) {
        try {
            return this.dao.getHospitalDoctor(doctor);
        } catch (Exception ex) {
            throw new DBException(ex);
        }
    }
}
