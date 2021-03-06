package com.kanglian.healthcare.back.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.easyway.business.framework.mybatis.annotion.SingleValue;
import com.easyway.business.framework.pojo.Grid;
import com.easyway.business.framework.springmvc.controller.CrudController;
import com.easyway.business.framework.springmvc.result.ResultBody;
import com.easyway.business.framework.springmvc.result.ResultUtil;
import com.easyway.business.framework.util.DateUtil;
import com.easyway.business.framework.util.StringUtil;
import com.kanglian.healthcare.authorization.annotation.Authorization;
import com.kanglian.healthcare.back.constant.ApiMapping;
import com.kanglian.healthcare.back.pojo.MyDoctor;
import com.kanglian.healthcare.back.pojo.User;
import com.kanglian.healthcare.back.service.MyDoctorBo;
import com.kanglian.healthcare.back.service.UserBo;
import com.kanglian.healthcare.exception.InvalidOperationException;
import com.kanglian.healthcare.exception.InvalidParamException;

/**
 * 患者扫描医生关注
 * 
 * @author xl.liu
 */
@Authorization
@RestController
public class MyDoctorController extends CrudController<MyDoctor, MyDoctorBo> {

    @Autowired
    private UserBo userBo;
    
    /**
     * 关注医生列表
     * 
     * @param query
     * @return
     * @throws Exception
     */
    @GetMapping(ApiMapping.USER_MYDOCTOR_LIST)
    public ResultBody list(MyDoctorQuery query) throws Exception {
        if (StringUtil.isEmpty(query.getUserId())) {
            throw new InvalidParamException("userId");
        }
        return ResultUtil.success(this.bo.frontList(query));
    }
    
    /**
     * 关注医生
     * 
     * @param myDoctor
     * @return
     */
    @PostMapping(ApiMapping.USER_MYDOCTOR_ADD)
    public ResultBody addDoctor(@RequestBody MyDoctor myDoctor) {
        String userId = myDoctor.getUserId();
        String doctorUserId = myDoctor.getDoctorUserId();
        if (StringUtil.isEmpty(userId)) {
            throw new InvalidParamException("userId");
        } else {
            try {
                Long.valueOf(userId);
            } catch (Exception e) {
                throw new InvalidOperationException("userId");
            }
        }
        
        if (StringUtil.isEmpty(doctorUserId)) {
            throw new InvalidParamException("doctorUserId");
        } else {
            try {
                Long.valueOf(doctorUserId);
            } catch (Exception e) {
                throw new InvalidOperationException("doctorUserId");
            }
        }
        
        User u2 = userBo.get(Long.valueOf(doctorUserId));
        if (u2 == null) {
            return ResultUtil.error("医生不存在");
        }
        
        if (this.bo.get(myDoctor) != null) {
            return ResultUtil.success();// 已关注
        }
        
        myDoctor.setAddTime(DateUtil.currentDate());
        this.bo.save(myDoctor);
        return ResultUtil.success();
    }
    
    public static class MyDoctorQuery extends Grid {
        private String userId;

        @SingleValue(column = "user_id", equal = "=")
        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }
    }
}
