package com.kanglian.healthcare.back.dal.dao;

import com.kanglian.healthcare.back.dal.pojo.UserIdentify;
import com.easyway.business.framework.dao.CrudDao;

public interface UserIdentifyDao extends CrudDao<UserIdentify> {

    public UserIdentify getByUserId(Integer userId);
}
