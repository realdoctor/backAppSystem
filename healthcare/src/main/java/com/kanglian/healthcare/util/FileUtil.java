package com.kanglian.healthcare.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import org.apache.commons.lang.StringUtils;

public class FileUtil {
//    private final static Logger             logger           = LoggerFactory.getLogger(FileUtil.class);

    public static final Map<String, String> CONTENT_TYPE_MAP = new HashMap<String, String>();

    static {
        CONTENT_TYPE_MAP.put("image", "gif,jpg,jpeg,png,bmp");
        CONTENT_TYPE_MAP.put("flash", "swf,flv");
        CONTENT_TYPE_MAP.put("media", "swf,flv,mp3,wav,wma,wmv,mid,avi,mpg,asf,rm,rmvb");
        CONTENT_TYPE_MAP.put("file", "doc,docx,xls,xlsx,ppt,pptx,htm,html,txt,dwg,pdf");
    }

    public static final String ALLCHAR =
            "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    /**
     * 返回一个定长的随机字符串(只包含大小写字母、数字)
     * 
     * @param length 随机字符串长度
     * @return 随机字符串
     */
    public static String generateString(int length) {
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(ALLCHAR.charAt(random.nextInt(ALLCHAR.length())));
        }
        return sb.toString();
    }

    /**
     * @param pattern
     * @param extension
     * @return
     */
    public static String randomPathname(String pattern, String extension) {
        StringBuilder filename = new StringBuilder();
        DateFormat df = new SimpleDateFormat(pattern);
        filename.append(df.format(new Date()));
        filename.append(generateString(10));
        if (StringUtils.isNotBlank(extension)) {
            filename.append(".").append(extension.toLowerCase());
        }
        return filename.toString();
    }

    public static String randomPathname(String extension) {
        return randomPathname("/yyyyMMdd/yyyyMMddHHmmss_", extension);
    }
}
