package com.kanglian.healthcare.back.service;

import org.springframework.stereotype.Service;
import com.kanglian.healthcare.back.common.NewCrudBo;
import com.kanglian.healthcare.back.dal.dao.MyDoctorDao;
import com.kanglian.healthcare.back.dal.pojo.MyDoctor;

@Service
public class MyDoctorBo extends NewCrudBo<MyDoctor,MyDoctorDao> {

}
