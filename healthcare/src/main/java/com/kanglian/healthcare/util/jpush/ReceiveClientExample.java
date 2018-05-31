package com.kanglian.healthcare.util.jpush;

import cn.jpush.api.JPushClient;
import cn.jpush.api.report.ReceivedsResult;
import cn.jpush.api.report.ReceivedsResult.Received;

public class ReceiveClientExample {

    private static final String appKey       = JPushConstants.APP_KEY;

    private static final String masterSecret = JPushConstants.MASTER_SECRET;

    /**
     * 
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        JPushClient jPushClient = new JPushClient(masterSecret, appKey);
        // pushResult：{"msg_id":1702144481,"sendno":1447447117,"statusCode":0}
        String msgIds = "1702144481,2701641499,2737352040,1686719975";
        ReceivedsResult result = jPushClient.getReportReceiveds(msgIds);
        Received received = result.received_list.get(0);
        System.out.println("android_received:" + received.android_received  
                + "\nios:" + received.ios_apns_sent);
        System.out.println("Got result - " + result);
    }
}
