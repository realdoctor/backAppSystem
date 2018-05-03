package com.kanglian.healthcare.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

public final class ValidateUtil {

    /**
     * 验证手机号码
     * 
     * @param mobile
     * @return
     */
    public static boolean isMobile(final String mobile) {
        if (StringUtils.isEmpty(mobile)) {
            return false;
        }
        Pattern p = null;
        Matcher m = null;
        boolean b = false;
        p = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$"); // 验证手机号
        m = p.matcher(mobile);
        b = m.matches();
        return b;
    }

}
