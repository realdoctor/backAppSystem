package com.kanglian.healthcare.back.web;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.fastjson.JSONObject;
import com.easyway.business.framework.json.JsonClothProcessor;
import com.easyway.business.framework.mybatis.annotion.SingleValue;
import com.easyway.business.framework.pojo.Grid;
import com.easyway.business.framework.springmvc.controller.CrudController;
import com.easyway.business.framework.springmvc.result.ResultBody;
import com.easyway.business.framework.springmvc.result.ResultUtil;
import com.kanglian.healthcare.authorization.annotation.Authorization;
import com.kanglian.healthcare.back.dal.pojo.NoticeMessage;
import com.kanglian.healthcare.back.service.NoticeMessageBo;

/**
 * 消息提醒
 * 
 * @author xl.liu
 */
@Authorization
@RestController
@RequestMapping(value = "/message")
public class NoticeMessageController extends CrudController<NoticeMessage, NoticeMessageBo> {

    /**
     * 最近消息提醒一览
     * 
     * @param query
     * @return
     * @throws Exception
     */
    @GetMapping
    public ResultBody list(NoticeMessageQuery query) throws Exception {
        final List<NoticeMessage> dataList = this.bo.query(query);
        List<JSONObject> resultList = ResultUtil.wearCloth(dataList, new JsonClothProcessor() {

            @Override
            public JSONObject wearCloth(Object pojo, JSONObject jsonObject) {
                NoticeMessage message = (NoticeMessage) pojo;
                JSONObject newJsonObject = new JSONObject();
                try {
                    newJsonObject.put("noticeTypeId", message.getNoticeTypeId());
                    newJsonObject.put("noticeType", message.getNoticeType());
                    newJsonObject.put("content", message.getContent());
                    newJsonObject.put("addTime", message.getAddTime());
                } catch (Exception e) {
                    // TODO: handle exception
                }
                return newJsonObject;
            }

        });
        return ResultUtil.success(resultList);
    }

    /**
     * 消息提醒列表
     * 
     * @param query
     * @return
     * @throws Exception
     */
    @GetMapping("/frontList")
    public ResultBody noticeMessageList(NoticeMessageQuery query) throws Exception {
        final Grid grid = this.bo.queryFrontList(query);
        return ResultUtil.success(grid, new JsonClothProcessor() {

            @Override
            public JSONObject wearCloth(Object pojo, JSONObject jsonObject) {
                NoticeMessage message = (NoticeMessage) pojo;
                try {
                    // 就诊用药提醒
                    if ("1".equals(message.getNoticeTypeId())) {
                        jsonObject.put("dataList", message.getNoticeDiagDrugList());
                        jsonObject.remove("noticeDiagList");
                        jsonObject.remove("noticeDiagDrugList");
                        jsonObject.remove("noticeCommentList");
                    } 
                    // 病历更新
                    else if ("2".equals(message.getNoticeTypeId())) {
                        jsonObject.put("dataList", message.getNoticeDiagList());
                        jsonObject.remove("noticeDiagList");
                        jsonObject.remove("noticeDiagDrugList");
                        jsonObject.remove("noticeCommentList");
                    }
                    // 新的回复消息
                    else if ("3".equals(message.getNoticeTypeId())) {
                        jsonObject.put("dataList", message.getNoticeCommentList());
                        jsonObject.remove("noticeDiagList");
                        jsonObject.remove("noticeDiagDrugList");
                        jsonObject.remove("noticeCommentList");
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
