package com.kanglian.healthcare.back.web;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.easyway.business.framework.util.DateUtil;
import com.kanglian.healthcare.back.constants.AlipayConfig;
import com.kanglian.healthcare.back.constants.AlipayNotifyResponse;
import com.kanglian.healthcare.back.constants.Constants;
import com.kanglian.healthcare.back.constants.PaymentStatus;
import com.kanglian.healthcare.back.dal.cond.PaymentOrderT;
import com.kanglian.healthcare.back.dal.pojo.GoodsOrder;
import com.kanglian.healthcare.back.service.AlipayNotifyLogBo;
import com.kanglian.healthcare.back.service.GoodsOrderBo;
import com.kanglian.healthcare.back.service.PaymentOrderBo;
import com.kanglian.healthcare.util.JsonUtil;
import com.kanglian.healthcare.util.PayCommonUtil;

/**
 * 支付回调
 * 
 * @author xl.liu
 */
@Controller
@RequestMapping(value = "/pay")
public class PaymentNotifyController extends BaseController {
    @Autowired
    private GoodsOrderBo       goodsOrderBo;
    @Autowired
    private PaymentOrderBo     paymentOrderBo;
    @Autowired
    private AlipayNotifyLogBo  alipayNotifyLogBo;

    /**
     * 支付宝异步通知
     * 
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/alipay/notifyCallBack", method = RequestMethod.POST)
    @ResponseBody
    public String orderPayNotifyT(AlipayNotifyResponse alipayResponse, HttpServletRequest request)
            throws Exception {
        logger.debug("==============支付宝回调");
        @SuppressWarnings("rawtypes")
        Map requestParams = request.getParameterMap();
        logger.debug("====================支付宝回调结果1：" + requestParams.toString());
        // 获取支付宝POST过来反馈信息
        logger.debug("====================支付宝回调结果2：" + JsonUtil.beanToJson(alipayResponse));
        // 写入支付宝回调日志
        alipayNotifyLogBo.insertNotifyLog(alipayResponse);
        Map<String, String> params = alipayResponse.getAlipay_trade_app_pay_response();
        try {
            // 验证签名
            boolean signVerified = AlipaySignature.rsaCheckV1(params,
                    AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.CHARSET, AlipayConfig.SIGNTYPE);
            if (signVerified) { // 签名验证成功
                // 商户订单号
                String out_trade_no = params.get("out_trade_no");
                // 支付宝交易号
                String trade_no = params.get("trade_no");
                // 交易状态
                String trade_status = params.get("trade_status");
                // 附加数据
                String passback_params = PayCommonUtil.urlDecodeUTF8(params.get("passback_params"));
                logger.debug("支付宝异步通知，返回附加数据==" + passback_params);
                if (trade_status.equals("TRADE_FINISHED")) {
                    logger.info("支付失败= 订单号:{}，支付宝交易号:{}", out_trade_no, trade_no);
                } else if (trade_status.equals("TRADE_SUCCESS")) {
                    logger.info("支付成功= 订单号:{}，支付宝交易号:{}", out_trade_no, trade_no);
                    Map<String, Object> retMap = JsonUtil.jsonToMap(passback_params);
                    String type = (String)retMap.get("type");
                    // TODO 支付成功处理业务逻辑，避免重复处理
                    if (Constants.NOTIFY_RETURN_TAG_SP.equals(type) && !goodsOrderBo.orderPayStatus(out_trade_no)) {
                        GoodsOrder order = new GoodsOrder();
                        order.setOrderNo(out_trade_no);
                        order.setTradeStatus(PaymentStatus.PAYMENT_TRADE_SUCCESS);
                        order.setUpdateTime(DateUtil.currentDate());
                        goodsOrderBo.updateOrderStatus(order);
                    } else if(Constants.NOTIFY_RETURN_TAG_WQ.equals(type) && !paymentOrderBo.orderPayStatus(out_trade_no)) {
                        PaymentOrderT paymentOrderT = new PaymentOrderT();
                        paymentOrderT.setOrderNo(out_trade_no);
                        paymentOrderT.setUserId(retMap.get("userId") + "");
                        paymentOrderBo.updatePaymentOrderAndLog(paymentOrderT);
                    }
                }
            } else {
                logger.debug("====================签名验证失败！");
                return "fail";
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }

        return "success";
    }
    
    /**
     * 支付宝异步通知
     * 
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/reCallBack", method = RequestMethod.POST)
    @ResponseBody
    public String orderPayNotify(AlipayNotifyResponse alipayResponse,
            HttpServletRequest request) throws Exception {
        logger.debug("==============支付宝回调");
        // 写入支付宝回调日志
        alipayNotifyLogBo.insertNotifyLog(alipayResponse);
        // 获取支付宝POST过来反馈信息
        Map requestParams = request.getParameterMap();
        logger.debug("支付宝回调结果1：" + requestParams.toString());
        logger.debug("支付宝回调结果2：" + JsonUtil.beanToJson(alipayResponse));
        Map<String, String> params = new HashMap<String, String>();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            // 乱码解决，这段代码在出现乱码时使用。
            // valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }
        logger.info("params1======="+JsonUtil.beanToJson(params));
//        try {
//            // 验证签名
//            boolean signVerified = AlipaySignature.rsaCheckV1(params,
//                    AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.CHARSET, AlipayConfig.SIGNTYPE);
//            if (signVerified) { // 签名验证成功
//                // 付款金额
//                String total_fee = params.get("total_amount");
//                // 商户订单号
//                String out_trade_no = params.get("out_trade_no");
//                // 支付宝交易号
//                String trade_no = params.get("trade_no");
//                // 交易状态
//                String trade_status = params.get("trade_status");
//                // 附加数据
//                String passback_params = PayCommonUtil.urlDecodeUTF8(params.get("passback_params"));
//                logger.debug("支付宝异步通知，返回附加数据==" + passback_params);
//                if (trade_status.equals("TRADE_FINISHED")) {
//                    logger.info("支付失败= 订单号:{},支付宝交易号:{}", out_trade_no, trade_no);
//                } else if (trade_status.equals("TRADE_SUCCESS")) {
//                    logger.info("支付成功= 订单号:{},支付宝交易号:{}", out_trade_no, trade_no);
//                    // TODO 支付成功处理业务逻辑，需要注意避免重复处理
//                    if (!goodsOrderBo.orderPaySuccess(out_trade_no)) {
//                        GoodsOrder order = new GoodsOrder();
//                        order.setOrderNo(out_trade_no);
//                        order.setStatus(PaymentStatus.PAYMENT_STATUS_PAY);
//                        goodsOrderBo.updateOrderStatus(order);
//                    }
//                }
//            } else {
//                logger.debug("签名验证失败！");
//                return "fail";
//            }
//        } catch (AlipayApiException e) {
//            e.printStackTrace();
//        }
        return "success";
    }

    /**
     * 支付宝异步通知
     * 
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/notifyCallBackT", method = RequestMethod.POST)
    @ResponseBody
    public String orderPayNotify2(AlipayNotifyResponse alipayResponse, HttpServletRequest request) throws Exception {
        logger.debug("==============支付宝回调");
        // 写入支付宝回调日志
        alipayNotifyLogBo.insertNotifyLog(alipayResponse);
        // 获取支付宝POST过来反馈信息
        Map requestParams = request.getParameterMap();
        logger.debug("支付宝回调结果t1：" + requestParams.toString());
        logger.debug("支付宝回调结果t2：" + JsonUtil.beanToJson(alipayResponse));
        Map<String, String> params = new HashMap<String, String>();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            // 乱码解决，这段代码在出现乱码时使用。
            // valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }
        logger.info("params2======="+JsonUtil.beanToJson(params));
        // 切记alipaypublickey是支付宝的公钥，请去open.alipay.com对应应用下查看。
        boolean flag = true; // 校验公钥正确性防止意外
        try
        {
            flag = AlipaySignature.rsaCheckV1(params, AlipayConfig.ALIPAY_PUBLIC_KEY, "utf-8", "RSA2");
        } catch (AlipayApiException e)
        {
            e.printStackTrace();
        }
        if (flag)
        {
            Integer ordersId = Integer.parseInt(params.get("out_trade_no"));
            logger.info("ordersId======="+ordersId);
            String tradeStatus = params.get("trade_status");
            // orders.setOrderState("1"); // 订单状态位已支付
            switch (tradeStatus) // 判断交易结果
            {
            case "TRADE_FINISHED": // 完成
                break;
            case "TRADE_SUCCESS": // 完成
                break;
            case "WAIT_BUYER_PAY": // 待支付
                break;
            case "TRADE_CLOSED": // 交易关闭
                break;
            default:
                break;
            }
        }
        return "success";
    }
}
