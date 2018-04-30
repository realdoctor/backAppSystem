package com.realdoctor.back.service;

import org.springframework.stereotype.Service;

import com.realdoctor.back.common.Crud2Bo;
import com.realdoctor.back.dal.dao.OutpatientDrugDao;
import com.realdoctor.back.dal.pojo.OutpatientDrug;

@Service
public class OutpatientDrugBo extends Crud2Bo<OutpatientDrug,OutpatientDrugDao> {

}
