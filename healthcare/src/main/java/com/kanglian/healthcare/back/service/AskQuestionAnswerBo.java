package com.kanglian.healthcare.back.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.kanglian.healthcare.back.common.NewCrudBo;
import com.kanglian.healthcare.back.dal.dao.AskQuestionAnswerDao;
import com.kanglian.healthcare.back.dal.pojo.AskQuestionAnswer;
import com.kanglian.healthcare.exception.DBException;

@Service
public class AskQuestionAnswerBo extends NewCrudBo<AskQuestionAnswer, AskQuestionAnswerDao> {

    /**
     * 回复列表详情
     * 
     * @param messageId
     * @return
     */
    public List<AskQuestionAnswer> getListByUserId(String messageId) {
        try {
            return this.dao.getListByUserId(messageId);
        } catch (Exception ex) {
            throw new DBException(ex);
        }
    }
}
