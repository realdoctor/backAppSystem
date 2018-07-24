package com.kanglian.healthcare.back.web;

import java.util.HashMap;
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
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.easyway.business.framework.springmvc.result.ResultBody;
import com.easyway.business.framework.springmvc.result.ResultUtil;
import com.easyway.business.framework.util.StringUtil;
import com.kanglian.healthcare.back.constants.AlipayConfig;
import com.kanglian.healthcare.back.constants.Constants;
import com.kanglian.healthcare.back.constants.FromType;
import com.kanglian.healthcare.back.constants.PaymentType;
import com.kanglian.healthcare.back.dal.cond.PaymentOrder;
import com.kanglian.healthcare.back.dal.cond.PaymentOrderT;
import com.kanglian.healthcare.back.service.AlipayOrderLogBo;
import com.kanglian.healthcare.back.service.GoodsOrderBo;
import com.kanglian.healthcare.back.service.PaymentOrderBo;
import com.kanglian.healthcare.back.service.UserBo;
import com.kanglian.healthcare.util.JsonUtil;
import com.kanglian.healthcare.util.NumberUtil;
import com.kanglian.healthcare.util.PayCommonUtil;

/**
 * 支付
 * 
 * @author xl.liu
 */
@Controller
@RequestMapping(value = "/pay")
public class PaymentController extends BaseController {
    @Autowired
    private GoodsOrderBo     goodsOrderBo;
    @Autowired
    private AlipayOrderLogBo alipayOrderLogBo;
    @Autowired
    private UserBo           userBo;
    @Autowired
    private PaymentOrderBo   paymentOrderBo;

