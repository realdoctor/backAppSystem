package com.kanglian.healthcare.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.easyway.business.framework.util.StringUtil;

/**
 * 数据校验工具类
 * 
 * @author xl.liu
 */
public final class ValidateUtil {

    /**
     * 判断非空，返回自身
     * 
     * @param str
     * @return
     */
    public static String isNull(String str) {
        return (str == null || str.length() == 0) ? "" : str;
    }

    /**
     * 校验是否中文
     * 
     * @param str 字符串
     * @return 校验结果 true：通过 false：不通过
     */
    public static boolean isChinese(String str) {
        Pattern regex = Pattern.compile("[\\u4e00-\\u9fa5]{2,25}");
        Matcher matcher = regex.matcher(ValidateUtil.isNull(str));
        return matcher.matches();
    }

    /**
     * 校验Email格式
     * 
     * @param email 输入邮箱
     * @return 校验结果 true：通过 false：不通过
     */
    public static boolean isEmail(String email) {
        Pattern regex = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
        Matcher matcher = regex.matcher(ValidateUtil.isNull(email));
        return matcher.matches();
    }

    /**
     * 校验手机号格式
     * 
     * @param phone 手机号
     * @return 校验结果 true：通过 false：不通过
     */
    public static boolean isPhone(String phone) {
        Pattern regex = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$");
        Matcher matcher = regex.matcher(ValidateUtil.isNull(phone));
        boolean isMatched = matcher.matches();
        return isMatched;
    }

    /**
     * 校验手机号
     * 
     * @param phone 手机号
     * @return 校验结果和消息
     */
    public static String checkPhone(String phone) {
        if (StringUtil.isBlank(phone)) {
            return "请输入手机号！";
        }
        if (!isPhone(phone)) {
            return "请输入正确的11位手机号！";
        }
        return null;
    }

}
