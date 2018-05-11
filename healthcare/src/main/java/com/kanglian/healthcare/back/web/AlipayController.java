package com.kanglian.healthcare.back.web;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.easyway.business.framework.common.annotation.PerformanceClass;
import com.easyway.business.framework.springmvc.result.ResultBody;
import com.easyway.business.framework.springmvc.result.ResultUtil;
import com.easyway.business.framework.util.DateUtil;
import com.easyway.business.framework.util.StringUtil;
import com.kanglian.healthcare.back.constants.AlipayConfig;
import com.kanglian.healthcare.back.constants.AlipayNotifyResponse;
import com.kanglian.healthcare.back.constants.PaymentStatus;
import com.kanglian.healthcare.back.dal.cond.PaymentOrder;
import com.kanglian.healthcare.back.dal.pojo.GoodsOrder;
import com.kanglian.healthcare.back.service.AlipayNotifyLogBo;
import com.kanglian.healthcare.back.service.AlipayOrderLogBo;
import com.kanglian.healthcare.back.service.GoodsOrderBo;
import com.kanglian.healthcare.back.service.UserBo;
import com.kanglian.healthcare.util.JsonUtil;
import com.kanglian.healthcare.util.NumberUtil;
import com.kanglian.healthcare.util.PayCommonUtil;

/**
 * 支付宝支付
 * 
 * @author xl.liu
 */
@Controller
@RequestMapping(value = "/pay/alipay")
public class AlipayController extends BaseController {

    @Autowired
    private GoodsOrderBo        goodsOrderBo;
    @Autowired
    private AlipayOrderLogBo    alipayOrderLogBo;
    @Autowired
    private AlipayNotifyLogBo   alipayNotifyLogBo;
    @Autowired
    private UserBo              userBo;
    
