package com.kanglian.healthcare.back.web;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.easyway.business.framework.common.annotation.PerformanceClass;
import com.easyway.business.framework.mybatis.annotion.SingleValue;
import com.easyway.business.framework.pojo.Grid;
import com.easyway.business.framework.springmvc.controller.CrudController;
import com.easyway.business.framework.springmvc.result.ResultBody;
import com.easyway.business.framework.springmvc.result.ResultUtil;
import com.easyway.business.framework.util.DateUtil;
import com.easyway.business.framework.util.StringUtil;
import com.kanglian.healthcare.authorization.annotation.Authorization;
import com.kanglian.healthcare.authorization.annotation.CurrentUser;
import com.kanglian.healthcare.authorization.util.JwtUtil;
import com.kanglian.healthcare.back.constants.Constants;
import com.kanglian.healthcare.back.dal.pojo.User;
import com.kanglian.healthcare.back.service.UserBo;
import com.kanglian.healthcare.exception.InvalidOperationException;
import com.kanglian.healthcare.util.IdCardUtil;
import com.kanglian.healthcare.util.JsonUtil;
import com.kanglian.healthcare.util.MD5Util;
import com.kanglian.healthcare.util.NumberUtil;
import com.kanglian.healthcare.util.RedisCacheManager;
import com.kanglian.healthcare.util.SmsUtil;
import com.kanglian.healthcare.util.ValidateUtil;

@RestController
@RequestMapping(value = "/user")
public class UserController extends CrudController<User, UserBo> {

    @Autowired
    private RedisCacheManager redisCacheManager;
    
    @GetMapping
    public ResultBody list(UserQuery query) throws Exception {
        if (StringUtil.isEmpty(query.getMobilePhone())) {
            return ResultUtil.error("手机号不能为空！");
        }
        if (!ValidateUtil.isPhone(query.getMobilePhone())) {
            return ResultUtil.error("请输入正确的11位手机号！");
        }
        return super.list(query);
    }

    /**
     * 用户登录
     * 
     * @param user
     * @return
     * @throws Exception
     */
    @PerformanceClass
    @PostMapping("/login")
    public ResultBody login(@RequestBody User user) throws Exception {
        String mobilePhone = user.getMobilePhone();
        String pwd = user.getPwd();
        if (StringUtil.isEmpty(mobilePhone)) {
            return ResultUtil.error("手机号不能为空！");
        }
        if (StringUtil.isEmpty(pwd)) {
            return ResultUtil.error("密码不能为空！");
        }
        if (!ValidateUtil.isPhone(mobilePhone)) {
            return ResultUtil.error("请输入正确的11位手机号！");
        }
        user = this.bo.login(user);
        if (user == null) {
            return ResultUtil.error("用户不存在！");
        } else {
            if (!MD5Util.encrypt(pwd).equals(user.getPwd())) {
                logger.info("手机号{}，密码不正确", mobilePhone);
                return ResultUtil.error("密码不正确！");
            }
        }
        
        // 生成token
        String accessToken = JwtUtil.generToken(mobilePhone, JsonUtil.beanToJson(user), JwtUtil.JWT_TTL);
        this.bo.createRelationship(mobilePhone, user.getUserId(), accessToken);
        logger.info("========手机号{}，登录客户端。token={}", new Object[] {mobilePhone, accessToken});

        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("token", accessToken);
        resultMap.put("user", user);
        return ResultUtil.success(resultMap);
    }

    /**
     * 用户注册
     * 
     * @param user
     * @return
     * @throws Exception
     */
    @PerformanceClass
    @PostMapping("/regist")
    public ResultBody regist(@RequestBody User user) throws Exception {
        String mobilePhone = user.getMobilePhone();
        String pwd = user.getPwd();
        String verifyCode = user.getVerifyCode();
        if (StringUtil.isEmpty(mobilePhone)) {
            return ResultUtil.error("手机号不能为空！");
        }
        if (StringUtil.isEmpty(pwd)) {
            return ResultUtil.error("密码不能为空！");
        }
        if (StringUtil.isEmpty(verifyCode)) {
            return ResultUtil.error("验证码不能为空！");
        }
        if (!ValidateUtil.isPhone(mobilePhone)) {
            return ResultUtil.error("请输入正确的11位手机号！");
        }
        // 判断用户唯一
        if (this.bo.ifExist(mobilePhone)) {
            logger.info("手机号{}，用户已存在！", mobilePhone);
            return ResultUtil.error("用户已存在！");
        }
        // 手机验证码
        Object cacheVCode = redisCacheManager.getCacheObject(Constants.VERIFY_CODE_KEY_PREFIX+mobilePhone);
        if (cacheVCode == null) {
            return ResultUtil.error("验证码过期，请重新获取！");
        } else {
            if (!cacheVCode.equals(verifyCode)) {
                return ResultUtil.error("手机验证码不正确！");
            }
        }
        // 密码加密
        user.setPwd(MD5Util.encrypt(pwd));
        user.setAddTime(DateUtil.currentDate());
        // 保存
        this.bo.save(user);
        return ResultUtil.success();
    }

