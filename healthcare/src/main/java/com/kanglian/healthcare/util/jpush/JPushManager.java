package com.kanglian.healthcare.util.jpush;

import com.kanglian.healthcare.util.CacheManager;
import cn.jiguang.common.ClientConfig;
import cn.jpush.api.JPushClient;

public final class JPushManager {

    private static class LazyHolder {

        private static final JPushManager INSTANCE = new JPushManager();
    }

    private JPushManager(){
        // 生产环境
        if (RunningMode.PRODUCT.equals(JPushConstants.getRunningMode())) {
            CacheManager.getData(RunningMode.PRODUCT.name(), new CacheManager.Load<JPushClient>() {
                public JPushClient load() {
                    ClientConfig config = ClientConfig.getInstance();
                    config.setMaxRetryTimes(JPushConstants.RETRY_NUM);
                    config.setTimeToLive(JPushConstants.TIME_TO_LIVE);
                    config.setApnsProduction(true); // development env
                    JPushClient jPushClient = new JPushClient(JPushConstants.MASTER_SECRET, JPushConstants.APP_KEY,
                            null, config);
                    return jPushClient;
                }
            }, -1);
        } else {
            // 开发环境-iOS
            CacheManager.getData(RunningMode.PRODUCT.name(), new CacheManager.Load<JPushClient>() {
                public JPushClient load() {
                    ClientConfig config = ClientConfig.getInstance();
                    config.setMaxRetryTimes(JPushConstants.RETRY_NUM);
                    config.setTimeToLive(JPushConstants.TIME_TO_LIVE);
                    config.setApnsProduction(false);
                    JPushClient jPushClient = new JPushClient(JPushConstants.MASTER_SECRET, JPushConstants.APP_KEY,
                            null, config);
                    return jPushClient;
                }
            }, -1);
            
            // 开发环境-Android
            CacheManager.getData(RunningMode.DEV.name(), new CacheManager.Load<JPushClient>() {
                public JPushClient load() {
                    ClientConfig config = ClientConfig.getInstance();
                    config.setMaxRetryTimes(JPushConstants.RETRY_NUM);
                    config.setTimeToLive(JPushConstants.TIME_TO_LIVE);
                    config.setApnsProduction(false);
                    JPushClient jPushClient = new JPushClient("a24f70afad0f2571a3e661a9", "cc0f1b8ac99a8afcf09ce41c", null, config);
                    return jPushClient;
                }
            }, -1);
        }

    }

    public static final JPushManager getInstance() {
        return LazyHolder.INSTANCE;
    }

    public JPushClient getJPushClient(RunningMode runningMode) {
        if (runningMode == null)
            return null;
        return CacheManager.getData(runningMode.name());
    }
}
