package com.kanglian.healthcare.quartz.task;

import java.util.List;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.easyway.business.framework.util.CollectionUtil;
import com.easyway.business.framework.util.DateUtil;
import com.kanglian.healthcare.back.dal.pojo.AskQuestionAnswer;
import com.kanglian.healthcare.back.service.AskQuestionAnswerBo;
import com.kanglian.healthcare.quartz.common.AbstractTask;

/**
 * 复诊问题过期处理
 * 1、超过三天未处理，进行中已结束
 * 
 * @author xl.liu
 */
public class OverdueQuestionTask extends AbstractTask {

    /** logger */
    private static final Logger logger = LoggerFactory.getLogger(OverdueQuestionTask.class);
    @Resource
    private AskQuestionAnswerBo askQuestionAnswerBo;
    
    public OverdueQuestionTask() {
        this.taskName = "复诊问题过期处理";
    }

    @Override
    public boolean canProcess() {
        return true;
    }

    @Override
    public void process() {
        try {
            logger.info("==============进入复诊问题过期处理");
            List<AskQuestionAnswer> overdueQuestionList = askQuestionAnswerBo.getListOverThreeday();
            logger.info("==============超过三天未处理，进行中列表 {} 条", overdueQuestionList.size());
            if (CollectionUtil.isNotEmpty(overdueQuestionList)) {
                for (AskQuestionAnswer info : overdueQuestionList) {
                    logger.info("==============正在处理messageId={}，id={}", new Object[] {info.getMessageId(), info.getId()});
                    info.setStatus("2");
                    info.setCloseTime(DateUtil.currentDate());
                    askQuestionAnswerBo.update(info);// TODO:后改为批处理
                    logger.info("==============成功处理id={}", info.getId());
                }
            }
        } catch (Exception e) {
            logger.error("复诊问题过期处理异常", e);
        }
    }

}
