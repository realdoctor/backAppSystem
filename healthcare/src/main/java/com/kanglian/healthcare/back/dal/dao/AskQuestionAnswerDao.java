package com.kanglian.healthcare.back.dal.dao;

import java.util.List;
import com.kanglian.healthcare.back.common.NewCrudDao;
import com.kanglian.healthcare.back.dal.pojo.AskQuestionAnswer;

public interface AskQuestionAnswerDao extends NewCrudDao<AskQuestionAnswer> {

    public List<AskQuestionAnswer> getListByUserId(Integer userId);

    public List<AskQuestionAnswer> getListByMessageId(String messageId);

    /**
     * 获取超过三天未处理，进行中列表
     * 
     * @return
     */
    public List<AskQuestionAnswer> getListOverThreeday();

    /**
     * 更新超过三天未处理，进行中列表状态2
     * 
     * @return
     */
    public int updateStatusOverThreeday();
    
    /**
     * 超过三天未处理，未回复列表[退款]
     * 
     * @return
     */
    public List<AskQuestionAnswer> getListOverThreedayUnAnswer();
}
