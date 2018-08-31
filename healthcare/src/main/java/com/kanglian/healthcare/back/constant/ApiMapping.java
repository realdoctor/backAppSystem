package com.kanglian.healthcare.back.constant;

/**
 * 接口服务管理
 * 
 * @author xl.liu
 */
public final class ApiMapping {

    // ==========================================================================
    // ============用户管理
    // ==========================================================================
    /**
     * <H5>用户注册</H5>
     */
    public final static String USER_REGIST                     = "/user/regist";
    /**
     * <H5>用户登录</H5>
     */
    public final static String USER_LOGIN                      = "/user/login";
    /**
     * <H5>用户退出</H5>
     */
    public final static String USER_LOGOUT                     = "/user/logout";
    /**
     * <H5>手机验证码</H5>
     */
    public final static String USER_SENDCODE                   = "/user/sendCode";
    /**
     * <H5>密码修改</H5>
     */
    public final static String USER_UPDATEPWD                  = "/user/updatePwd";
    /**
     * <H5>忘记（重置）密码</H5>
     */
    public final static String USER_RESETPWD                   = "/user/resetPwd";
    /**
     * <H5>实名认证</H5>
     */
    public final static String USER_IDENTIFY                   = "/user/certification";
    /**
     * <H5>检查是否已实名</H5>
     */
    public final static String USER_IDENTIFY_CHECK             = "/user/certification/check";
    /**
     * <H5>刷新token</H5>
     */
    public final static String USER_REFRESH_TOKEN              = "/user/refreshToken";
    /**
     * <H5>扫描关注医生</H5>
     */
    public final static String USER_MYDOCTOR_ADD               = "/user/mydoctor/add";
    /**
     * <H5>关注医生列表</H5>
     */
    public final static String USER_MYDOCTOR_LIST              = "/user/mydoctor/list";
    /**
     * <H5>用户列表</H5>
     */
    public final static String USER_LIST                       = "/user/list";
    /**
     * <H5>用户基本信息</H5>
     */
    public final static String USER_INFO                       = "/user/info";
    /**
     * <H5>获取用户认证信息</H5>
     */
    public final static String USER_IDENTIFY_INFO              = "/user/certification/info";
    /**
     * <H5>用户消费记录</H5>
     */
    public final static String ACCOUNT_PAYMENT_LIST            = "/account/payment/list";
    /**
     * <H5>从服务器获取病历下载</H5>
     */
    public final static String USER_GET_UPLOADPATIENTURL       = "/user/getUploadPatientUrl";

    // ==========================================================================
    // ============上传信息
    // ==========================================================================
    /**
     * <H5>上传头像</H5>
     */
    public final static String UPLOAD_IMG                      = "/upload/uploadImg";
    /**
     * <H5>我的复诊-上传病历</H5>
     */
    public final static String UPLOAD_PATIENT                  = "/upload/uploadPatient";
    /**
     * <H5>上传本地病历</H5>
     */
    public final static String UPLOAD_PATIENT_RECORD           = "/upload/uploadPatientRecord";

    // ==========================================================================
    // ============首页
    // ==========================================================================
    /**
     * <H5>提醒信息内容</H5>
     */
    public final static String USER_MESSAGE_LIST               = "/user/message";
    /**
     * <H5>提醒信息列表</H5>
     */
    public final static String USER_MESSAGE_NOTICELIST         = "/user/message/noticeList";

    // ==========================================================================
    // ============患者功能
    // ==========================================================================
    /**
     * <H5>病历归档-查看病历列表</H5>
     */
    public final static String PATIENT_LIST                    = "/patient/list";
    /**
     * <H5>病历归档-病历用药列表</H5>
     */
    public final static String PATIENT_DRUG_LIST               = "/patient/drugList";
    /**
     * <H5>在线复诊-复诊病历列表</H5>
     */
    public final static String PATIENT_REVISIT_LIST            = "/patient/revisit/list";
    /**
     * <H5>在线复诊-复诊病历病种列表</H5>
     */
    public final static String PATIENT_REVISIT_DIAG_LIST       = "/patient/revisit/diagList";
    /**
     * <H5>我的复诊-患者回复（同医生）</H5>
     */
    public final static String ASKQUESTION_REPLY               = "/askQuestion/reply";
    /**
     * <H5>我的复诊-患者病历列表</H5>
     */
    public final static String ASKQUESTION_REPLY_DOCTORLIST    = "/askQuestion/reply/doctorList";

    // ==========================================================================
    // ============医生功能
    // ==========================================================================
    /**
     * <H5>我的复诊-患者病历列表</H5>
     */
    public final static String ASKQUESTION_REPLY_PATIENTLIST   = "/askQuestion/reply/patientList";
    /**
     * <H5>我的复诊-患者病历详情</H5>
     */
    public final static String ASKQUESTION_REPLY_INFO          = "/askQuestion/reply/info";
    /**
     * <H5>获取问诊收费配置</H5>
     */
    public final static String ASKQUESTION_GETMONEY            = "/askQuestion/getAskQuestionMoney";
    /**
     * <H5>设置问诊收费配置</H5>
     */
    public final static String ASKQUESTION_SETMONEY            = "/askQuestion/setAskQuestionMoney";
    /**
     * <H5>医生一览</H5>
     */
    public final static String DOCTOR_LIST                     = "/doctor/list";
    /**
     * <H5>医生详情</H5>
     */
    public final static String DOCTOR_INFO                     = "/doctor/getDoctorInfo";

