package com.kanglian.healthcare.quartz.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.kanglian.healthcare.back.service.CodetableBo;
import com.kanglian.healthcare.quartz.common.AbstractTask;

/**
 * 字段码表刷新
 * 
 * @author xl.liu
 */
public class CodeRefreshTask extends AbstractTask {

    /** logger */
    private static final Logger logger = LoggerFactory.getLogger(CodeRefreshTask.class);

    @Autowired
    private CodetableBo         codetableBo;

    public CodeRefreshTask() {
        this.taskName = "字典码表刷新";
    }

    @Override
    public boolean canProcess() {
        return true;
    }

    @Override
    public void process() {
        try {
            logger.info("==============进入字典码表刷新");
            codetableBo.initCacheData();
        } catch (Exception e) {
            logger.error("字典码表刷新异常", e);
        }
    }

}
