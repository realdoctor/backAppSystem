package com.kanglian.healthcare.back.service;

import org.springframework.stereotype.Service;
import com.easyway.business.framework.bo.CrudBo;
import com.easyway.business.framework.util.DateUtil;
import com.kanglian.healthcare.back.dal.dao.AlipayOrderLogDao;
import com.kanglian.healthcare.back.dal.pojo.AlipayOrderLog;
import com.kanglian.healthcare.exception.DBException;

@Service
public class AlipayOrderLogBo extends CrudBo<AlipayOrderLog, AlipayOrderLogDao> {

    /**
     * 写入支付宝预付单日志
     * 
     * @param userId
     * @param orderNo
     * @param orderString
     */
    public void insertPayOrderLog(String userId, String orderNo, String orderString) {
        try {
            AlipayOrderLog alipayOrderLog = new AlipayOrderLog();
            alipayOrderLog.setUserId(userId);
            alipayOrderLog.setOrderNo(orderNo);
            alipayOrderLog.setOrderString(orderString);
            alipayOrderLog.setAddTime(DateUtil.currentDate());
            this.dao.save(alipayOrderLog);
        } catch (Exception ex) {
            throw new DBException(ex);
        }
    }
}
