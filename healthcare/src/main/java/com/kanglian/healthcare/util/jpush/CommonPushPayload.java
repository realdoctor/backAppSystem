package com.kanglian.healthcare.util.jpush;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.easyway.business.framework.util.CollectionUtil;
import cn.jiguang.common.ServiceHelper;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;

/**
 * 极光推送对象
 * 
 * @author xl.liu
 */
public final class CommonPushPayload {
    
    /** logger */
    private final static Logger logger = LoggerFactory.getLogger(CommonPushPayload.class);
    
    public static class PushPayloadService {

        /*
         * ==========================================================================
         * ======= 推送通知、自定义消息
         * ==========================================================================
         */
        // all
        public static PushPayload buildPushObject_android_and_ios_alertWithExtrasAndMessage(String notification_title, String notification_alert, String msg_title,
                                                                                 String msg_content, Map<String, String> extrasMap, ICommonPushPayloadListener commonAudienceListener){
            logger.debug("===========>>buildPushObject_android_and_ios_alertWithExtrasAndMessage");
            return PushPayload.newBuilder()
                    .setPlatform(Platform.android_ios())
                    .setAudience(commonAudienceListener.buildAudience())//设置受众，推送目标
                    .setNotification(Notification.newBuilder().setAlert(notification_alert)
                            //指定当前推送的android通知
                            .addPlatformNotification(AndroidNotification.newBuilder()
                                    .setAlert(notification_alert)
                                    .setTitle(notification_title)
                                    //此字段为透传字段，不会显示在通知栏。用户可以通过此字段来做一些定制需求，如特定的key传要指定跳转的页面（value）
                                    .addExtras(extrasMap)
                                    .build()
                            )
                            //指定当前推送的iOS通知
                            .addPlatformNotification(IosNotification.newBuilder()
                                    .setAlert(notification_alert)
                                    .incrBadge(1)//应用角标
                                    .addExtras(extrasMap)
                                    //.setContentAvailable(true)//如果为1表示要静默推送
                                    .build()
                            ).build()
                    )
                    .setMessage(Message.newBuilder()
                            .setMsgContent(msg_content)
                            .setTitle(msg_title)
                            .build())
                    .setOptions(Options.newBuilder()
                            .setApnsProduction(JPushConstants.APNS_PRODUCTION)
                            .setSendno(ServiceHelper.generateSendno())
                            .setTimeToLive(JPushConstants.TIME_TO_LIVE)
                            .build()
                    )
                    .build();
        }
        
        // android
        public static PushPayload buildPushObject_android_alertWithExtrasAndMessage(String notification_title, String notification_alert, String msg_title,
                                                                         String msg_content, Map<String, String> extrasMap, ICommonPushPayloadListener commonAudienceListener){
            logger.debug("===========>>buildPushObject_android_alertWithExtrasAndMessage");
            return PushPayload.newBuilder()
                    .setPlatform(Platform.android())
                    .setAudience(commonAudienceListener.buildAudience())
                    .setNotification(Notification.newBuilder()
                            .addPlatformNotification(AndroidNotification.newBuilder()
                                    .setAlert(notification_alert)
                                    .setTitle(notification_title)
                                    .addExtras(extrasMap)
                                    .build())
                            .build()
                    )
                    .setMessage(Message.newBuilder()
                            .setMsgContent(msg_content)
                            .setTitle(msg_title)
                            .build())
                    .setOptions(Options.newBuilder()
                            .setApnsProduction(JPushConstants.APNS_PRODUCTION)
                            .setSendno(ServiceHelper.generateSendno())
                            .setTimeToLive(JPushConstants.TIME_TO_LIVE)
                            .build())
                    .build();
        }
        
