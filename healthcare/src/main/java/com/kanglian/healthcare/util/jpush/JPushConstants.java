package com.kanglian.healthcare.util.jpush;

import com.easyway.business.framework.util.StringUtil;
import com.kanglian.healthcare.exception.InvalidOperationException;
import com.kanglian.healthcare.util.PropConfig;

public final class JPushConstants {

    public final static String  APP_KEY         = JPushConstants.getAppKey();
    public final static String  MASTER_SECRET   = JPushConstants.getMasterSecret();
    public final static boolean APNS_PRODUCTION = JPushConstants.getApnsProduction();
    public final static long    TIME_TO_LIVE    = JPushConstants.getTimeToLive();
    public final static int     RETRY_NUM       = JPushConfig.RETRY_NUM;
    
    static class JPushConfig {

        public final static String APP_KEY         = "app_key";
        public final static String MASTER_SECRET   = "master_secret";
        // 推送的apns环境，false表示开发，true表示生产；对android和自定义消息无意义
        public final static String APNS_PRODUCTION = "apns_production";
        /**
         * 离线消息保留时长，推送当前用户不在线时，为该用户保留多长时间的离线消息，以便其上线时再次推送。秒为单位。最多支持10天（864000秒）。
         * 0表示该消息不保存离线。即：用户在线马上发出，当前不在线用户将不会收到此消息。 此参数不设置则表示默认，默认为保存1天的离线消息（86400秒)。
         */
        public final static String TIME_TO_LIVE    = "time_to_live";
        public final static int    RETRY_NUM       = 3;
    }

    public static String getAppKey() {
        String app_key = PropConfig.getInstance().getPropertyValue(JPushConfig.APP_KEY);
        if (StringUtil.isEmpty(app_key)) {
            throw new InvalidOperationException("app_key");
        }
        return app_key;
    }

    public static String getMasterSecret() {
        String master_secret = PropConfig.getInstance().getPropertyValue(JPushConfig.MASTER_SECRET);
        if (StringUtil.isEmpty(master_secret)) {
            throw new InvalidOperationException("master_secret");
        }
        return master_secret;
    }

    public static boolean getApnsProduction() {
        String apns_production =
                PropConfig.getInstance().getPropertyValue(JPushConfig.APNS_PRODUCTION);
        if (StringUtil.isEmpty(apns_production)) {
            throw new InvalidOperationException("apns_production");
        }
        return apns_production.equals("true");
    }

    public static long getTimeToLive() {
        String time_to_live = PropConfig.getInstance().getPropertyValue(JPushConfig.TIME_TO_LIVE);
        if (StringUtil.isEmpty(time_to_live)) {
            throw new InvalidOperationException("time_to_live");
        }
        return Long.valueOf(time_to_live);
    }
    
    /**
     * 手机推送环境
     * 
     * @return
     */
    public static RunningMode getRunningMode() {
        if (getApnsProduction()) {
            return RunningMode.PRODUCT;
        }
        return RunningMode.DEV;
    }
}
