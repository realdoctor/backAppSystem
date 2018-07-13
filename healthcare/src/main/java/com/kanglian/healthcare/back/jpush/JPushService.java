package com.kanglian.healthcare.back.jpush;

import java.text.MessageFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.easyway.business.framework.util.CollectionUtil;
import com.kanglian.healthcare.util.jpush.JPushClientUtil;
import com.kanglian.healthcare.util.jpush.JPushData;

@Service
public class JPushService {

    /** logger */
    private final static Logger logger = LoggerFactory.getLogger(JPushService.class);

    private JPushData convertToJPushData(PushModel pushModel) {
        final JPushData pushData = new JPushData();
        pushData.setTitle(pushModel.getTitle());
        pushData.setContent(pushModel.getContent());
        pushData.setExtrasMap(pushModel.getExtrasMap());
        if (CollectionUtil.isNotEmpty(pushModel.getRegIds())) {
            pushData.setRegIds(pushModel.getRegIds());
        }
        if (CollectionUtil.isNotEmpty(pushModel.getAlias())) {
            pushData.setAlias(pushModel.getAlias());
        }
        if (CollectionUtil.isNotEmpty(pushModel.getTags())) {
            pushData.setTags(pushModel.getTags());
        }
        return pushData;
    }

    /**
     * 推送Android平台
     * 
     * @param pushModel
     * @return
     */
    public Boolean pushToAndroid(PushModel pushModel) {
        try {
            logger.debug("推送参数[android]:" + pushModel.toString());
            int success = JPushClientUtil.sendToAllAndroid(convertToJPushData(pushModel));
            if (success == 1)
                return true;
        } catch (Exception ex) {
            logger.error(MessageFormat.format("安卓推送失败,{0}", pushModel.toString()), ex);
        }
        return false;
    }

    /**
     * 推送iOS平台
     * 
     * @param pushModel
     * @return
     */
    public Boolean pushToiOS(PushModel pushModel) {
        try {
            logger.debug("推送参数[iOS]:" + pushModel.toString());
            int success = JPushClientUtil.sendToAllIos(convertToJPushData(pushModel));
            if (success == 1)
                return true;
        } catch (Exception ex) {
            logger.error(MessageFormat.format("iOS推送失败,{0}", pushModel.toString()), ex);
        }
        return false;
    }

    /**
     * 推送全平台
     * 
     * @param pushModel
     * @return
     */
    public Boolean pushToAll(PushModel pushModel) {
        try {
            logger.debug("推送参数[all]:" + pushModel.toString());
            int success = JPushClientUtil.sendToAll(convertToJPushData(pushModel));
            if (success == 1)
                return true;
        } catch (Exception ex) {
            logger.error(MessageFormat.format("全平台推送失败,{0}", pushModel.toString()), ex);
        }
        return false;
    }
}
