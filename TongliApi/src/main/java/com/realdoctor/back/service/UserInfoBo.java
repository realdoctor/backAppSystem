package com.realdoctor.back.service;

import com.realdoctor.back.dal.pojo.UserInfo;
import com.realdoctor.back.dal.dao.UserInfoDao;
import com.easyway.business.framework.bo.CrudBo;
import org.springframework.stereotype.Service;

@Service
public class UserInfoBo extends CrudBo<UserInfo,UserInfoDao> {

}
