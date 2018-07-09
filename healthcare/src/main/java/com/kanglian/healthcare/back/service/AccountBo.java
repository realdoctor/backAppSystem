package com.kanglian.healthcare.back.service;

import com.easyway.business.framework.bo.CrudBo;
import com.kanglian.healthcare.back.dal.pojo.Account;
import org.springframework.stereotype.Service;
import com.kanglian.healthcare.back.dal.dao.AccountDao;

@Service
public class AccountBo extends CrudBo<Account,AccountDao> {

}