    /**
     * 拉取商品预付单
     * 
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/orderPay", method = RequestMethod.POST)
    @ResponseBody
    public ResultBody orderPay(@RequestBody PaymentOrder paymentOrder, HttpServletRequest request)
            throws Exception {
        logger.info("==============进入拉取商品预付单");
        final String ptype = paymentOrder.getType();
        if (PaymentType.ALIPAY.getName().equals(ptype)) {
            logger.info("==========================支付宝支付");
        } else if (PaymentType.WXPAY.getName().equals(ptype)) {
            logger.info("==========================微信支付");
        } else if (PaymentType.SPAY.getName().equals(ptype)) {
            logger.info("==========================账户余额支付");
        } else {
            logger.info("+++++++++++++++++++++++++++++++请选择支付方式");
            return ResultUtil.error("请选择支付方式");
        }
        if (StringUtil.isEmpty(paymentOrder.getUserId())) {
            logger.info("+++++++++++++++++++++++++++++++用户不能为空");
            return ResultUtil.error("用户不能为空");
        }
        if (paymentOrder.getTotalAmount() == null) {
            logger.info("+++++++++++++++++++++++++++++++支付金额不能为空");
            return ResultUtil.error("支付金额不能为空");
        }
        
        // 判断用户是否存在
        if (!userBo.ifExist(Long.valueOf(paymentOrder.getUserId()))) {
            logger.info("+++++++++++++++++++++++++++++++非法用户请求");
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
        String orderNo = NumberUtil.getOrderId();
        paymentOrder.setOrderNo(orderNo);
        
        Map<String, String> retResultMap = new HashMap<String, String>();
        /**
         * 拉取支付宝预付单
         */
        if (PaymentType.ALIPAY.getName().equals(ptype)) {
            orderNo = Constants.ALIPAY_PREFIX.concat(orderNo);
            paymentOrder.setOrderNo(orderNo);
            // 订单总金额，单位为元，精确到小数点后两位，取值范围[0.01,100000000]这里调试每次支付1分钱，在项目上线前应将此处改为订单的总金额格
            String orderPrice = "0.01";
            // 设置后台异步通知的地址，在手机端支付成功后支付宝会通知后台，手机端的真实支付结果依赖于此地址
            String alipayNotifyUrl = AlipayConfig.getNotifyUrl();
            // 实例化客户端
            AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.PAY_URL,
                    AlipayConfig.APPID, AlipayConfig.APP_PRIVATE_KEY, AlipayConfig.FORMAT,
                    AlipayConfig.CHARSET, AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.SIGNTYPE);
            // 实例化具体API对应的request类，类名称和接口名称对应，当前调用接口名称：alipay.trade.app.pay
            AlipayTradeAppPayRequest alipayRequest = new AlipayTradeAppPayRequest();
            // SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
            AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
            model.setSubject("测试支付");// 订单标题
            model.setBody("康连健康商品支付");// 对交易或商品的描述
            model.setOutTradeNo(orderNo);
            model.setTimeoutExpress("30m");// 30分钟付款超时
            model.setTotalAmount(orderPrice);
            /**
             * 支付回传参数
             */
            Map<String, Object> notifyParmMap = new HashMap<String, Object>();
            notifyParmMap.put("userId", userId);
            notifyParmMap.put("orderNo", orderNo);
            notifyParmMap.put(Constants.NOTIFY_RETURN_TAG, Constants.NOTIFY_RETURN_TAG_SP);
            model.setPassbackParams(
                    PayCommonUtil.urlEncodeUTF8(JsonUtil.beanToJson(notifyParmMap)));
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
                logger.debug("=============用户Id：{}，订单号：{}，请求参数：{}，响应字符串：{}，支付串：{}",
                        new Object[] {userId, orderNo, requestParams, responseString, orderString});
            } catch (AlipayApiException e) {
                throw new Exception("获取支付宝参数错误");
            }
            /**
             * 写入支付宝预付单日志
             */
            alipayOrderLogBo.insertPayOrderLog(userId, orderNo, requestParams, responseString,
                    orderString);
        }
        /**
         * 拉取微信预付单
         */
        else if (PaymentType.WXPAY.getName().equals(ptype)) {
            orderNo = Constants.WXPAY_PREFIX.concat(orderNo);
            paymentOrder.setOrderNo(orderNo);
            return ResultUtil.error("微信支付未开放");
        }
        /**
         * 账户余额支付
         */
        else if (PaymentType.SPAY.getName().equals(ptype)) {
            return ResultUtil.error("余额支付未开放");
        }
        
        /**
         * 写入购买商品订单明细
         */
        goodsOrderBo.createGoodsOrder(paymentOrder);
        return ResultUtil.success(retResultMap);
    }

    /**
     * 拉取预付单
     * 
     * @param paymentOrder
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/orderPayT", method = RequestMethod.POST)
    @ResponseBody
    public ResultBody orderPayT(@RequestBody PaymentOrderT paymentOrder, HttpServletRequest request)
            throws Exception {
        logger.info("==============进入拉取预付单");
        final String ptype = paymentOrder.getType();
        if (PaymentType.ALIPAY.getName().equals(ptype)) {
            logger.info("==========================支付宝支付");
        } else if (PaymentType.WXPAY.getName().equals(ptype)) {
            logger.info("==========================微信支付");
        } else if (PaymentType.SPAY.getName().equals(ptype)) {
            logger.info("==========================账户余额支付");
        } else {
            logger.info("+++++++++++++++++++++++++++++++请选择支付方式");
            return ResultUtil.error("请选择支付方式");
        }
        if (StringUtil.isEmpty(paymentOrder.getUserId())) {
            logger.info("+++++++++++++++++++++++++++++++用户不能为空");
            return ResultUtil.error("用户不能为空");
        }
        if (paymentOrder.getPayAmount() == null) {
            logger.info("+++++++++++++++++++++++++++++++支付金额不能为空");
            return ResultUtil.error("支付金额不能为空");
        }
        // 支付来源
        if (StringUtil.isEmpty(paymentOrder.getFrom())
                || StringUtil.isEmpty(FromType.getName(paymentOrder.getFrom()))) {
            logger.info("+++++++++++++++++++++++++++++++支付来源不明确");
            return ResultUtil.error("支付来源不明确");
        }
        // 判断用户是否存在
        if (!userBo.ifExist(Long.valueOf(paymentOrder.getUserId()))) {
            logger.info("+++++++++++++++++++++++++++++++非法用户请求");
            return ResultUtil.error("非法用户");
        }

        // 支付用户Id
        String userId = paymentOrder.getUserId();
//        // 支付给用户Id
//        String toUserId = paymentOrder.getToUserId();
//        // 支付金额
//        Double payAmount = paymentOrder.getPayAmount();
        // 外部订单号
        String orderNo = NumberUtil.getOrderNo();
        paymentOrder.setOrderNo(orderNo);
        
        Map<String, String> retResultMap = new HashMap<String, String>();
        /**
         * 拉取支付宝预付单
         */
        if (PaymentType.ALIPAY.getName().equals(ptype)) {
            orderNo = Constants.ALIPAY_PREFIX.concat(orderNo);
            paymentOrder.setOrderNo(orderNo);
            String orderPrice = "0.01";
            String alipayNotifyUrl = AlipayConfig.getNotifyUrl();
            AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.PAY_URL,
                    AlipayConfig.APPID, AlipayConfig.APP_PRIVATE_KEY, AlipayConfig.FORMAT,
                    AlipayConfig.CHARSET, AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.SIGNTYPE);
            AlipayTradeAppPayRequest alipayRequest = new AlipayTradeAppPayRequest();
            AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
            model.setSubject("支付-"+orderPrice);
            model.setBody("支付-"+orderPrice);
            model.setOutTradeNo(orderNo);
            model.setTimeoutExpress("30m");
            model.setTotalAmount(orderPrice);
            /**
             * 支付回传参数
             */
            Map<String, Object> notifyParmMap = new HashMap<String, Object>();
            notifyParmMap.put("userId", userId);
            notifyParmMap.put("orderNo", orderNo);
            notifyParmMap.put(Constants.NOTIFY_RETURN_TAG, Constants.NOTIFY_RETURN_TAG_WQ);
            model.setPassbackParams(
                    PayCommonUtil.urlEncodeUTF8(JsonUtil.beanToJson(notifyParmMap)));
            alipayRequest.setBizModel(model);
            alipayRequest.setNotifyUrl(alipayNotifyUrl);
            String requestParams = JsonUtil.beanToJson(alipayRequest);
            String responseString = null;
            String orderString = null;
            try {
                AlipayTradeAppPayResponse response = alipayClient.sdkExecute(alipayRequest);
                if (!response.isSuccess()) {
                    logger.debug("==========调用失败");
                    return ResultUtil.error("拉取支付宝预付单失败");
                }
                logger.debug("==========调用成功");
                responseString = JsonUtil.beanToJson(response);
                orderString = response.getBody();
                retResultMap.put("orderString", orderString);// 就是orderString可以直接给客户端请求，无需再做处理。
                logger.debug("=============用户Id：{}，订单号：{}，请求参数：{}，响应字符串：{}，支付串：{}",
                        new Object[] {userId, orderNo, requestParams, responseString, orderString});
            } catch (AlipayApiException e) {
                throw new Exception("获取支付宝参数错误");
            }
            /**
             * 写入支付宝预付单日志
             */
            alipayOrderLogBo.insertPayOrderLog(userId, orderNo, requestParams, responseString,
                    orderString);
        }
        /**
         * 拉取微信预付单
         */
        else if (PaymentType.WXPAY.getName().equals(ptype)) {
            orderNo = Constants.WXPAY_PREFIX.concat(orderNo);
            paymentOrder.setOrderNo(orderNo);
            return ResultUtil.error("微信支付未开放");
        }
        /**
         * 账户余额支付
         */
        else if (PaymentType.SPAY.getName().equals(ptype)) {
            return ResultUtil.error("余额支付未开放");
        }
        
        /**
         * 写入支付订单
         */
        paymentOrderBo.createPaymentOrderAndLog(paymentOrder);
        return ResultUtil.success(retResultMap);
    }
}
