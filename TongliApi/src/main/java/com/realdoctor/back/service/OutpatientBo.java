package com.realdoctor.back.service;

import org.springframework.stereotype.Service;

import com.realdoctor.back.common.Crud2Bo;
import com.realdoctor.back.dal.dao.OutpatientDao;
import com.realdoctor.back.dal.pojo.Outpatient;

@Service
public class OutpatientBo extends Crud2Bo<Outpatient,OutpatientDao> {

}
