package com.kanglian.healthcare.back.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.easyway.business.framework.bo.CrudBo;
import com.kanglian.healthcare.back.dal.dao.NoticeMessageDao;
import com.kanglian.healthcare.back.dal.pojo.NoticeMessage;
import com.kanglian.healthcare.exception.DBException;

@Service
public class NoticeMessageBo extends CrudBo<NoticeMessage, NoticeMessageDao> {

    /**
     * 消息提醒列表
     * 
     * @param userId
     * @return
     */
    public List<NoticeMessage> queryForList(final Long userId) {
        try {
            return this.dao.queryForList(userId);
        } catch (Exception ex) {
            throw new DBException(ex);
        }
    }
}
