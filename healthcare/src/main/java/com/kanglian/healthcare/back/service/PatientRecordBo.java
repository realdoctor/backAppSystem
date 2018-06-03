package com.kanglian.healthcare.back.service;

import org.springframework.stereotype.Service;

import com.kanglian.healthcare.back.common.NewCrudBo;
import com.kanglian.healthcare.back.dal.dao.PatientRecordDao;
import com.kanglian.healthcare.back.dal.pojo.PatientRecord;

@Service
public class PatientRecordBo extends NewCrudBo<PatientRecord,PatientRecordDao> {

}
