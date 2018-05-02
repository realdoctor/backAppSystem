package com.realdoctor.back.service;

import com.easyway.business.framework.bo.CrudBo;
import org.springframework.stereotype.Service;
import com.realdoctor.back.dal.dao.PatientDrugDao;
import com.realdoctor.back.dal.pojo.PatientDrug;

@Service
public class PatientDrugBo extends CrudBo<PatientDrug,PatientDrugDao> {

}
