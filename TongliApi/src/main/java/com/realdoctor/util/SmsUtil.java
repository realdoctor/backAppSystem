package com.realdoctor.util;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;

public class SmsUtil {

    // 短信API产品名称
    static String product            = "Dysmsapi";
    // 短信API产品域名
    static String domain             = "dysmsapi.aliyuncs.com";
    static String accessKeyId        = "LTAIBnXKgAXWJBfN";
    static String accessKeySecret    = "f0X3HbQl0WdkXdMCjW0hTol2OpW5T4";
    static String signName           = "康连健康";
    // 验证码
    public static String verifyTempleteCode = "SMS_133125186";
    // 短信通知
    public static String noticeTempleteCode = "SMS_133125186";
    static {
        try {
            PropConfig.getInstance().loadConfig("sms.properties");
            PropConfig propConfig = PropConfig.getInstance();
            product = propConfig.getPropertyValue("product");
            domain = propConfig.getPropertyValue("domain");
            accessKeyId = propConfig.getPropertyValue("accessKeyId");
            accessKeySecret = propConfig.getPropertyValue("accessKeySecret");
            signName = propConfig.getPropertyValue("signName");
            verifyTempleteCode = propConfig.getPropertyValue("verifyTempleteCode");
            noticeTempleteCode = propConfig.getPropertyValue("noticeTempleteCode");
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
    
    public static SendSmsResponse sendSms(String mobile, String templateParam,
                                          String templateCode) throws ClientException {

        // 可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");

        // 初始化acsClient,暂不支持region化
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        IAcsClient acsClient = new DefaultAcsClient(profile);

        // 组装请求对象-具体描述见控制台-文档部分内容
        SendSmsRequest request = new SendSmsRequest();

        // 必填:待发送手机号
        request.setPhoneNumbers(mobile);
        // 必填:短信签名-可在短信控制台中找到
        request.setSignName(signName);
        // 必填:短信模板-可在短信控制台中找到
        request.setTemplateCode(templateCode);

        // 可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
        request.setTemplateParam(templateParam);

        // 选填-上行短信扩展码(无特殊需求用户请忽略此字段)
        // request.setSmsUpExtendCode("90997");

        // 可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
        request.setOutId("9999");

        // hint 此处可能会抛出异常，注意catch
        SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);

        return sendSmsResponse;
    }

    /**
     * 发送验证码
     * 
     * @param mobile 手机号
     * @param code 验证码 
     *      变量替换值<=6位数字或字母
     * @return
     */
    public static boolean sendCode(String mobile, String code) {
        try {
            SendSmsResponse response = sendSms(mobile, "{\"code\":\"" + code + "\"}", verifyTempleteCode);
            if (response.getCode() != null && response.getCode().equals("OK")) {
                System.out.println("发送成功！");
                return true;
            } else {
                System.out.println("发送失败！");
                return false;
            }
        } catch (ClientException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        String mobile = "13000000000";
        int code = (int)((Math.random()*9+1)*100000);
        SendSmsResponse sendSms = sendSms(mobile, "{\"code\":\"" + code + "\"}", verifyTempleteCode);
        System.out.println("短信接口返回的数据----------------");
        System.out.println("Code=" + sendSms.getCode());
        System.out.println("Message=" + sendSms.getMessage());
        System.out.println("RequestId=" + sendSms.getRequestId());
        System.out.println("BizId=" + sendSms.getBizId());
    }
}
