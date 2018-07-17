package com.kanglian.healthcare.back.dal.dao;

import java.util.Map;
import com.easyway.business.framework.dao.CrudDao;
import com.kanglian.healthcare.back.dal.pojo.User;

public interface UserDao extends CrudDao<User> {

    public User login(User user);

    public User queryUser(String mobilePhone);
    
    public Map<String, Object> getIdentifyInfo(Integer userId);
    
    public Map<String, Object> getDoctorInfoByUserId(Integer userId);
}