        // ios
        public static PushPayload buildPushObject_ios_alertWithExtrasAndMessage(String notification_title, String notification_alert, String msg_title,
                                                                     String msg_content, Map<String, String> extrasMap, ICommonPushPayloadListener commonAudienceListener){
            logger.debug("===========>>buildPushObject_ios_alertWithExtrasAndMessage");
            return PushPayload.newBuilder()
                    .setPlatform(Platform.ios())
                    .setAudience(commonAudienceListener.buildAudience())
                    .setNotification(Notification.newBuilder()
                            .addPlatformNotification(IosNotification.newBuilder()
                                    .setAlert(notification_alert)
                                    .incrBadge(1)
                                    .addExtras(extrasMap)
                                    //.setContentAvailable(true)
                                    .build())
                            .build()
                    )
                    .setMessage(Message.newBuilder()
                            .setMsgContent(msg_content)
                            .setTitle(msg_title)
                            .build())
                    .setOptions(Options.newBuilder()
                            .setApnsProduction(JPushConstants.APNS_PRODUCTION)
                            .setSendno(ServiceHelper.generateSendno())
                            .setTimeToLive(JPushConstants.TIME_TO_LIVE)
                            .build())
                    .build();
        }
        
        /*
         * ==========================================================================
         * ======= 推送通知
         * ======= 不管客户端是否在运行都是能够收到推送过来的通知
         * ==========================================================================
         */
        // all
        public static PushPayload buildPushObject_android_and_ios_alertWithExtras(String notification_title,
                                                                                  String notification_alert,
                                                                                  Map<String, String> extrasMap,
                                                                                  ICommonPushPayloadListener commonAudienceListener) {
            logger.debug("===========>>buildPushObject_android_and_ios_alertWithExtras");
            return PushPayload.newBuilder()
                    .setPlatform(Platform.android_ios())
                    .setAudience(commonAudienceListener.buildAudience())//设置受众，推送目标
                    .setNotification(Notification.newBuilder().setAlert(notification_alert)
                            //指定当前推送的android通知
                            .addPlatformNotification(AndroidNotification.newBuilder()
                                    .setTitle(notification_title)
                                    .setAlert(notification_alert)
                                    //此字段为透传字段，不会显示在通知栏。用户可以通过此字段来做一些定制需求，如特定的key传要指定跳转的页面（value）
                                    .addExtras(extrasMap)
                                    .build()
                            )
                            //指定当前推送的iOS通知
                            .addPlatformNotification(IosNotification.newBuilder()
                                    .setAlert(notification_alert)
                                    .incrBadge(1)//应用角标
                                    .addExtras(extrasMap)
                                    //.setContentAvailable(true)//如果为1表示要静默推送
                                    .build()
                            ).build()
                    )
                    .setOptions(Options.newBuilder()
                            .setApnsProduction(JPushConstants.APNS_PRODUCTION)
                            .setSendno(ServiceHelper.generateSendno())
                            .setTimeToLive(JPushConstants.TIME_TO_LIVE)
                            .build()
                    )
                    .build();
        }
        
        // android
        public static PushPayload buildPushObject_android_alertWithExtras(String notification_title,
                                                                          String notification_alert,
                                                                          Map<String, String> extrasMap,
                                                                          ICommonPushPayloadListener commonAudienceListener) {
            logger.debug("===========>>buildPushObject_android_alertWithExtras");
            return PushPayload.newBuilder()
                    .setPlatform(Platform.android())
                    .setAudience(commonAudienceListener.buildAudience())
                    .setNotification(Notification.newBuilder()
                            .addPlatformNotification(AndroidNotification.newBuilder()
                                    .setTitle(notification_title)
                                    .setAlert(notification_alert)
                                    .addExtras(extrasMap)
                                    .build())
                            .build()
                    )
                    .setOptions(Options.newBuilder()
                            .setApnsProduction(JPushConstants.APNS_PRODUCTION)
                            .setSendno(ServiceHelper.generateSendno())
                            .setTimeToLive(JPushConstants.TIME_TO_LIVE)
                            .build())
                    .build();
        }
        
