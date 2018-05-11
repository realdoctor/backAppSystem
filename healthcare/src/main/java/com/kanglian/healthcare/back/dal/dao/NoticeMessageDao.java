package com.kanglian.healthcare.back.dal.dao;

import java.util.List;
import com.easyway.business.framework.dao.CrudDao;
import com.kanglian.healthcare.back.dal.pojo.NoticeMessage;

public interface NoticeMessageDao extends CrudDao<NoticeMessage> {

    /**
     * 消息提醒列表
     * 
     * @param userId
     * @return
     */
    public List<NoticeMessage> queryForList(Long userId);
}
