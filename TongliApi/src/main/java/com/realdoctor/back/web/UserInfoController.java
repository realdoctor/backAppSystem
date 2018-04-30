package com.realdoctor.back.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.easyway.business.framework.mybatis.annotion.SingleValue;
import com.easyway.business.framework.pojo.Grid;
import com.easyway.business.framework.springmvc.controller.CrudController;
import com.easyway.business.framework.springmvc.result.ResultBody;
import com.easyway.business.framework.springmvc.result.ResultUtil;
import com.realdoctor.back.dal.pojo.UserInfo;
import com.realdoctor.back.service.UserInfoBo;

@RestController
@RequestMapping(value = "/userInfo")
public class UserInfoController extends CrudController<UserInfo, UserInfoBo> {

    /**
     * 用户基本信息
     * 
     * @param query
     * @return
     * @throws Exception
     */
    @GetMapping
    public ResultBody getUserInfo(UserQuery query) throws Exception {
        return ResultUtil.success(null);
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