        // ios
        public static PushPayload buildPushObject_ios_alertWithExtras(String notification_title,
                                                                      String notification_alert,
                                                                      Map<String, String> extrasMap,
                                                                      ICommonPushPayloadListener commonAudienceListener) {
            logger.debug("===========>>buildPushObject_ios_alertWithExtras");
            return PushPayload.newBuilder()
                    .setPlatform(Platform.ios())
                    .setAudience(commonAudienceListener.buildAudience())
                    .setNotification(Notification.newBuilder()
                            .addPlatformNotification(IosNotification.newBuilder()
                                    .setAlert(notification_alert)
                                    .incrBadge(1)
                                    .addExtras(extrasMap)
                                    //.setContentAvailable(true)
                                    .build())
                            .build()
                    )
                    .setOptions(Options.newBuilder()
                            .setApnsProduction(JPushConstants.APNS_PRODUCTION)
                            .setSendno(ServiceHelper.generateSendno())
                            .setTimeToLive(JPushConstants.TIME_TO_LIVE)
                            .build())
                    .build();
        }
        
        /*
         * ==========================================================================
         * ======= 自定义消息
         * ======= 客户端App只有在运行的时候才能收到自定义消息
         * ==========================================================================
         */
        // all
        public static PushPayload buildPushObject_android_and_ios_messageWithExtras(String msg_title,
                                                                                    String msg_content,
                                                                                    Map<String, String> extrasMap,
                                                                                    ICommonPushPayloadListener commonAudienceListener) {
            logger.debug("===========>>buildPushObject_android_and_ios_messageWithExtras");
            return PushPayload.newBuilder()
                    .setPlatform(Platform.android_ios())
                    .setAudience(commonAudienceListener.buildAudience())//设置受众，推送目标
                    .setMessage(Message.newBuilder()
                            .setTitle(msg_title)
                            .setMsgContent(msg_content)
                            .addExtras(extrasMap)
                            .build())
                    .setOptions(Options.newBuilder()
                            .setApnsProduction(JPushConstants.APNS_PRODUCTION)
                            .setSendno(ServiceHelper.generateSendno())
                            .setTimeToLive(JPushConstants.TIME_TO_LIVE)
                            .build()
                    )
                    .build();
        }
        
        // android
        public static PushPayload buildPushObject_android_messageWithExtras(String msg_title, String msg_content,
                                                                            Map<String, String> extrasMap,
                                                                            ICommonPushPayloadListener commonAudienceListener) {
            logger.debug("===========>>buildPushObject_android_messageWithExtras");
            return PushPayload.newBuilder()
                    .setPlatform(Platform.android())
                    .setAudience(commonAudienceListener.buildAudience())
                    .setMessage(Message.newBuilder()
                            .setTitle(msg_title)
                            .setMsgContent(msg_content)
                            .addExtras(extrasMap)
                            .build())
                    .setOptions(Options.newBuilder()
                            .setApnsProduction(JPushConstants.APNS_PRODUCTION)
                            .setSendno(ServiceHelper.generateSendno())
                            .setTimeToLive(JPushConstants.TIME_TO_LIVE)
                            .build())
                    .build();
        }
        
        // ios
        public static PushPayload buildPushObject_ios_messageWithExtras(String msg_title, String msg_content,
                                                                        Map<String, String> extrasMap,
                                                                        ICommonPushPayloadListener commonAudienceListener) {
            logger.debug("===========>>buildPushObject_ios_messageWithExtras");
            return PushPayload.newBuilder()
                    .setPlatform(Platform.ios())
                    .setAudience(commonAudienceListener.buildAudience())
                    .setMessage(Message.newBuilder()
                            .setTitle(msg_title)
                            .setMsgContent(msg_content)
                            .addExtras(extrasMap)
                            .build())
                    .setOptions(Options.newBuilder()
                            .setApnsProduction(JPushConstants.APNS_PRODUCTION)
                            .setSendno(ServiceHelper.generateSendno())
                            .setTimeToLive(JPushConstants.TIME_TO_LIVE)
                            .build())
                    .build();
        }
    }