    /**
     * 发送短信验证码
     * 
     * @return
     * @throws Exception
     */
    @PerformanceClass
    @RequestMapping(method = RequestMethod.GET, value = "/sendCode")
    public ResultBody sendCode(String mobilePhone) throws Exception {
        if (StringUtil.isBlank(mobilePhone)) {
            return ResultUtil.error("手机号不能为空！");
        }
        if (!ValidateUtil.isPhone(mobilePhone)) {
            return ResultUtil.error("请输入正确的11位手机号！");
        }
        String verifyCode = NumberUtil.getRandByNum(Constants.VERIFY_CODE_NUM);
        boolean bool = SmsUtil.sendCode(mobilePhone, verifyCode);
        if (!bool) {
            return ResultUtil.error("发送手机验证码失败！");
        }
        // 5分钟过期
        redisCacheManager.setCacheObject(Constants.VERIFY_CODE_KEY_PREFIX + mobilePhone, verifyCode, 5L,
                TimeUnit.MINUTES);
        Map<String, String> retMap = new HashMap<String, String>();
        retMap.put("code", verifyCode);
        return ResultUtil.success(retMap);
    }
    
    /**
     * 修改密码
     * 
     * @param user
     * @return
     * @throws Exception
     */
    @Authorization
    @PostMapping("/updatePwd")
    public ResultBody updatePwd(@RequestBody User user) throws Exception {
        if (user == null) {
            throw new InvalidOperationException();
        }
        String mobilePhone = user.getMobilePhone();
        String pwd = user.getPwd();
        if (StringUtil.isEmpty(mobilePhone)) {
            return ResultUtil.error("手机号不能为空！");
        }
        if (StringUtil.isEmpty(pwd)) {
            return ResultUtil.error("密码不能为空！");
        }
        if (!ValidateUtil.isPhone(mobilePhone)) {
            return ResultUtil.error("请输入正确的11位手机号！");
        }
        User userT = this.bo.login(user);
        if (userT == null) {
            return ResultUtil.error("用户不存在！");
        }
        userT.setPwd(MD5Util.encrypt(pwd));
        userT.setLastUpdateDtime(DateUtil.currentDate());
        this.bo.update(userT);
        logger.info("====================================手机用户{}，修改密码。", userT.getMobilePhone());
        return ResultUtil.success();
    }

    
    /**
     * 用户退出
     * 
     * @param user
     * @return
     * @throws Exception
     */
    @Authorization
    @PostMapping("/logout")
    public ResultBody logout(@CurrentUser User user) throws Exception {
        if (user == null) {
            throw new InvalidOperationException();
        }
        logger.info("====================================手机用户{}，退出登录。", user.getMobilePhone());
        return ResultUtil.success();
    }

    /**
     * 客户端自动刷新token
     * 
     * @param user
     * @return
     * @throws Exception
     */
    @Authorization
    @GetMapping("/refreshToken")
    public ResultBody refreshToken(@CurrentUser User user) throws Exception {
        if (user == null) {
            throw new InvalidOperationException();
        }
        logger.info("====================================手机用户{}，客户端自动刷新token。", user.getMobilePhone());
        final String mobilePhone = user.getMobilePhone();
        // 重新生成token
        String token = JwtUtil.generToken(mobilePhone, JsonUtil.beanToJson(user), JwtUtil.JWT_TTL);
        this.bo.createRelationship(mobilePhone, user.getUserId(), token);
        logger.info("================手机号{}，重新生成token={}", new Object[] {mobilePhone, token});
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("token", token);
        return ResultUtil.success(resultMap);
    }
    
    /**
     * 实名认证
     * 
     * @param user
     * @return
     * @throws Exception
     */
    @Authorization
    @PostMapping("/certification")
    public ResultBody certification(@RequestBody User user) throws Exception {
        String mobilePhone = user.getMobilePhone();
        String idNumber = user.getIdNo();
        if (StringUtil.isEmpty(mobilePhone)) {
            return ResultUtil.error("手机号不能为空！");
        }
        if (StringUtil.isEmpty(idNumber)) {
            return ResultUtil.error("身份证不能为空！");
        }
        if (!IdCardUtil.isIdcard(idNumber)) {
            return ResultUtil.error("身份证不合法！");
        }
        boolean identifyOk = this.bo.certification(user);
        if (!identifyOk) {
            return ResultUtil.error("实名认证失败");
        }
        return ResultUtil.success();
    }
    
    /**
     * 实名认证审核
     * 
     * @param user
     * @return
     * @throws Exception
     */
    @Authorization
    @GetMapping("/certification/check")
    public ResultBody verifyIdCard(@CurrentUser User user) throws Exception {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if (StringUtil.isBlank(user.getIdNo())) {
            resultMap.put("verifyFlag", "0");// 未认证
        } else {
            resultMap.put("verifyFlag", "1");// 已认证
        }

        return ResultUtil.success(resultMap);
    }
    
    public static class UserQuery extends Grid {

        private String userId;
        private String mobilePhone;

        @SingleValue(column = "user_id", equal = "=")
        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        @SingleValue(column = "mobile_phone", equal = "=")
        public String getMobilePhone() {
            return mobilePhone;
        }

        public void setMobilePhone(String mobilePhone) {
            this.mobilePhone = mobilePhone;
        }
        
    }
}