    /**
     * 拉取支付宝预付单
     * 
     * @param request
     * @return
     * @throws Exception
     */
    @PerformanceClass
    @RequestMapping(value = "/orderPay", method = RequestMethod.POST)
    @ResponseBody
    public ResultBody orderPay(@RequestBody PaymentOrder paymentOrder, HttpServletRequest request)
            throws Exception {
        logger.info("==============进入拉取支付宝预付单");
        if (StringUtil.isEmpty(paymentOrder.getUserId())) {
            return ResultUtil.error("用户Id不能为空");
        }
        if (paymentOrder.getTotalAmount() == null) {
            return ResultUtil.error("支付金额不能为空");
        }
        // 判断用户是否存在
        if(!userBo.ifExist(Long.valueOf(paymentOrder.getUserId()))) {
            return ResultUtil.error("非法用户");
        }
        /**
         * 判断支付的总金额是否匹配，防止被修改
         */
        if (!this.goodsOrderBo.reviewPaymentOrder(paymentOrder)) {
            logger.info("支付的总金额与购买商品总价不一致，购买订单明细==={}", JsonUtil.object2Json(paymentOrder));
            return ResultUtil.error("支付的总金额与购买商品总价不一致");
        }
        // 用户Id
        String userId = paymentOrder.getUserId();
        // 外部订单号
        final String orderNo = NumberUtil.getOrderNo();
        paymentOrder.setOrderNo(orderNo);
        /**
         * 用户购买商品入库明细
         */
        Map<String, Object> notifyParmMap = goodsOrderBo.createGoodsOrder(paymentOrder);
        /**
         * 拉取支付宝预付单
         */
        // 订单总金额，单位为元，精确到小数点后两位，取值范围[0.01,100000000]这里调试每次支付1分钱，在项目上线前应将此处改为订单的总金额格
        String orderPrice = "0.01";
        // 设置后台异步通知的地址，在手机端支付成功后支付宝会通知后台，手机端的真实支付结果依赖于此地址
        String alipayNotifyUrl = AlipayConfig.getNotifyUrl();
        Map<String, String> retResultMap = new HashMap<>();
        // 实例化客户端
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.PAY_URL,
                AlipayConfig.APPID, AlipayConfig.APP_PRIVATE_KEY, AlipayConfig.FORMAT,
                AlipayConfig.CHARSET, AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.SIGNTYPE);
        // 实例化具体API对应的request类，类名称和接口名称对应，当前调用接口名称：alipay.trade.app.pay
        AlipayTradeAppPayRequest alipayRequest = new AlipayTradeAppPayRequest();
        // SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        model.setSubject("测试支付");// 订单标题
        model.setBody("康连健康商品");// 对交易或商品的描述
        model.setOutTradeNo(orderNo);
        model.setTimeoutExpress("30m");// 30分钟付款超时
        model.setTotalAmount(orderPrice);
        /**
         * 支付回调参数
         */
        notifyParmMap.put("userId", userId);
        notifyParmMap.put("orderNo", orderNo);
        model.setPassbackParams(PayCommonUtil.urlEncodeUTF8(JsonUtil.beanToJson(notifyParmMap)));
        alipayRequest.setBizModel(model);
        alipayRequest.setNotifyUrl(alipayNotifyUrl);
        String requestParams = JsonUtil.beanToJson(alipayRequest);
        String responseString = null;
        String orderString = null;
        try {
            // 这里和普通的接口调用不同，使用的是sdkExecute
            AlipayTradeAppPayResponse response = alipayClient.sdkExecute(alipayRequest);
            if (!response.isSuccess()) {
                logger.debug("==========调用失败");
                return ResultUtil.error("拉取支付宝预付单失败");
            }
            logger.debug("==========调用成功");
            responseString = JsonUtil.beanToJson(response);
            orderString = response.getBody();
            retResultMap.put("orderString", orderString);// 就是orderString可以直接给客户端请求，无需再做处理。
            logger.debug("=============用户Id：{}，订单号：{}，请求参数：{}，响应字符串：{}，支付串：{}", new Object[] {userId, orderNo, requestParams, responseString, orderString});
        } catch (AlipayApiException e) {
            throw new Exception("获取支付宝参数错误");
        }
        /**
         * 写入支付宝预付单日志
         */
        alipayOrderLogBo.insertPayOrderLog(userId, orderNo, requestParams, responseString, orderString);
        return ResultUtil.success(retResultMap);
    }

    /**
     * 支付宝异步通知
     * 
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @PerformanceClass
    @RequestMapping(value = "/reCallBack", method = RequestMethod.POST)
    @ResponseBody
    public String orderPayNotify(@RequestBody AlipayNotifyResponse alipayResponse,
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
    @PerformanceClass
    @RequestMapping(value = "/notifyCallBack", method = RequestMethod.POST)
    @ResponseBody
    public String orderPayNotify2(@RequestBody AlipayNotifyResponse alipayResponse,
            HttpServletRequest request) throws Exception {
        logger.debug("==============支付宝回调");
        // 写入支付宝回调日志
        alipayNotifyLogBo.insertNotifyLog(alipayResponse);
        // 获取支付宝POST过来反馈信息
        Map requestParams = request.getParameterMap();
        logger.debug("支付宝回调结果3：" + requestParams.toString());
        logger.debug("支付宝回调结果4：" + JsonUtil.beanToJson(alipayResponse));
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
    
    
    /**
     * 支付宝异步通知
     * 
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @PerformanceClass
    @RequestMapping(value = "/notifyCallBackT", method = RequestMethod.POST)
    @ResponseBody
    public String orderPayNotify3(@RequestBody AlipayNotifyResponse alipayResponse,
            HttpServletRequest request) throws Exception {
        logger.debug("==============支付宝回调");
        // 写入支付宝回调日志
        alipayNotifyLogBo.insertNotifyLog(alipayResponse);
        // 获取支付宝POST过来反馈信息
        logger.debug("====================支付宝回调结果：" + JsonUtil.beanToJson(alipayResponse));
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
