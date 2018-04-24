package com.realdoctor.back.inpatient.bo;

import com.realdoctor.back.inpatient.pojo.Inpatient;
import com.easyway.business.framework.bo.CrudBo;
import org.springframework.stereotype.Service;
import com.realdoctor.back.inpatient.dao.InpatientDao;

@Service
public class InpatientBo extends CrudBo<Inpatient,InpatientDao> {

}
