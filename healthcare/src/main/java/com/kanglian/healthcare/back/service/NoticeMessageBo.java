package com.kanglian.healthcare.back.service;

import org.springframework.stereotype.Service;
import com.kanglian.healthcare.back.common.Crud2Bo;
import com.kanglian.healthcare.back.dal.dao.NoticeMessageDao;
import com.kanglian.healthcare.back.dal.pojo.NoticeMessage;

@Service
public class NoticeMessageBo extends Crud2Bo<NoticeMessage, NoticeMessageDao> {

}
