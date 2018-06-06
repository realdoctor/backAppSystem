package com.kanglian.healthcare.util.jpush;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.PushPayload;

/**
 * <p>
 *  <li>[tags推送]为安装了应用程序的用户，打上标签。其目的主要是方便开发者根据标签，来批量下发 Push 消息。</li>
 *  <li>[别名alias推送]为安装了应用程序的用户，取个别名来标识。一般用userId，每个用户只能指定一个别名</li>
 *  <li>[registrationId推送]应用程序在第一次成功注册到 JPush 服务器时，JPush 服务器会给客户端返回一个唯一的该设备的标识。将Registration ID上传PC服务器</li>
 *  <li>registrationID、tag、alias几个类型同时存在时，是取交集的。</li>
 * </p>
 * 
 * @author xl.liu
 */
public class JPushClientUtil {

    /** logger */
    private final static Logger logger = LoggerFactory.getLogger(JPushClientUtil.class);

    public static class RunningService {

        /**
         * @param jPushClient
         * @param r
         * @return 0推送失败，1推送成功
         */
        public static int sendPush(final JPushClient jPushClient, final IPushPayloadListener payload) {
            int result = 0;
            try {
                PushPayload pushPayload = payload.build();// 极光推送对象
                logger.info("=======>>>pushPayload：" + pushPayload);
                PushResult pushResult = jPushClient.sendPush(pushPayload);
                logger.info("=======>>>pushResult：" + pushResult);
                if (pushResult.getResponseCode() == 200) {
                    result = 1;
                }
            } catch (Exception ex) {
                logger.error(ex.getMessage(), ex);
            }

            return result;
        }
    }

    public interface IPushPayloadListener {

        PushPayload build() throws Exception;
    }
    
    /**
     * 发送给所有安卓用户
     * 
     * @param msg_title 消息内容标题
     * @param msg_content 消息内容
     * @param extrasMap 扩展字段
     * @return 0推送失败，1推送成功
     */
    public static int sendToAllAndroid(final JPushData pushData) {
        final JPushClient jPushClient = JPushManager.getInstance().getJPushClient(JPushConstants.getRunningMode());
        return RunningService.sendPush(jPushClient, new IPushPayloadListener() {

            @Override
            public PushPayload build() throws Exception {
                return CommonPushPayload.buildPushObject_android_all_alertWithExtras(pushData);
            }

        });
    }

    /**
     * 发送给所有IOS用户
     * 
     * @param msg_title 消息内容标题
     * @param msg_content 消息内容
     * @param extrasMap 扩展字段
     * @return 0推送失败，1推送成功
     */
    public static int sendToAllIos(final JPushData pushData) {
        final JPushClient jPushClient = JPushManager.getInstance().getJPushClient(RunningMode.PRODUCT);
        return RunningService.sendPush(jPushClient, new IPushPayloadListener() {

            @Override
            public PushPayload build() throws Exception {
                return CommonPushPayload.buildPushObject_ios_all_alertWithExtras(pushData);
            }

        });
    }

    /**
     * 发送给所有用户
     * 
     * @desc 开发生产环境 
     *       1、安卓环境（开发生产环境不同key） 
     *       2、IOS环境（开发生产环境同key）
     * @param msg_title 消息内容标题
     * @param msg_content 消息内容
     * @param extrasMap 扩展字段
     * @return 0推送失败，1推送成功
     */
    public static int sendToAll(final JPushData pushData) {
        if (RunningMode.PRODUCT.equals(JPushConstants.getRunningMode())) {
            final JPushClient jPushClient = JPushManager.getInstance().getJPushClient(RunningMode.PRODUCT);
            return RunningService.sendPush(jPushClient, new IPushPayloadListener() {

                @Override
                public PushPayload build() throws Exception {
                    return CommonPushPayload.buildPushObject_android_and_ios_alertWithExtras(pushData);
                }

            });
        } else {
            // 开发环境分发
            int s1 = sendToAllAndroid(pushData);
            int s2 = sendToAllIos(pushData);
            if ((s1 + s2) > 0) {
                return 1;
            }
            return 0;
        }
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        JPushData pushData = new JPushData();
        Map<String, String> extrasMap = new HashMap<String, String>();
        extrasMap.put("url", "http://news.sina.com.cn/c/nd/2017-10-15/doc-ifymviyp1380801.shtml");
        pushData.setExtrasMap(extrasMap);
        try {
            for (int i = 1; i <= 1; i++) {
                pushData.setTitle("康链健康");
                pushData.setContent("test");
                if (JPushClientUtil.sendToAllAndroid(pushData) == 1) {// sendToAllIos
                    System.out.println("success");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
