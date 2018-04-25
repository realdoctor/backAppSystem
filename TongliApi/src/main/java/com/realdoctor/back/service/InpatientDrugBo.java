package com.realdoctor.back.service;

import com.realdoctor.back.dal.pojo.InpatientDrug;
import com.realdoctor.back.dal.dao.InpatientDrugDao;
import com.easyway.business.framework.bo.CrudBo;
import org.springframework.stereotype.Service;

@Service
public class InpatientDrugBo extends CrudBo<InpatientDrug,InpatientDrugDao> {

}
