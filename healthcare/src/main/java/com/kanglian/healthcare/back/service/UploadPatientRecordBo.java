package com.kanglian.healthcare.back.service;

import com.kanglian.healthcare.back.dal.dao.UploadPatientRecordDao;
import com.easyway.business.framework.bo.CrudBo;
import org.springframework.stereotype.Service;
import com.kanglian.healthcare.back.dal.pojo.UploadPatientRecord;
import com.kanglian.healthcare.exception.DBException;

@Service
public class UploadPatientRecordBo extends CrudBo<UploadPatientRecord, UploadPatientRecordDao> {
    public UploadPatientRecord getByUserId(Integer userId) {
        try {
            return this.dao.getByUserId(userId);
        } catch (Exception ex) {
            throw new DBException(ex);
        }
    }

    public int updateByUserId(UploadPatientRecord uploadPatientRecord) {
        try {
            return this.dao.updateByUserId(uploadPatientRecord);
        } catch (Exception ex) {
            throw new DBException(ex);
        }
    }
}
