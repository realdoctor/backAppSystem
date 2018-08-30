package com.kanglian.healthcare.back.constant;

/**
 * 接口服务上下文
 * 
 * @author xl.liu
 */
public final class ApiMapping {

    // ==========================================================================
    // ============用户管理
    // ==========================================================================
    /**
     * 用户注册
     */
    public final static String USER_REGIST                     = "/user/regist";
    /**
     * 用户登录
     */
    public final static String USER_LOGIN                      = "/user/login";
    /**
     * 用户退出
     */
    public final static String USER_LOGOUT                     = "/user/logout";
    /**
     * 手机验证码
     */
    public final static String USER_SENDCODE                   = "/user/sendCode";
    /**
     * 密码修改
     */
    public final static String USER_UPDATEPWD                  = "/user/updatePwd";
    /**
     * 忘记（重置）密码
     */
    public final static String USER_RESETPWD                   = "/user/resetPwd";
    /**
     * 实名认证
     */
    public final static String USER_IDENTIFY                   = "/user/certification";
    /**
     * 检查是否已实名
     */
    public final static String USER_IDENTIFY_CHECK             = "/user/certification/check";
    /**
     * 刷新token
     */
    public final static String USER_REFRESH_TOKEN              = "/user/refreshToken";
    /**
     * 扫描关注医生
     */
    public final static String USER_MYDOCTOR_ADD               = "/user/mydoctor/add";
    /**
     * 关注医生列表
     */
    public final static String USER_MYDOCTOR_LIST              = "/user/mydoctor/list";

    // ==========================================================================
    // ============上传信息
    // ==========================================================================
    /**
     * 上传头像
     */
    public final static String UPLOAD_IMG                      = "/upload/uploadImg";
    /**
     * 我的复诊-上传病历
     */
    public final static String UPLOAD_PATIENT                  = "/upload/uploadPatient";
    /**
     * 上传本地病历
     */
    public final static String UPLOAD_PATIENT_RECORD           = "/upload/uploadPatientRecord";

    // ==========================================================================
    // ============首页
    // ==========================================================================
    /**
     * 提醒信息内容
     */
    public final static String USER_MESSAGE_LIST               = "/user/message";
    /**
     * 提醒信息列表
     */
    public final static String USER_MESSAGE_NOTICELIST         = "/user/message/noticeList";

    // ==========================================================================
    // ============患者功能
    // ==========================================================================
    /**
     * 病历归档-查看病历列表
     */
    public final static String PATIENT_LIST                    = "/patient/list";
    /**
     * 病历归档-病历用药列表
     */
    public final static String PATIENT_DRUG_LIST               = "/patient/drug";
    /**
     * 在线复诊-复诊病历列表
     */
    public final static String PATIENT_REVISIT_LIST            = "/patient/revisit/list";
    /**
     * 在线复诊-复诊病历病种列表
     */
    public final static String PATIENT_REVISIT_DIAG_LIST       = "/patient/revisit/diagList";
    /**
     * 我的复诊-患者回复
     */
    public final static String ASKQUESTION_REPLY               = "/askQuestion/reply";
    /**
     * 我的复诊-患者病历列表
     */
    public final static String ASKQUESTION_REPLY_DOCTORLIST    = "/askQuestion/reply/doctorList";

    // ==========================================================================
    // ============医生功能
    // ==========================================================================
    /**
     * 我的复诊-患者病历列表
     */
    public final static String ASKQUESTION_REPLY_PATIENTLIST   = "/askQuestion/reply/patientList";
    /**
     * 我的复诊-患者病历详情
     */
    public final static String ASKQUESTION_REPLY_INFO          = "/askQuestion/reply/info";
    /**
     * 获取问诊收费配置
     */
    public final static String ASKQUESTION_GETMONEY            = "/askQuestion/getAskQuestionMoney";
    /**
     * 设置问诊收费配置
     */
    public final static String ASKQUESTION_SETMONEY            = "/askQuestion/setAskQuestionMoney";

