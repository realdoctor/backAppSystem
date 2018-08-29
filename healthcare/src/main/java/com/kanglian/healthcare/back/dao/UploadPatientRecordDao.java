package com.kanglian.healthcare.back.dao;

import com.easyway.business.framework.dao.CrudDao;
import com.kanglian.healthcare.back.pojo.UploadPatientRecord;

public interface UploadPatientRecordDao extends CrudDao<UploadPatientRecord> {
    public UploadPatientRecord getByUserId(Integer userId);

    public int updateByUserId(UploadPatientRecord uploadPatientRecord);
}
