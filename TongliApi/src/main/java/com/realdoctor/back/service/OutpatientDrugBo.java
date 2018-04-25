package com.realdoctor.back.service;

import com.realdoctor.back.dal.dao.OutpatientDrugDao;
import com.realdoctor.back.dal.pojo.OutpatientDrug;
import com.easyway.business.framework.bo.CrudBo;
import org.springframework.stereotype.Service;

@Service
public class OutpatientDrugBo extends CrudBo<OutpatientDrug,OutpatientDrugDao> {

}