    public interface ICommonPushPayloadListener {

        Audience buildAudience();
    }
    
    /**
     * 生成极光推送对象（Android|iOS）
     * 
     * @param notification_title
     * @param msg_title
     * @param msg_content
     * @param extrasMap
     * @return
     */
    public static PushPayload buildPushObject_android_and_ios_alertWithExtrasAndMessage(String notification_title, String notification_alert, String msg_title,
                                                              String msg_content, Map<String, String> extrasMap) {
        return PushPayloadService.buildPushObject_android_and_ios_alertWithExtrasAndMessage(notification_title, notification_alert, msg_title, msg_content, extrasMap, 
                                                                          new ICommonPushPayloadListener(){

            @Override
            public Audience buildAudience() {
                return Audience.all();
            }
            
        });
    }
 
    /**
     * 生成极光推送对象（Android）
     * 
     * @param notification_title
     * @param msg_title
     * @param msg_content
     * @param extrasMap
     * @return
     */
    public static PushPayload buildPushObject_android_all_alertWithExtrasAndMessage(String notification_title, String notification_alert, String msg_title,
                                                                          String msg_content, Map<String, String> extrasMap) {
        return PushPayloadService.buildPushObject_android_alertWithExtrasAndMessage(notification_title, notification_alert, msg_title, msg_content, extrasMap, 
                                                                                 new ICommonPushPayloadListener(){

                   @Override
                   public Audience buildAudience() {
                       return Audience.all();
                   }
                   
               });
        
    }
 
    /**
     * 生成极光推送对象（iOS）
     * 
     * @param notification_title
     * @param msg_title
     * @param msg_content
     * @param extrasMap
     * @return
     */
    public static PushPayload buildPushObject_ios_all_alertWithExtrasAndMessage(String notification_title, String notification_alert, String msg_title,
                                                                      String msg_content, Map<String, String> extrasMap) {
        return PushPayloadService.buildPushObject_ios_alertWithExtrasAndMessage(notification_title, notification_alert, msg_title, msg_content, extrasMap, 
                                                                         new ICommonPushPayloadListener(){

           @Override
           public Audience buildAudience() {
               return Audience.all();
           }
           
       });
        
    }
    
    /*
     * ==========================================================================
     * ======= registrationId、tags、alias推送通知，默认全平台
     * ==========================================================================
     */
    public static PushPayload buildPushObject_android_and_ios_alertWithExtras(
            final JPushData pushData) {
        return PushPayloadService.buildPushObject_android_and_ios_alertWithExtras(
                pushData.getTitle(), pushData.getContent(), pushData.getExtrasMap(),
                new ICommonPushPayloadListener() {

                    @Override
                    public Audience buildAudience() {
                        if (CollectionUtil.isNotEmpty(pushData.getRegIds())) {
                            return Audience.registrationId(pushData.getRegIds());// 推送给设备标识参数的用户
                        } else if (CollectionUtil.isNotEmpty(pushData.getTags())) {
                            return Audience.tag(pushData.getTags());// tag并集 tag_and交集
                        } else if (CollectionUtil.isNotEmpty(pushData.getAlias())) {
                            return Audience.alias(pushData.getAlias());
                        }
                        return Audience.all();
                    }

                });
    }
 
    public static PushPayload buildPushObject_android_all_alertWithExtras(
            final JPushData pushData) {
        return PushPayloadService.buildPushObject_android_alertWithExtras(pushData.getTitle(),
                pushData.getContent(), pushData.getExtrasMap(), new ICommonPushPayloadListener() {

                    @Override
                    public Audience buildAudience() {
                        if (CollectionUtil.isNotEmpty(pushData.getRegIds())) {
                            return Audience.registrationId(pushData.getRegIds());
                        } else if (CollectionUtil.isNotEmpty(pushData.getTags())) {
                            return Audience.tag(pushData.getTags());
                        } else if (CollectionUtil.isNotEmpty(pushData.getAlias())) {
                            return Audience.alias(pushData.getAlias());
                        }
                        return Audience.all();
                    }

                });

    }
 
