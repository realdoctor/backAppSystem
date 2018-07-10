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

    /**
     * 获取患者超过三天未处理已回复列表
     * 
     * @return
     */
    public List<AskQuestionAnswer> getListOverThreeday() {
        try {
            return this.dao.getListOverThreeday();
        } catch (Exception ex) {
            throw new DBException(ex);
        }
    }

    /**
     * 更新患者超过三天未处理，已回复列表状态2
     * 
     * @return
     */
    public int updateStatusOverThreeday() {
        try {
            return this.dao.updateStatusOverThreeday();
        } catch (Exception ex) {
            throw new DBException(ex);
        }
    }
}
