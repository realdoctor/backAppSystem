package com.realdoctor.back.service;

import com.realdoctor.back.dal.pojo.Outpatient;
import com.easyway.business.framework.bo.CrudBo;
import org.springframework.stereotype.Service;
import com.realdoctor.back.dal.dao.OutpatientDao;

@Service
public class OutpatientBo extends CrudBo<Outpatient,OutpatientDao> {

}
