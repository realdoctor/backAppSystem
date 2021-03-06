package com.kanglian.healthcare.back.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.fastjson.JSONObject;
import com.easyway.business.framework.json.JsonClothProcessor;
import com.easyway.business.framework.mybatis.annotion.SingleValue;
import com.easyway.business.framework.pojo.Grid;
import com.easyway.business.framework.springmvc.controller.CrudController;
import com.easyway.business.framework.springmvc.result.ResultBody;
import com.easyway.business.framework.springmvc.result.ResultUtil;
import com.kanglian.healthcare.authorization.annotation.Authorization;
import com.kanglian.healthcare.authorization.annotation.CurrentUser;
import com.kanglian.healthcare.back.constant.ApiMapping;
import com.kanglian.healthcare.back.pojo.NoticeMessage;
import com.kanglian.healthcare.back.pojo.User;
import com.kanglian.healthcare.back.service.NoticeMessageBo;

/**
 * 消息提醒
 * 
 * @author xl.liu
 */
@Authorization
@RestController
public class NoticeMessageController extends CrudController<NoticeMessage, NoticeMessageBo> {

    /**
     * 最近消息提醒一览
     * 
     * @param query
     * @return
     * @throws Exception
     */
    @GetMapping(ApiMapping.USER_MESSAGE_LIST)
    public ResultBody list(NoticeMessageQuery query, @CurrentUser User user) throws Exception {
        query.setUserId(String.valueOf(user.getUserId()));
        return ResultUtil.success(this.bo.queryList(query));
    }

    /**
     * 消息提醒详情
     * 
     * @param query
     * @return
     * @throws Exception
     */
    @GetMapping(ApiMapping.USER_MESSAGE_NOTICELIST)
    public ResultBody noticeMessageList(NoticeMessageQuery query, @CurrentUser User user) throws Exception {
        query.setUserId(String.valueOf(user.getUserId()));
        final Grid grid = this.bo.frontList(query);
        return ResultUtil.success(grid, new JsonClothProcessor() {

            @Override
            public JSONObject wearCloth(Object pojo, JSONObject jsonObject) {
                NoticeMessage message = (NoticeMessage) pojo;
                try {
                    // 就诊用药提醒
                    if ("1".equals(message.getNoticeTypeId())) {
                        jsonObject.put("dataList", message.getNoticeDiagDrugList());
                    }
                    // 病历更新
                    else if ("2".equals(message.getNoticeTypeId())) {
                        jsonObject.put("dataList", message.getNoticeDiagList());
                    }
                    // 新的回复消息
                    else if ("3".equals(message.getNoticeTypeId())) {
                        jsonObject.put("dataList", message.getNoticeCommentList());
                    }
                } catch (Exception e) {
                    // TODO: handle exception
                }
                return jsonObject;
            }

        });
    }

    public static class NoticeMessageQuery extends Grid {

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
