package com.realdoctor.back.service;

import com.realdoctor.back.dal.pojo.InpatientInrecord;
import com.easyway.business.framework.bo.CrudBo;
import org.springframework.stereotype.Service;
import com.realdoctor.back.dal.dao.InpatientInrecordDao;

@Service
public class InpatientInrecordBo extends CrudBo<InpatientInrecord,InpatientInrecordDao> {

}
