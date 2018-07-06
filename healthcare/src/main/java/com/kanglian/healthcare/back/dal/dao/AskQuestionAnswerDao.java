package com.kanglian.healthcare.back.dal.dao;

import java.util.List;
import com.kanglian.healthcare.back.common.NewCrudDao;
import com.kanglian.healthcare.back.dal.pojo.AskQuestionAnswer;

public interface AskQuestionAnswerDao extends NewCrudDao<AskQuestionAnswer> {

    public AskQuestionAnswer getByMessageId(String messageId);
    
    public int updateByMessageId(AskQuestionAnswer askQuestionAnswer);
    
    /**
     * 回复列表详情
     * 
     * @param messageId
     * @return
     */
    public List<AskQuestionAnswer> getListByUserId(String messageId);
}
