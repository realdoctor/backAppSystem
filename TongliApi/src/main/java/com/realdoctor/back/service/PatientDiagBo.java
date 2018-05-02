package com.realdoctor.back.service;

import com.easyway.business.framework.bo.CrudBo;
import org.springframework.stereotype.Service;
import com.realdoctor.back.dal.dao.PatientDiagDao;
import com.realdoctor.back.dal.pojo.PatientDiag;

@Service
public class PatientDiagBo extends CrudBo<PatientDiag,PatientDiagDao> {

}
