package com.kanglian.healthcare.back.dal.dao;

import com.kanglian.healthcare.back.common.NewCrudDao;
import com.kanglian.healthcare.back.dal.pojo.UserDoctor;

public interface UserDoctorDao extends NewCrudDao<UserDoctor> {

    public UserDoctor getDoctorInfo(Integer userId);
}
