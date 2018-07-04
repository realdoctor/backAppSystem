package com.kanglian.healthcare.back.web;

import org.springframework.web.bind.annotation.RestController;
import com.easyway.business.framework.springmvc.controller.CrudController;
import com.kanglian.healthcare.back.dal.pojo.AskQuestionAnswer;
import com.kanglian.healthcare.back.service.AskQuestionAnswerBo;

@RestController
public class AskQuestionAnswerController extends CrudController<AskQuestionAnswer,AskQuestionAnswerBo> {

}