    // ==========================================================================
    // ============预约挂号
    // ==========================================================================
    /**
     * <H5>我的预约-患者挂号一览（患者）</H5>
     */
    public final static String USER_MYGUAHAO_ORDER             = "/user/myGuahaoOrder";
    /**
     * <H5>我的预约-患者挂号一览（医生）</H5>
     */
    public final static String USER_MYPATIENT_ORDER            = "/user/myPatientOrder";
    /**
     * <H5>挂号医院一览</H5>
     */
    public final static String GUAHAO_HOSPITAL_LIST            = "/guahao/hospital/list";
    /**
     * <H5>医院科室分类</H5>
     */
    public final static String GUAHAO_HOSPITAL_DEPTCATEGORY    = "/guahao/hospital/deptCategory";
    /**
     * <H5>预约日期列表</H5>
     */
    public final static String GUAHAO_HOSPITAL_ORDERDATE       = "/guahao/hospital/orderDate";
    /**
     * <H5>按专家预约列表</H5>
     */
    public final static String GUAHAO_HOSPITAL_ORDEREXPERT     = "/guahao/hospital/orderExpert";
    /**
     * <H5>按日期预约列表</H5>
     */
    public final static String GUAHAO_HOSPITAL_ORDERDATEEXPERT = "/guahao/hospital/orderDateExpert";
    /**
     * <H5>预约挂号</H5>
     */
    public final static String GUAHAO_FASTORDER                = "/guahao/fastorder";

    // ==========================================================================
    // ============商城
    // ==========================================================================
    /**
     * <H5>商品列表</H5>
     */
    public final static String GOODS_LIST                      = "/goods/list";
    /**
     * <H5>我的购物车</H5>
     */
    public final static String GOODS_CART_LIST                 = "/goods/cart/list";
    /**
     * <H5>添加购物车</H5>
     */
    public final static String GOODS_CART_ADD                  = "/goods/cart/addCartItem";
    /**
     * <H5>清空购物车</H5>
     */
    public final static String GOODS_CART_CLEAR                = "/goods/cart/clearCart";
    /**
     * <H5>删除购物车</H5>
     */
    public final static String GOODS_CART_DELETE               = "/goods/cart/deleteCartItem";
    /**
     * <H5>我的订单</H5>
     */
    public final static String GOODS_ORDER_LIST                = "/goods/order/orderList";
    /**
     * <H5>取消订单</H5>
     */
    public final static String GOODS_ORDER_CANCEL              = "/goods/order/cancelOrder";
    /**
     * <H5>订单明细</H5>
     */
    public final static String GOODS_ORDER_DETAIL              = "/goods/order/orderDetail";

    // ==========================================================================
    // ============图文视频
    // ==========================================================================
    /**
     * <H5>上传视频图片</H5>
     */
    public final static String UPLOAD_UPLOADFILES              = "/upload/uploadFiles";
    /**
     * <H5>上传视频图片列表</H5>
     */
    public final static String NEWS_PUB_LIST                   = "/news_pub/list";
    /**
     * <H5>上传视频图片详情</H5>
     */
    public final static String NEWS_PUB_INFO                   = "/news_pub/info";
    /**
     * <H5>上传上传视频图片</H5>
     */
    public final static String NEWS_PUB_DEL                    = "/news_pub/del";

    // ==========================================================================
    // ============资讯信息
    // ==========================================================================
    /**
     * <H5>资讯一览</H5>
     */
    public final static String NEWS_LIST                       = "/healthnews/list";
    /**
     * <H5>资讯详情</H5>
     */
    public final static String NEWS_INFO                       = "/healthnews/info";
    /**
     * <H5>关注资讯</H5>
     */
    public final static String NEWS_FOCUS                      = "/healthnews/focus";
    /**
     * <H5>取消关注资讯</H5>
     */
    public final static String NEWS_FOCUS_OFF                  = "/healthnews/focus/off";
    /**
     * <H5>我关注的资讯列表</H5>
     */
    public final static String NEWS_FOCUS_LIST                 = "/healthnews/focus/list";
    /**
     * <H5>广告列表</H5>
     */
    public final static String NEWS_AD_LIST                    = "/healthnews/ad/list";
    /**
     * <H5>资讯搜索</H5>
     */
    public final static String NEWS_SEARCH                     = "/news/search";

    // ==========================================================================
    // ============极光推送
    // ==========================================================================
    /**
     * <H5>推送消息</H5>
     */
    public final static String PUSH_PUSHMSG                    = "/push/pushmsg";

    // ==========================================================================
    // ============支付
    // ==========================================================================
    /**
     * <H5>拉取预付单-商城</H5>
     */
    public final static String PAY_ORDERPAY                    = "/pay/orderPay";
    /**
     * <H5>拉取预付单</H5>
     */
    public final static String PAY_ORDERPAYT                   = "/pay/orderPayT";
}
