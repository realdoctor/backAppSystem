package com.kanglian.healthcare.back.service;

import com.easyway.business.framework.bo.CrudBo;
import com.kanglian.healthcare.back.dal.pojo.AccountLog;
import org.springframework.stereotype.Service;
import com.kanglian.healthcare.back.dal.dao.AccountLogDao;

@Service
public class AccountLogBo extends CrudBo<AccountLog,AccountLogDao> {

}
