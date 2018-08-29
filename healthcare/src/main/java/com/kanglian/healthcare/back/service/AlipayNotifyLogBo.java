package com.kanglian.healthcare.back.service;

import java.util.Map;
import org.springframework.stereotype.Service;
import com.easyway.business.framework.bo.CrudBo;
import com.easyway.business.framework.util.DateUtil;
import com.kanglian.healthcare.back.constant.AlipayNotifyResponse;
import com.kanglian.healthcare.back.dao.AlipayNotifyLogDao;
import com.kanglian.healthcare.back.pojo.AlipayNotifyLog;
import com.kanglian.healthcare.util.JsonUtil;
import com.kanglian.healthcare.util.PayCommonUtil;

@Service
public class AlipayNotifyLogBo extends CrudBo<AlipayNotifyLog, AlipayNotifyLogDao> {

    /**
     * 写入支付宝回调日志
     * 
     * @param alipayResponse
     */
    public void insertNotifyLog(AlipayNotifyResponse alipayResponse) {
        Map<String, String> response = alipayResponse.getAlipay_trade_app_pay_response();
        String userId = null;
        if (response.get("passback_params") != null) {
            // 附加数据
            String passback_params = PayCommonUtil.urlDecodeUTF8(response.get("passback_params"));
            Map<String, Object> params = JsonUtil.jsonToMap(passback_params);
            userId = params.get("userId") + "";
        }
        AlipayNotifyLog alipayNotifyLog = new AlipayNotifyLog();
        alipayNotifyLog.setUserId(userId);
        alipayNotifyLog.setOrderNo(response.get("out_trade_no"));
        alipayNotifyLog.setNotifyString(JsonUtil.beanToJson(alipayResponse));
        alipayNotifyLog.setAddTime(DateUtil.currentDate());
        dao.save(alipayNotifyLog);
    }
}
