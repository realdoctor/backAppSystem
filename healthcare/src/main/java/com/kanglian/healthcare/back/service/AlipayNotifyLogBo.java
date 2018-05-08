package com.kanglian.healthcare.back.service;

import com.kanglian.healthcare.back.constants.AlipayNotifyResponse;
import com.kanglian.healthcare.back.dal.dao.AlipayNotifyLogDao;
import com.kanglian.healthcare.back.dal.pojo.AlipayNotifyLog;
import com.alibaba.fastjson.JSON;
import com.easyway.business.framework.bo.CrudBo;
import com.easyway.business.framework.util.DateUtil;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class AlipayNotifyLogBo extends CrudBo<AlipayNotifyLog, AlipayNotifyLogDao> {

    /**
     * 写入支付宝回调日志
     * 
     * @param alipayResponse
     */
    public void insertNotifyLog(AlipayNotifyResponse alipayResponse) {
        Map<String, String> payNotifyResponse = alipayResponse.getAlipay_trade_app_pay_response();
        AlipayNotifyLog alipayNotifyLog = new AlipayNotifyLog();
        alipayNotifyLog.setUserId(null);
        alipayNotifyLog.setOrderNo(payNotifyResponse.get("out_trade_no"));
        alipayNotifyLog.setNotifyString(JSON.toJSONString(alipayResponse));
        alipayNotifyLog.setAddTime(DateUtil.currentDate());
        dao.save(alipayNotifyLog);
    }
}
