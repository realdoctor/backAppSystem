package com.kanglian.healthcare.back.service;

import com.kanglian.healthcare.back.dal.pojo.UserDoctor;
import com.easyway.business.framework.bo.CrudBo;
import org.springframework.stereotype.Service;
import com.kanglian.healthcare.back.dal.dao.UserDoctorDao;

@Service
public class UserDoctorBo extends CrudBo<UserDoctor,UserDoctorDao> {

}
