package com.kanglian.healthcare.back.web;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
import com.kanglian.healthcare.authorization.annotation.CurrentUser;
import com.kanglian.healthcare.back.dal.pojo.UploadContent;
import com.kanglian.healthcare.back.dal.pojo.User;
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
     * 视频图片列表
     * 
     * @param query
     * @return
     * @throws Exception
     */
    @GetMapping("/news_pub/list")
    public ResultBody list(ContentQuery query) throws Exception {
        return super.list(query);
    }

    /**
     * 详情列表
     * 
     * @param query
     * @return
     * @throws Exception
     */
    @GetMapping("/news_pub/info")
    public ResultBody info(String pubId) throws Exception {
        return ResultUtil.success(this.bo.getByPubId(pubId));
    }

    /**
     * 发布视频图片
     * 
     * @param user
     * @param content
     * @return
     * @throws Exception
     */
    @PostMapping("/news_pub/addUploadContent")
    public ResultBody addUploadContent(@CurrentUser User user,
            @RequestBody UploadContent uploadContent) throws Exception {
        if (StringUtil.isEmpty(uploadContent.getPubId())) {
            throw new InvalidParamException("pubId");
        }
        if (StringUtil.isEmpty(uploadContent.getContent())) {
            throw new InvalidParamException("content");
        }
        final String orderId = uploadContent.getPubId();
        List<UploadContent> list = this.bo.getByPubId(orderId);
        if (list != null) {
            UploadContent content = new UploadContent();
            content.setPubId(orderId);
            content.setContent(uploadContent.getContent());
            this.bo.updateByPubId(uploadContent);
        }
        return ResultUtil.success();
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
            query.addSingleValueCondition(new SingleValueCondition("type", "!=", 3));
            return query;
        }

    }
}
