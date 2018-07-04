package com.kanglian.healthcare.back.dal.dao;

import java.util.List;
import java.util.Map;
import com.kanglian.healthcare.back.common.NewCrudDao;
import com.kanglian.healthcare.back.dal.pojo.HealthNewsFocus;

public interface HealthNewsFocusDao extends NewCrudDao<HealthNewsFocus> {

    List<Map<String, Object>> getNewsIdsByUserId(Integer userId);
}
