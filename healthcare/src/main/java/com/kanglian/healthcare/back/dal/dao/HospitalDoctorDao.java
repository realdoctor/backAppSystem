package com.kanglian.healthcare.back.dal.dao;

import com.kanglian.healthcare.back.dal.pojo.HospitalDoctor;
import com.easyway.business.framework.dao.CrudDao;

public interface HospitalDoctorDao extends CrudDao<HospitalDoctor> {

    public HospitalDoctor getHospitalDoctor(HospitalDoctor doctor);
}
