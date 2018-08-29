package com.kanglian.healthcare.back.dao;

import com.easyway.business.framework.dao.CrudDao;
import com.kanglian.healthcare.back.pojo.Account;

public interface AccountDao extends CrudDao<Account> {

    public Account getByUserId(Integer userId);
}
