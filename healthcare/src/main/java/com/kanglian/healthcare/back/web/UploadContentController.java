package com.kanglian.healthcare.back.web;

import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
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

@Authorization
@RestController
public class UploadContentController extends CrudController<UploadContent, UploadContentBo> {

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
        if (StringUtil.isEmpty(uploadContent.getOrderId())) {
            throw new InvalidParamException("orderId");
        }
        if (StringUtil.isEmpty(uploadContent.getContent())) {
            throw new InvalidParamException("content");
        }
        final String orderId = uploadContent.getOrderId();
        List<UploadContent> list = this.bo.getByUUId(orderId);
        if (list != null) {
            UploadContent content = new UploadContent();
            content.setOrderId(orderId);
            content.setContent(uploadContent.getContent());
            this.bo.updateByUUId(uploadContent);
        }
        return ResultUtil.success();
    }
}
