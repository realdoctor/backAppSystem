package com.kanglian.healthcare.back.dao;

import com.easyway.business.framework.dao.CrudDao;
import com.kanglian.healthcare.back.pojo.UserIdentify;

public interface UserIdentifyDao extends CrudDao<UserIdentify> {

    public UserIdentify getByUserId(Integer userId);
}
