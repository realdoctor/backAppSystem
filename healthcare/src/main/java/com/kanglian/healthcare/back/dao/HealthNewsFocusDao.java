package com.kanglian.healthcare.back.dao;

import java.util.List;
import java.util.Map;
import com.kanglian.healthcare.back.pojo.HealthNewsFocus;
import com.kanglian.healthcare.common.NewCrudDao;

public interface HealthNewsFocusDao extends NewCrudDao<HealthNewsFocus> {

    List<Map<String, Object>> getNewsIdsByUserId(Integer userId);
}