    // ==========================================================================
    // ============预约挂号
    // ==========================================================================
    /**
     * 我的预约-患者挂号一览（患者）
     */
    public final static String USER_MYGUAHAOORDER              = "/user/myGuahaoOrder";
    /**
     * 我的预约-患者挂号一览（医生）
     */
    public final static String USER_MYPATIENTORDER             = "/user/myPatientOrder";
    /**
     * 挂号医院一览
     */
    public final static String GUAHAO_HOSPITAL_LIST            = "/guahao/hospital/list";
    /**
     * 医院科室分类
     */
    public final static String GUAHAO_HOSPITAL_DEPTCATEGORY    = "/guahao/hospital/deptCategory";
    /**
     * 预约日期列表
     */
    public final static String GUAHAO_HOSPITAL_DUTYDAY         = "/guahao/hospital/orderDate";
    /**
     * 按专家预约列表
     */
    public final static String GUAHAO_HOSPITAL_ORDEREXPERT     = "/guahao/hospital/orderExpert";
    /**
     * 按日期预约列表
     */
    public final static String GUAHAO_HOSPITAL_ORDERDATEEXPERT = "/guahao/hospital/orderDateExpert";
    /**
     * 预约挂号
     */
    public final static String GUAHAO_FASTORDER                = "/guahao/fastorder";

    // ==========================================================================
    // ============商城
    // ==========================================================================
    /**
     * 商品列表
     */
    public final static String GOODS_LIST                      = "/goods/list";
    /**
     * 我的购物车
     */
    public final static String GOODS_CART_LIST                 = "/goods/cart/list";
    /**
     * 添加购物车
     */
    public final static String GOODS_CART_ADDCARTITEM          = "/goods/cart/addCartItem";
    /**
     * 清空购物车
     */
    public final static String GOODS_CART_CLEARCART            = "/goods/cart/clearCart";
    /**
     * 删除购物车
     */
    public final static String GOODS_CART_DELETECARTITEM       = "/goods/cart/deleteCartItem";
    /**
     * 取消订单
     */
    public final static String GOODS_ORDER_CANCELORDER         = "/goods/order/cancelOrder";

    // ==========================================================================
    // ============图文视频
    // ==========================================================================
    /**
     * 上传视频图片
     */
    public final static String UPLOAD_UPLOADFILES              = "/upload/uploadFiles";
    /**
     * 上传视频图片列表
     */
    public final static String NEWS_PUB_LIST                   = "/news_pub/list";
    /**
     * 上传视频图片详情
     */
    public final static String NEWS_PUB_INFO                   = "/news_pub/info";
    /**
     * 上传上传视频图片
     */
    public final static String NEWS_PUB_DEL                    = "/news_pub/del";

    // ==========================================================================
    // ============资讯信息
    // ==========================================================================
    /**
     * 资讯一览
     */
    public final static String NEWS_LIST                       = "/healthnews/list";
    /**
     * 资讯详情
     */
    public final static String NEWS_INFO                       = "/healthnews/info";
    /**
     * 关注资讯
     */
    public final static String NEWS_FOCUS                      = "/healthnews/focus";
    /**
     * 取消关注资讯
     */
    public final static String NEWS_FOCUS_OFF                  = "/healthnews/focus/off";
    /**
     * 我关注的资讯列表
     */
    public final static String NEWS_FOCUS_LIST                 = "/healthnews/myFocusList";
    /**
     * 广告列表
     */
    public final static String NEWS_AD_LIST                    = "/healthnews/ad/list";
    /**
     * 资讯搜索
     */
    public final static String NEWS_SEARCH                     = "/news/search";

    // ==========================================================================
    // ============极光推送
    // ==========================================================================
    /**
     * 推送消息
     */
    public final static String PUSH_PUSHMSG                    = "/push/pushmsg";

    // ==========================================================================
    // ============支付
    // ==========================================================================
    /**
     * 拉取预付单-商城
     */
    public final static String PAY_ORDERPAY                    = "/pay/orderPay";
    /**
     * 拉取预付单
     */
    public final static String PAY_ORDERPAYT                   = "/pay/orderPayT";
}
