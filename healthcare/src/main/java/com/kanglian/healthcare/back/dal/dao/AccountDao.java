package com.kanglian.healthcare.back.dal.dao;

import com.kanglian.healthcare.back.dal.pojo.Account;
import com.easyway.business.framework.dao.CrudDao;

public interface AccountDao extends CrudDao<Account> {

    public Account getByUserId(Integer userId);
}
