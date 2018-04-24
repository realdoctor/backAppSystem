package com.realdoctor.back.inpatientDrug.bo;

import com.realdoctor.back.inpatientDrug.pojo.InpatientDrug;
import com.easyway.business.framework.bo.CrudBo;
import org.springframework.stereotype.Service;
import com.realdoctor.back.inpatientDrug.dao.InpatientDrugDao;

@Service
public class InpatientDrugBo extends CrudBo<InpatientDrug,InpatientDrugDao> {

}
