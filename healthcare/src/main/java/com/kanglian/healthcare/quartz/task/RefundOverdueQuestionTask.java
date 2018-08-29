package com.kanglian.healthcare.quartz.task;

import java.util.List;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.easyway.business.framework.util.CollectionUtil;
import com.kanglian.healthcare.back.pojo.AskQuestionAnswer;
import com.kanglian.healthcare.back.service.AskQuestionAnswerBo;
import com.kanglian.healthcare.back.service.PaymentOrderBo;
import com.kanglian.healthcare.quartz.common.AbstractTask;

/**
 * 复诊问题退款处理
 * 1、超过三天未回复处理，退款
 * 
 * @author xl.liu
 */
public class RefundOverdueQuestionTask extends AbstractTask {

    /** logger */
    private static final Logger logger = LoggerFactory.getLogger(RefundOverdueQuestionTask.class);
    @Resource
    private AskQuestionAnswerBo askQuestionAnswerBo;
    @Resource
    private PaymentOrderBo paymentOrderBo;
    
    public RefundOverdueQuestionTask() {
        this.taskName = "复诊问题退款处理";
    }

    @Override
    public boolean canProcess() {
        return true;
    }

    @Override
    public void process() {
        try {
            logger.info("==============进入复诊问题退款处理");
            List<AskQuestionAnswer> overdueQuestionList = askQuestionAnswerBo.getListOverThreedayUnAnswer();
            logger.info("==============超过三天未回复处理，退款列表 {} 条", overdueQuestionList.size());
            if (CollectionUtil.isNotEmpty(overdueQuestionList)) {
                for (AskQuestionAnswer info : overdueQuestionList) {
                    paymentOrderBo.refundAskQuestion(info);
                }
            }
        } catch (Exception e) {
            logger.error("复诊问题退款处理", e);
        }
    }

}
