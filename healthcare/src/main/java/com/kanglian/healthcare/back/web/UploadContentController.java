package com.kanglian.healthcare.back.web;

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
import com.kanglian.healthcare.back.constant.ApiMapping;
import com.kanglian.healthcare.back.pojo.UploadContent;
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
    @GetMapping(ApiMapping.NEWS_PUB_LIST)
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
    @GetMapping(ApiMapping.NEWS_PUB_INFO)
    public ResultBody info(String pubId) throws Exception {
        return ResultUtil.success(this.bo.getByPubId(pubId));
    }

    /**
     * 视频图片删除
     * 
     * @param query
     * @return
     * @throws Exception
     */
    @PostMapping(ApiMapping.NEWS_PUB_DEL)
    public ResultBody del(@RequestBody UploadContent uploadContent) throws Exception {
        String pubId = uploadContent.getPubId();
        if (StringUtil.isEmpty(pubId)) {
            throw new InvalidParamException("pubId");
        }
        this.bo.deleteByPubId(pubId);
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
            if (!"3".equals(getType())) {
                query.addSingleValueCondition(new SingleValueCondition("type", "!=", 3));
            }
            return query;
        }

    }
}
