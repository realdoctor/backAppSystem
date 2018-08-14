package com.kanglian.healthcare.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradeRefundResponse;

/**
 * 支付宝退款
 * 
 * @author xl.liu
 */
public class AlipayRefundUtil {

    private final static Logger logger = LoggerFactory.getLogger(AlipayRefundUtil.class);
    
    /**
     * 支付宝退款接口
     * 
     * @param out_trade_no 订单支付时传入的商户订单号,不能和支付宝交易号（trade_no）同时为空。
     * @param trade_no 支付宝交易号
     * @param refund_amount 需要退款的金额，该金额不能大于订单金额,单位为元，支持两位小数
     * @return 将提示信息返回
     */
    public synchronized static String alipayRefundRequest(String out_trade_no, String trade_no,
            double refund_amount) {
        String returnStr = null;
        String out_request_no = "BZ35581R88001";// 随机数 不是全额退款，部分退款使用该参数
        try {
            AlipayClient alipayClient = PayCommonUtil.getAlipayClient();
            AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
            request.setBizContent("{" + "\"out_trade_no\":\"" + out_trade_no + "\","
                    + "\"trade_no\":\"" + trade_no + "\"," + "\"refund_amount\":\"" + refund_amount
                    + "\"," + "\"out_request_no\":\"" + out_request_no + "\","
                    + "\"refund_reason\":\"正常退款\"" + " }");
            logger.info("=========支付宝退款请求，request={}", JSON.toJSONString(request));
            AlipayTradeRefundResponse response;
            response = alipayClient.execute(request);
            logger.info("=========支付宝退款响应，response={}", JSON.toJSONString(response));
            if (response.isSuccess()) {
                logger.info("================支付宝退款成功");
                return "success";
            } else {
                returnStr = response.getSubMsg();// 失败会返回错误信息
                logger.info("================支付宝退款失败，returnStr={}", returnStr);
            }
        } catch (Exception e) {
            logger.error("支付宝退款异常", e);
            return "fail";
        }
        return returnStr;
    }

    /**
     * @param str
     */
    public static void main(String str[]) {
        String strq = alipayRefundRequest("109adb5aa64878314ab184e44f4c6f07", "", 0.1D);
        System.out.println(strq);
    }

}
