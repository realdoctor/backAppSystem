package com.kanglian.healthcare.back.constants;

public final class Constants {

    // 卡类型代码（社保卡、医保卡、身份证）
    public final static String STD_CARD_TYPE                 = "STD_CARD_TYPE";

    // 生理性别代码
    public final static String STD_SEX                       = "STD_SEX";

    // 身份证件类别代码
    public final static String STD_PERSON_ID_TYPE            = "STD_PERSON_ID_TYPE";

    // 民族类别代码
    public final static String STD_NATIONALITY               = "STD_NATIONALITY";

    // ABO血型代码
    public final static String STD_BLOOD_TYPE                = "STD_BLOOD_TYPE";

    // Rh（D）血型代码
    public final static String STD_RH_RESULT                 = "STD_RH_RESULT";

    // 学历代码
    public final static String STD_EDUCATION                 = "STD_EDUCATION";

    // 职业分类与代码
    public final static String STD_OCCUPATION                = "STD_OCCUPATION";

    // 婚姻状况代码
    public final static String STD_MARRIAGE                  = "STD_MARRIAGE";

    // 过敏源代码
    public final static String STD_ALLERGIC_SOURCE           = "STD_ALLERGIC_SOURCE";

    // 既往常见疾病种类代码
    public final static String STD_PAST_COMMON_DISEASE       = "STD_PAST_COMMON_DISEASE";

    // 家族疾病史代码
    public final static String STD_FAMILY_HISTORY_DISEASES   = "STD_FAMILY_HISTORY_DISEASES";

    // 中药使用类别代码
    public final static String STD_CHINESE_MEDICINE_TYPE     = "STD_CHINESE_MEDICINE_TYPE";

    // 中医病证分类与代码
    public final static String STD_CHINESE_MEDICINE_SYMPTOMS = "STD_CHINESE_MEDICINE_SYMPTOMS";

    // 用药途径代码
    public final static String STD_USE_MEDICINE_WAY          = "STD_USE_MEDICINE_WAY";

    // 中华人民共和国行政区划代码
    public final static String STD_ADMINISTRATIVE_DIVISION   = "STD_ADMINISTRATIVE_DIVISION";

    // 世界各国和地区名称代码
    public final static String STD_COUNTRY                   = "STD_COUNTRY";

    // 入院途径代码
    public final static String STD_IN_PATH                   = "STD_IN_PATH";

    // 疾病诊断（ICD10）
    public final static String STD_ICD10                     = "STD_ICD10";

    // 药物剂型代码
    public final static String STD_MEDICINE_FORM             = "STD_MEDICINE_FORM";

    // 药物使用频率
    public final static String STD_MEDICATION_USE_FREQUENCY  = "STD_MEDICATION_USE_FREQUENCY";

    /**
     * 
     */
    // 手机验证码位数
    public final static int    VERIFY_CODE_NUM               = 4;

    // 手机验证码
    public static final String VERIFY_CODE_KEY_PREFIX        = "VERIFY_CODE_";

    // 判断已缓存字典码表标识
    public static final String MARK_CODETABLE_KEY            = "INIT_CACHE_CODETABLE";

    // 判断是否在Token刷新频率范围标识
    public static final String MARK_REFRESH_TOKEN_KEY_PREFIX = "AUTHORIZATION_REFRESH_TOKEN_";

    /**
     * 上传文件
     */
    // 上传路径
    public static final String UPLOAD_PATH                   = "upload_path";
    // 访问路径
    public static final String STATIC_URL                    = "static_url";
    // 视频截图工具路径
    public static final String FFMPEG_PATH                   = "/usr/local/ffmpeg/bin/ffmpeg"; // ffmpeg

    /**
     * 支付订单标识
     */
    // 支付宝支付
    public static final String ALIPAY_PREFIX                 = "AL";
    // 微信支付
    public static final String WXPAY_PREFIX                  = "WX";
    // 医保支付
    public static final String YBPAY_PREFIX                  = "YB";
}
