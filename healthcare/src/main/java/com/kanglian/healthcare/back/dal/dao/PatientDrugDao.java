package com.kanglian.healthcare.back.dal.dao;

import java.util.List;
import com.easyway.business.framework.dao.CrudDao;
import com.kanglian.healthcare.back.dal.pojo.PatientDrug;

public interface PatientDrugDao extends CrudDao<PatientDrug> {

    public List<PatientDrug> getByPatientDiagId(Integer patientDiagId);
}
