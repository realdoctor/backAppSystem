package com.kanglian.healthcare.quartz.task;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.alibaba.fastjson.JSON;
import com.easyway.business.framework.util.CollectionUtil;
import com.kanglian.healthcare.back.dal.pojo.AskQuestionAnswer;
import com.kanglian.healthcare.back.service.AskQuestionAnswerBo;
import com.kanglian.healthcare.quartz.common.AbstractTask;

/**
 * 过期问题处理 1、退款 2、已结束
 * 
 * @author xl.liu
 */
public class OverdueQuestionTask extends AbstractTask {

    /** logger */
    private static final Logger logger = LoggerFactory.getLogger(OverdueQuestionTask.class);
    @Resource
    private AskQuestionAnswerBo askQuestionAnswerBo;
    
    public OverdueQuestionTask() {
        this.taskName = "过期问题处理";
    }

    @Override
    public boolean canProcess() {
        return true;
    }

    @Override
    public void process() {
        try {
            logger.info("==============进入过期问题处理");
            List<AskQuestionAnswer> overdueQuestionList = askQuestionAnswerBo.getListOverThreeday();
            logger.info("==============超过三天未处理已回复列表 {} 条", overdueQuestionList.size());
            if (CollectionUtil.isNotEmpty(overdueQuestionList)) {
                List<String> list = new ArrayList<String>();
                for (AskQuestionAnswer info : overdueQuestionList) {
                    list.add(info.getMessageId() + "-" + info.getUserId());
                }
                logger.info("==============未处理回复列表如下：\r\n" + JSON.toJSONString(list));
                int num = askQuestionAnswerBo.updateStatusOverThreeday();
                logger.info("==============成功处理[{}]条", num);
            }
        } catch (Exception e) {
            logger.error("过期问题处理异常", e);
        }
    }

}
