package com.kanglian.healthcare.back.dao;

import com.easyway.business.framework.dao.CrudDao;
import com.kanglian.healthcare.back.pojo.HospitalDoctor;

public interface HospitalDoctorDao extends CrudDao<HospitalDoctor> {

    public HospitalDoctor getHospitalDoctor(HospitalDoctor doctor);
}
