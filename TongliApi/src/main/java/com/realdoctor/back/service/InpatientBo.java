package com.realdoctor.back.service;

import com.realdoctor.back.dal.pojo.Inpatient;
import com.easyway.business.framework.bo.CrudBo;
import org.springframework.stereotype.Service;
import com.realdoctor.back.dal.dao.InpatientDao;

@Service
public class InpatientBo extends CrudBo<Inpatient,InpatientDao> {

}
