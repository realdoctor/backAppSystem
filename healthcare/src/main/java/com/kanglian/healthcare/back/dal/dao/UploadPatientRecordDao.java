package com.kanglian.healthcare.back.dal.dao;

import com.kanglian.healthcare.back.dal.pojo.UploadPatientRecord;
import com.easyway.business.framework.dao.CrudDao;

public interface UploadPatientRecordDao extends CrudDao<UploadPatientRecord> {
    public UploadPatientRecord getByUserId(Integer userId);

    public int updateByUserId(UploadPatientRecord uploadPatientRecord);
}
