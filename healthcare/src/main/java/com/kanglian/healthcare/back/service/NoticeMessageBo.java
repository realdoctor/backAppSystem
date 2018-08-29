package com.kanglian.healthcare.back.service;

import org.springframework.stereotype.Service;
import com.kanglian.healthcare.back.dao.NoticeMessageDao;
import com.kanglian.healthcare.back.pojo.NoticeMessage;
import com.kanglian.healthcare.common.NewCrudBo;

@Service
public class NoticeMessageBo extends NewCrudBo<NoticeMessage, NoticeMessageDao> {

}
