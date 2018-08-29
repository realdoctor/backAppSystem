package com.kanglian.healthcare.back.constant;

/**
 * 支付状态
 * 
 * @author xl.liu
 */
public final class PaymentStatus {
    // 未支付
    public static final String PAYMENT_WAIT_BUYER_PAY  = "1";
    // 已支付
    public static final String PAYMENT_TRADE_SUCCESS   = "2";
    // 交易关闭
    public static final String PAYMENT_TRADE_CLOSE     = "3";
}
