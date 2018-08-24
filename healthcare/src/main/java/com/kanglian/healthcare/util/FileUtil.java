package com.kanglian.healthcare.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.channels.FileChannel;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import org.apache.commons.lang.StringUtils;

public class FileUtil {

    public static final Map<String, String> CONTENT_TYPE_MAP = new HashMap<String, String>();

    static {
        CONTENT_TYPE_MAP.put("image", "gif,jpg,jpeg,png,bmp");
        CONTENT_TYPE_MAP.put("flash", "swf,flv");
        CONTENT_TYPE_MAP.put("media", "swf,flv,mp3,mp4,3gp,wav,wma,wmv,mid,avi,mpg,asf,mov,rm,rmvb");
        CONTENT_TYPE_MAP.put("file", "rar,zip,doc,docx,xls,xlsx,ppt,pptx,htm,html,txt,dwg,pdf");
        CONTENT_TYPE_MAP.put("excel", "xls,xlsx");
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
    
    public static String getKBSize(long size) {
        if (size <= 0) return "0";
        double value = (double) size;
        DecimalFormat df = new DecimalFormat("#0.00");
        return df.format(value / 1024);
    }

    public static String getMBSize(long size) {
        if (size <= 0) return "0";
        double value = (double) size;
        DecimalFormat df = new DecimalFormat("#0.00");
        return df.format(value / (1024 * 1024));
    }
    
    public static String getGBSize(long size) {
        if (size <= 0) return "0";
        double value = (double) size;
        DecimalFormat df = new DecimalFormat("#0.00");
        return df.format(value / (1024 * 1024 * 1024));
    }
    
    public static long getFileSize(String filePath) {
        FileChannel fc = null;
        FileInputStream fis = null;
        long size = 0L;
        try {
            File f = new File(filePath);
            if (f.exists() && f.isFile()) {
                fis = new FileInputStream(f);
                fc = fis.getChannel();
                size = fc.size();
            }
        } catch (Exception e) {
            LogUtil.getErrorLogger().error(e.getMessage(), e);
        } finally {
            if (null != fis) {
                try {
                    fis.close();
                } catch (IOException e) {
                }
            }
            if (null != fc) {
                try {
                    fc.close();
                } catch (IOException e) {
                }
            }
        }
        return size;
    }
    
    public static String getPrintSize(long size) {
        // 如果字节数少于1024，则直接以B为单位，否则先除于1024，后3位因太少无意义
        double value = (double) size;
        if (value < 1024) {
            return String.valueOf(value) + "B";
        } else {
            value = new BigDecimal(value / 1024).setScale(2, BigDecimal.ROUND_DOWN).doubleValue();
        }
        // 如果原字节数除于1024之后，少于1024，则可以直接以KB作为单位
        // 因为还没有到达要使用另一个单位的时候
        // 接下去以此类推
        if (value < 1024) {
            return String.valueOf(value) + "KB";
        } else {
            value = new BigDecimal(value / 1024).setScale(2, BigDecimal.ROUND_DOWN).doubleValue();
        }
        if (value < 1024) {
            return String.valueOf(value) + "MB";
        } else {
            // 否则如果要以GB为单位的，先除于1024再作同样的处理
            value = new BigDecimal(value / 1024).setScale(2, BigDecimal.ROUND_DOWN).doubleValue();
            return String.valueOf(value) + "GB";
        }
    }
}
