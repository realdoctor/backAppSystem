package com.kanglian.healthcare.back.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.kanglian.healthcare.back.common.NewCrudBo;
import com.kanglian.healthcare.back.dal.dao.UploadPatientDao;
import com.kanglian.healthcare.back.dal.pojo.AskQuestionAnswer;
import com.kanglian.healthcare.back.dal.pojo.UploadPatient;
import com.kanglian.healthcare.exception.DBException;

@Service
public class UploadPatientBo extends NewCrudBo<UploadPatient, UploadPatientDao> {

    @Autowired
    private AskQuestionAnswerBo askQuestionAnswerBo;

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public void saveUploadPatientAndQuestion(UploadPatient uploadPatient,
            AskQuestionAnswer askQuestionAnswer) {
        try {
            this.dao.save(uploadPatient);
            askQuestionAnswerBo.save(askQuestionAnswer);
        } catch (Exception ex) {
            throw new DBException(ex);
        }
    }
}
