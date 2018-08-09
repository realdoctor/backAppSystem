package com.kanglian.healthcare.util;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.kanglian.healthcare.back.constants.AlipayConfig;

public class PayCommonUtil {
    private static final Logger logger    = LoggerFactory.getLogger(PayCommonUtil.class);
    public static final String  DATE_TIME = "yyyyMMddHHmmss";
    private static final String CHARSET   = "UTF-8";

    /**
     * 创建支付宝交易对象
     */
    public static AlipayClient getAlipayClient() {
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.getPayUrl(),
                AlipayConfig.getAppid(), AlipayConfig.getAppPrivateKey(), AlipayConfig.FORMAT,
                AlipayConfig.CHARSET, AlipayConfig.getAlipayPublicKey(), AlipayConfig.SIGNTYPE);

        return alipayClient;
    }

    /**
     * 返回当前时间字符串
     * 
     * @return yyyyMMddHHmmss
     */
    public static String getDateStr() {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME);
        return sdf.format(new Date());
    }

    public static String urlEncodeUTF8(String source) {
        String result = source;
        try {
            result = java.net.URLEncoder.encode(source, CHARSET);
        } catch (UnsupportedEncodingException ex) {
            logger.info("utf8编码===", ex);
        }
        return result;
    }

    public static String urlDecodeUTF8(String source) {
        String result = source;
        try {
            result = java.net.URLDecoder.decode(source, CHARSET);
        } catch (UnsupportedEncodingException ex) {
            logger.info("utf8解码===", ex);
        }
        return result;
    }
    
}
