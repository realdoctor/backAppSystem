package com.kanglian.healthcare.back.web;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.kanglian.healthcare.authorization.annotation.Authorization;
import com.kanglian.healthcare.back.dal.pojo.MyDoctor;
import com.kanglian.healthcare.back.dal.pojo.User;
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
@RequestMapping(value = "/user/mydoctor")
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
    @GetMapping("/list")
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
    @PostMapping("/add")
    public ResultBody addDoctor(@RequestBody MyDoctor myDoctor) {
        String userId = myDoctor.getUserId();
        String doctorId = myDoctor.getDoctorId();
        if (StringUtil.isEmpty(userId)) {
            throw new InvalidParamException("userId");
        } else {
            try {
                Long.valueOf(userId);
            } catch (Exception e) {
                throw new InvalidOperationException("userId");
            }
        }
        
        if (StringUtil.isEmpty(doctorId)) {
            throw new InvalidParamException("doctorId");
        } else {
            try {
                Long.valueOf(doctorId);
            } catch (Exception e) {
                throw new InvalidOperationException("doctorId");
            }
        }
        
        User u2 = userBo.get(Long.valueOf(doctorId));
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
