package com.kanglian.healthcare.back.service;

import org.springframework.stereotype.Service;
import com.easyway.business.framework.bo.CrudBo;
import com.easyway.business.framework.util.DateUtil;
import com.kanglian.healthcare.back.dao.AlipayPaymentLogDao;
import com.kanglian.healthcare.back.pojo.AlipayPaymentLog;
import com.kanglian.healthcare.exception.DBException;

@Service
public class AlipayPaymentLogBo extends CrudBo<AlipayPaymentLog, AlipayPaymentLogDao> {

    /**
     * 写入支付宝预付单日志
     * 
     * @param userId
     * @param orderNo
     * @param requestParams
     * @param orderString
     */
    public void insertPayOrderLog(String userId, String orderNo, String requestParams,
            String responseString, String orderString) {
        try {
            AlipayPaymentLog alipayOrderLog = new AlipayPaymentLog();
            alipayOrderLog.setUserId(userId);
            alipayOrderLog.setOrderNo(orderNo);
            alipayOrderLog.setRequestParams(requestParams);
            alipayOrderLog.setResponseString(responseString);
            alipayOrderLog.setOrderString(orderString);
            alipayOrderLog.setAddTime(DateUtil.currentDate());
            this.dao.save(alipayOrderLog);
        } catch (Exception ex) {
            throw new DBException(ex);
        }
    }
}
