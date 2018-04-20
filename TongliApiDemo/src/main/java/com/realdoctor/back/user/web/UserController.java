package com.realdoctor.back.user.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.easyway.business.framework.mybatis.annotion.SingleValue;
import com.easyway.business.framework.pojo.Grid;
import com.easyway.business.framework.springmvc.controller.CrudController;
import com.easyway.business.framework.springmvc.result.ResultBody;
import com.easyway.business.framework.springmvc.result.ResultUtil;
import com.easyway.business.framework.util.DateUtil;
import com.easyway.business.framework.util.StringUtil;
import com.realdoctor.back.user.bo.UserBo;
import com.realdoctor.back.user.pojo.User;
import com.realdoctor.util.MD5Util;
import com.realdoctor.util.ValidateUtil;

@RestController
@RequestMapping(value = "/user")
public class UserController extends CrudController<User, UserBo> {

    @GetMapping
    public ResultBody list(UserQuery query) throws Exception {
        return super.list(query);
    }

    /**
     * 用户登录
     * 
     * @param user
     * @return
     * @throws Exception
     */
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
        if(!ValidateUtil.isMobile(mobilePhone)){
            return ResultUtil.error("手机号格式不正确！");
        }
        user = this.bo.login(user);
        if (user == null) {
            return ResultUtil.error("用户不存在！");
        } else {
            if (!MD5Util.encrypt(pwd).equals(user.getPwd())) {
                return ResultUtil.error("密码错误！");
            }
        }
        return ResultUtil.success(user.toJSONObject());
    }

    /**
     * 用户注册
     * 
     * @param user
     * @return
     * @throws Exception
     */
    @PostMapping("/regist")
    public ResultBody regist(@RequestBody User user) throws Exception {
        String mobilePhone = user.getMobilePhone();
        String pwd = user.getPwd();
        if (StringUtil.isEmpty(mobilePhone)) {
            return ResultUtil.error("手机号不能为空！");
        }
        if (StringUtil.isEmpty(pwd)) {
            return ResultUtil.error("密码不能为空！");
        }
        if(!ValidateUtil.isMobile(mobilePhone)){
            return ResultUtil.error("手机号格式不正确！");
        }
        // 判断用户唯一
        if(this.bo.ifExist(mobilePhone)){
            return ResultUtil.error("用户已存在！");
        }
        // 手机验证码
        // 加密密码
        user.setPwd(MD5Util.encrypt(pwd));
        user.setAddTime(DateUtil.currentDate());
        // 保存
        this.bo.save(user);
        return ResultUtil.success();
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
