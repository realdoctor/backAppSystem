package com.kanglian.healthcare.back.dal.dao;

import java.util.List;
import com.kanglian.healthcare.back.common.NewCrudDao;
import com.kanglian.healthcare.back.dal.pojo.AskQuestionAnswer;

public interface AskQuestionAnswerDao extends NewCrudDao<AskQuestionAnswer> {

    public List<AskQuestionAnswer> getListByUserId(Integer userId);

    public List<AskQuestionAnswer> getListByMessageId(String messageId);

    /**
     * 获取患者超过三天未处理已回复列表
     * 
     * @return
     */
    public List<AskQuestionAnswer> getListOverThreeday();

    /**
     * 更新患者超过三天未处理，已回复列表状态2
     * 
     * @return
     */
    public int updateStatusOverThreeday();
}
