package com.kanglian.healthcare.back.dal.dao;

import java.util.List;
import com.kanglian.healthcare.back.common.NewCrudDao;
import com.kanglian.healthcare.back.dal.pojo.AskQuestionAnswer;

public interface AskQuestionAnswerDao extends NewCrudDao<AskQuestionAnswer> {

    public List<AskQuestionAnswer> getListByUserId(Integer userId);
    
    public List<AskQuestionAnswer> getListByMessageId(String messageId);
}
