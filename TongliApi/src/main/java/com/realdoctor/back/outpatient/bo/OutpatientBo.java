package com.realdoctor.back.outpatient.bo;

import com.easyway.business.framework.bo.CrudBo;
import org.springframework.stereotype.Service;
import com.realdoctor.back.outpatient.dao.OutpatientDao;
import com.realdoctor.back.outpatient.pojo.Outpatient;

@Service
public class OutpatientBo extends CrudBo<Outpatient,OutpatientDao> {

}
