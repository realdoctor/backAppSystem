package com.realdoctor.back.service;

import com.realdoctor.back.dal.pojo.PatientRecord;
import com.easyway.business.framework.bo.CrudBo;
import com.realdoctor.back.dal.dao.PatientRecordDao;
import org.springframework.stereotype.Service;

@Service
public class PatientRecordBo extends CrudBo<PatientRecord,PatientRecordDao> {

}
