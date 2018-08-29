package com.kanglian.healthcare.back.service;

import com.easyway.business.framework.bo.CrudBo;
import org.springframework.stereotype.Service;
import com.kanglian.healthcare.back.dao.HospitalDoctorDao;
import com.kanglian.healthcare.back.pojo.HospitalDoctor;
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
