package com.kanglian.healthcare.back.service;

import org.springframework.stereotype.Service;
import com.kanglian.healthcare.back.dao.MyDoctorDao;
import com.kanglian.healthcare.back.pojo.MyDoctor;
import com.kanglian.healthcare.common.NewCrudBo;

@Service
public class MyDoctorBo extends NewCrudBo<MyDoctor,MyDoctorDao> {

}
