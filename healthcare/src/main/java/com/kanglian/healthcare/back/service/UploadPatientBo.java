package com.kanglian.healthcare.back.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.kanglian.healthcare.back.dao.UploadPatientDao;
import com.kanglian.healthcare.back.pojo.AskQuestionAnswer;
import com.kanglian.healthcare.back.pojo.UploadPatient;
import com.kanglian.healthcare.common.NewCrudBo;
import com.kanglian.healthcare.exception.DBException;

@Service
public class UploadPatientBo extends NewCrudBo<UploadPatient, UploadPatientDao> {

    @Autowired
    private AskQuestionAnswerBo askQuestionAnswerBo;

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public void updateAndSaveQuestion(AskQuestionAnswer updateAskQuestionAnswer,
            AskQuestionAnswer saveAskQuestionAnswer, UploadPatient uploadPatient) throws Exception {
        try {
            if (updateAskQuestionAnswer != null) {
                askQuestionAnswerBo.update(updateAskQuestionAnswer);
            }
            if (saveAskQuestionAnswer != null) {
                askQuestionAnswerBo.save(saveAskQuestionAnswer);
                if (uploadPatient != null) {
                    uploadPatient.setQuestionId(saveAskQuestionAnswer.getId().intValue());
                    this.dao.save(uploadPatient);
                }
            }
        } catch (Exception ex) {
            throw new DBException(ex);
        }
    }

}
