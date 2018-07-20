package com.kanglian.healthcare.back.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.easyway.business.framework.mybatis.annotion.SingleValue;
import com.easyway.business.framework.mybatis.query.ConditionQuery;
import com.easyway.business.framework.mybatis.query.condition.SingleValueCondition;
import com.easyway.business.framework.pojo.Grid;
import com.easyway.business.framework.springmvc.controller.CrudController;
import com.easyway.business.framework.springmvc.result.ResultBody;
import com.easyway.business.framework.springmvc.result.ResultUtil;
import com.easyway.business.framework.util.StringUtil;
import com.kanglian.healthcare.authorization.annotation.Authorization;
import com.kanglian.healthcare.back.dal.pojo.UploadContent;
import com.kanglian.healthcare.back.service.UploadContentBo;
import com.kanglian.healthcare.exception.InvalidParamException;

/**
 * 发布视频图片
 * 
 * @author xl.liu
 */
@Authorization
@RestController
public class UploadContentController extends CrudController<UploadContent, UploadContentBo> {

    /**
     * 医生发布的视频图片列表
     * 
     * @param query
     * @return
     * @throws Exception
     */
    @GetMapping("/news_pub/list")
    public ResultBody list(final ContentQuery query) throws Exception {
        if (StringUtil.isEmpty(query.getUserId())) {
            throw new InvalidParamException("userId");
        }
        return super.list(query);
    }

    /**
     * 视频图片详情
     * 
     * @param query
     * @return
     * @throws Exception
     */
    @GetMapping("/news_pub/info")
    public ResultBody info(String pubId) throws Exception {
        return ResultUtil.success(this.bo.getByPubId(pubId));
    }

    public static class ContentQuery extends Grid {
        private String userId;
        private String type;

        @SingleValue(column = "user_id", equal = "=")
        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        @SingleValue(column = "type", equal = "=")
        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        @Override
        public ConditionQuery buildConditionQuery() {
            ConditionQuery query = super.buildConditionQuery();
            if (!"3".equals(getType())) {
                query.addSingleValueCondition(new SingleValueCondition("type", "!=", 3));
            }
            return query;
        }

    }
}
