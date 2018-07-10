package com.kanglian.healthcare.back.web;

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
import com.kanglian.healthcare.back.constants.PaymentStatus;
import com.kanglian.healthcare.back.dal.pojo.GoodsOrder;
import com.kanglian.healthcare.back.service.AlipayNotifyLogBo;
import com.kanglian.healthcare.back.service.GoodsOrderBo;
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
    private GoodsOrderBo      goodsOrderBo;
    @Autowired
    private AlipayNotifyLogBo alipayNotifyLogBo;

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
    public String alipayNotify(AlipayNotifyResponse alipayResponse, HttpServletRequest request)
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
                    // TODO 支付成功处理业务逻辑，避免重复处理
                    if (!goodsOrderBo.orderPayStatus(out_trade_no)) {
                        GoodsOrder order = new GoodsOrder();
                        order.setOrderNo(out_trade_no);
                        order.setTradeStatus(PaymentStatus.PAYMENT_TRADE_SUCCESS);
                        order.setUpdateTime(DateUtil.currentDate());
                        goodsOrderBo.updateOrderStatus(order);
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
}
