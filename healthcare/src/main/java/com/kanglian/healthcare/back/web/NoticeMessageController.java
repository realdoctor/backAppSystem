package com.kanglian.healthcare.back.web;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.easyway.business.framework.util.StringUtil;
import com.kanglian.healthcare.back.dal.pojo.NoticeMessage;
import com.kanglian.healthcare.back.service.NoticeMessageBo;
import com.kanglian.healthcare.back.service.UserBo;

/**
 * 消息提醒
 * 
 * @author xl.liu
 */
@RestController
@RequestMapping(value = "/message")
public class NoticeMessageController extends CrudController<NoticeMessage, NoticeMessageBo> {

    @Autowired
    private UserBo userBo;
    
    /**
     * 最近消息提醒一览
     * 
     * @param query
     * @return
     * @throws Exception
     */
    @GetMapping
    public ResultBody list(NoticeMessageQuery query) throws Exception {
        if (StringUtil.isEmpty(query.getUserId())) {
            return ResultUtil.error("用户未登录！");
        }
        if (!userBo.ifExist(Long.valueOf(query.getUserId()))) {
            return ResultUtil.error("用户不存在");
        }
        return ResultUtil.success(this.bo.query(query));
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
