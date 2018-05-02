package com.realdoctor.back.service;

import org.springframework.stereotype.Service;

import com.realdoctor.back.common.Crud2Bo;
import com.realdoctor.back.dal.dao.PatientRecordDao;
import com.realdoctor.back.dal.pojo.PatientRecord;

@Service
public class PatientRecordBo extends Crud2Bo<PatientRecord,PatientRecordDao> {

}
