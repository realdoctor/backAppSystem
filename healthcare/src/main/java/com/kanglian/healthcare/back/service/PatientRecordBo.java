package com.kanglian.healthcare.back.service;

import org.springframework.stereotype.Service;
import com.kanglian.healthcare.back.dao.PatientRecordDao;
import com.kanglian.healthcare.back.pojo.PatientRecord;
import com.kanglian.healthcare.common.NewCrudBo;

@Service
public class PatientRecordBo extends NewCrudBo<PatientRecord,PatientRecordDao> {

}