    public static PushPayload buildPushObject_ios_all_alertWithExtras(final JPushData pushData) {
        return PushPayloadService.buildPushObject_ios_alertWithExtras(pushData.getTitle(),
                pushData.getContent(), pushData.getExtrasMap(), new ICommonPushPayloadListener() {

                    @Override
                    public Audience buildAudience() {
                        if (CollectionUtil.isNotEmpty(pushData.getRegIds())) {
                            return Audience.registrationId(pushData.getRegIds());
                        } else if (CollectionUtil.isNotEmpty(pushData.getTags())) {
                            return Audience.tag(pushData.getTags());
                        } else if (CollectionUtil.isNotEmpty(pushData.getAlias())) {
                            return Audience.alias(pushData.getAlias());
                        }
                        return Audience.all();
                    }

                });

    }
    
    /*
     * ==========================================================================
     * ======= registrationId、tags、alias推送自定义消息，默认全平台
     * ==========================================================================
     */
    public static PushPayload buildPushObject_android_and_ios_messageWithExtras(
            final JPushData pushData) {
        return PushPayloadService.buildPushObject_android_and_ios_messageWithExtras(
                pushData.getTitle(), pushData.getContent(), pushData.getExtrasMap(),
                new ICommonPushPayloadListener() {

                    @Override
                    public Audience buildAudience() {
                        if (CollectionUtil.isNotEmpty(pushData.getRegIds())) {
                            return Audience.registrationId(pushData.getRegIds());// 推送给设备标识参数的用户
                        } else if (CollectionUtil.isNotEmpty(pushData.getTags())) {
                            return Audience.tag(pushData.getTags());// tag并集 tag_and交集
                        } else if (CollectionUtil.isNotEmpty(pushData.getAlias())) {
                            return Audience.alias(pushData.getAlias());
                        }
                        return Audience.all();
                    }

                });
    }
 
    public static PushPayload buildPushObject_android_all_messageWithExtras(
            final JPushData pushData) {
        return PushPayloadService.buildPushObject_android_messageWithExtras(pushData.getTitle(),
                pushData.getContent(), pushData.getExtrasMap(), new ICommonPushPayloadListener() {

                    @Override
                    public Audience buildAudience() {
                        if (CollectionUtil.isNotEmpty(pushData.getRegIds())) {
                            return Audience.registrationId(pushData.getRegIds());
                        } else if (CollectionUtil.isNotEmpty(pushData.getTags())) {
                            return Audience.tag(pushData.getTags());
                        } else if (CollectionUtil.isNotEmpty(pushData.getAlias())) {
                            return Audience.alias(pushData.getAlias());
                        }
                        return Audience.all();
                    }

                });

    }
 
    public static PushPayload buildPushObject_ios_all_messageWithExtras(final JPushData pushData) {
        return PushPayloadService.buildPushObject_ios_messageWithExtras(pushData.getTitle(),
                pushData.getContent(), pushData.getExtrasMap(), new ICommonPushPayloadListener() {

                    @Override
                    public Audience buildAudience() {
                        if (CollectionUtil.isNotEmpty(pushData.getRegIds())) {
                            return Audience.registrationId(pushData.getRegIds());
                        } else if (CollectionUtil.isNotEmpty(pushData.getTags())) {
                            return Audience.tag(pushData.getTags());
                        } else if (CollectionUtil.isNotEmpty(pushData.getAlias())) {
                            return Audience.alias(pushData.getAlias());
                        }
                        return Audience.all();
                    }

                });

    }
    
}
