package com.kanglian.healthcare.back.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.kanglian.healthcare.back.common.NewCrudBo;
import com.kanglian.healthcare.back.dal.dao.AskQuestionAnswerDao;
import com.kanglian.healthcare.back.dal.pojo.AskQuestionAnswer;
import com.kanglian.healthcare.exception.DBException;

@Service
public class AskQuestionAnswerBo extends NewCrudBo<AskQuestionAnswer, AskQuestionAnswerDao> {

    public List<AskQuestionAnswer> getListByUserId(Integer userId) {
        try {
            return this.dao.getListByUserId(userId);
        } catch (Exception ex) {
            throw new DBException(ex);
        }
    }
    
    public List<AskQuestionAnswer> getListByMessageId(String messageId) {
        try {
            return this.dao.getListByMessageId(messageId);
        } catch (Exception ex) {
            throw new DBException(ex);
        }
    }
}
