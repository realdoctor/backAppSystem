package com.kanglian.healthcare.back.web;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import com.easyway.business.framework.springmvc.result.ResultBody;
import com.easyway.business.framework.springmvc.result.ResultUtil;
import com.easyway.business.framework.util.DateUtil;
import com.easyway.business.framework.util.StringUtil;
import com.kanglian.healthcare.authorization.annotation.Authorization;
import com.kanglian.healthcare.authorization.annotation.CurrentUser;
import com.kanglian.healthcare.back.constants.Constants;
import com.kanglian.healthcare.back.constants.UploadType;
import com.kanglian.healthcare.back.dal.pojo.AskQuestionAnswer;
import com.kanglian.healthcare.back.dal.pojo.UploadContent;
import com.kanglian.healthcare.back.dal.pojo.UploadPatient;
import com.kanglian.healthcare.back.dal.pojo.UploadPatientRecord;
import com.kanglian.healthcare.back.dal.pojo.User;
import com.kanglian.healthcare.back.dal.pojo.UserPic;
import com.kanglian.healthcare.back.jpush.JPushService;
import com.kanglian.healthcare.back.jpush.PushModel;
import com.kanglian.healthcare.back.service.AskQuestionAnswerBo;
import com.kanglian.healthcare.back.service.UploadContentBo;
import com.kanglian.healthcare.back.service.UploadPatientBo;
import com.kanglian.healthcare.back.service.UploadPatientRecordBo;
import com.kanglian.healthcare.back.service.UserBo;
import com.kanglian.healthcare.back.service.UserPicBo;
import com.kanglian.healthcare.exception.InvalidParamException;
import com.kanglian.healthcare.util.FileUtil;
import com.kanglian.healthcare.util.NumberUtil;
import com.kanglian.healthcare.util.PropConfig;
import com.kanglian.healthcare.util.VideoPictureUtil;
import net.coobird.thumbnailator.Thumbnails;

@Authorization
@Controller
@RequestMapping("/upload")
public class UploadController {
    /** logger **/
    private static final Logger logger = LoggerFactory.getLogger(UploadController.class);

    @Autowired
    private UserBo                userBo;
    @Autowired
    private UserPicBo             userPicBo;
    @Autowired
    private UploadContentBo       uploadContentBo;
    @Autowired
    private UploadPatientBo       uploadPatientBo;
    @Autowired
    private UploadPatientRecordBo uploadPatientRecordBo;
    @Autowired
    private AskQuestionAnswerBo   askQuestionAnswerBo;
    @Autowired
    private JPushService          jPushService;

    /**
     * 上传头像
     * 
     * @param user
     * @param imageFile
     * @param request
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/uploadImg", method = RequestMethod.POST)
    public ResultBody headpicUpload(@CurrentUser User user,
            @RequestParam(value = "attach", required = false) MultipartFile imageFile,
            HttpServletRequest request) throws Exception {
        logger.info("===========进入上传图片，user=" + user.getMobilePhone());

        if (imageFile == null) {
            throw new InvalidParamException("attach");
        }

        if (imageFile.isEmpty()) {
            return ResultUtil.error("不能上传空文件");
        }

        logger.info("===========上传图片 {} 兆",
                String.format("%.1f", imageFile.getSize() / (1024.0 * 1024.0)));
        long fileSize = 10 * 1024 * 1024;
        // 如果文件大小大于限制
        if (imageFile.getSize() > fileSize) {
            return ResultUtil.error("图片过大，请选择小于10M的图片");
        }

        // 文件名
        final String fileName = imageFile.getOriginalFilename();
        // 获取文件类型
        String extension = FilenameUtils.getExtension(fileName).toLowerCase();
        if (!Arrays.asList(FileUtil.CONTENT_TYPE_MAP.get("image").split(",")).contains(extension)) {
            return ResultUtil.error("上传格式不符合");
        }

        // 获得物理路径webapp所在路径
        String pathRoot = request.getSession().getServletContext().getRealPath("");
        pathRoot = PropConfig.getInstance().getPropertyValue(Constants.UPLOAD_PATH);
        String thumbnailPath = "";
        try {
            String originalPath = "/files/headpic".concat(FileUtil.randomPathname(extension));
            originalPath =
                    originalPath.substring(0, originalPath.lastIndexOf(".")) + "_appTh." + extension;
            File uploadedFile = new File(pathRoot + originalPath);
            FileUtils.writeByteArrayToFile(uploadedFile, imageFile.getBytes());
            thumbnailPath = "/files/headpic".concat(FileUtil.randomPathname(extension));
            thumbnailPath =
                    thumbnailPath.substring(0, thumbnailPath.lastIndexOf(".")) + "_appTh.png";
            Thumbnails.of(new File(pathRoot + originalPath)).size(200, 200).keepAspectRatio(false)
                    .toFile(new File(pathRoot + thumbnailPath));
            logger.info("===============原始图路径" + originalPath);
            logger.info("======================生成缩略图路径" + thumbnailPath);
            UserPic userPic = userPicBo.get(user.getUserId());
            if (userPic != null) {
                try {
                    // 更新头像，删除原来上传的
                    FileUtils.forceDelete(new File(pathRoot.concat(userPic.getPic())));
                    FileUtils.forceDelete(new File(pathRoot.concat(userPic.getThumbnailPic())));
                } catch (Exception ex) {
                    // TODO: handle exception
                }
                userPic.setPic(originalPath);
                userPic.setThumbnailPic(thumbnailPath);
                userPic.setLastUpdateDtime(DateUtil.currentDate());
                userPicBo.update(userPic);
            } else {
                userPic = new UserPic();
                userPic.setUserId(user.getUserId());
                userPic.setPic(originalPath);
                userPic.setThumbnailPic(thumbnailPath);
                userPic.setAddTime(DateUtil.currentDate());
                userPicBo.save(userPic);
            }
        } catch (Exception e) {
            logger.info("上传头像异常", e);
            return ResultUtil.error("上传失败");
        }

        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("imageUrl", PropConfig.getInstance().getPropertyValue(Constants.STATIC_URL)
                .concat(thumbnailPath));
        return ResultUtil.success(resultMap);
    }

    /**
     * 上传视频图片文件
     * 
     * @param user
     * @param files
     * @param request
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/uploadFiles", method = RequestMethod.POST)
    public ResultBody filesUpload(@CurrentUser User user,
            @RequestParam(value = "attach", required = false) MultipartFile[] files,
            HttpServletRequest request) throws Exception {
        logger.info("===========进入上传视频图片，user=" + user.getMobilePhone());
        
        // 文件
        if (files == null) {
            throw new InvalidParamException("attach");
        }
        // 判断file数组不能为空并且长度大于0
        if (files.length <= 0) {
            return ResultUtil.error("不能上传空文件");
        }
        
        // 内容
        String content = request.getParameter("content");
        
        // 价格
        String price = request.getParameter("price");
        if (StringUtil.isNotBlank(price) && !NumberUtil.checkPrice(price)) {
            return ResultUtil.error("价格不正确");
        }
        
        // 标签
        String tag = request.getParameter("tag");
        
        // 描述
        String description = request.getParameter("description");
        
        String pathRoot = PropConfig.getInstance().getPropertyValue(Constants.UPLOAD_PATH);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        List<Map<String, String>> pathList = new ArrayList<Map<String, String>>();
        final String contentId = NumberUtil.getNewId();// RandomStringUtils.randomAlphanumeric(20);
        // 循环获取file数组中得文件
        for (int i = 0; i < files.length; i++) {
            MultipartFile file = files[i];
            if (!file.isEmpty()) {
                // 文件名
                final String fileName = file.getOriginalFilename();
                // 获取文件类型
                String extension = FilenameUtils.getExtension(fileName).toLowerCase();
                String filePath = null;
                int type = 0;
                if (Arrays.asList(FileUtil.CONTENT_TYPE_MAP.get("media").split(","))
                        .contains(extension)) {// 上传视频
                    filePath = "/files/video".concat(FileUtil.randomPathname(extension));
                    type = UploadType.VIDEOS.getValue();
                } else if (Arrays.asList(FileUtil.CONTENT_TYPE_MAP.get("image").split(","))
                        .contains(extension)) {// 上传图片
                    filePath = "/files/images".concat(FileUtil.randomPathname(extension));
                    type = UploadType.IMAGES.getValue();
                } else {
                    return ResultUtil.error("上传格式不符合");
                }
                
                try {
                    File uploadedFile = new File(pathRoot + filePath);
                    FileUtils.writeByteArrayToFile(uploadedFile, file.getBytes());
                } catch (Exception e) {
                    logger.error("上传图文视频文件异常", e);
                    return ResultUtil.error("上传失败");
                }
                
                // 上传视频图片
                UploadContent uploadContent = new UploadContent();
                uploadContent.setUserId(user.getUserId().intValue());
                uploadContent.setPubId(contentId);
                uploadContent.setType(type);
                uploadContent.setContent(content);
                uploadContent.setSrc(PropConfig.getInstance()
                        .getPropertyValue(Constants.STATIC_URL).concat(filePath));
                uploadContent.setTag(tag);
                uploadContent.setDescription(description);
                uploadContent.setAddTime(DateUtil.currentDate());
                if (StringUtil.isNotBlank(price)) {// 发布视频图片价格
                    uploadContent.setPrice(Double.valueOf(price));
                } else {
                    uploadContent.setPrice(0d);
                }
                
                Map<String, String> urlMap = new HashMap<String, String>();
                urlMap.put("url", uploadContent.getSrc());
                pathList.add(urlMap);
                String thumbnailUrl = "";
                try {
                    // 上传视频，生成截图
                    if (UploadType.VIDEOS.getValue() == type) {
                        String outImagePath =
                                filePath.substring(0, filePath.lastIndexOf(".")).concat(".png");
                        VideoPictureUtil.getVideoImage(Constants.FFMPEG_PATH,
                                pathRoot.concat(filePath), pathRoot.concat(outImagePath));
                        uploadContent.setPic(PropConfig.getInstance()
                                .getPropertyValue(Constants.STATIC_URL).concat(outImagePath));
                        uploadContent.setRemark(fileName + "视频截图");
                        thumbnailUrl = uploadContent.getPic();
                        urlMap.put("thumbnailUrl", thumbnailUrl);
                    }
                } catch (Exception e) {
                    logger.info("生成视频截图异常", e);
                }
                uploadContentBo.save(uploadContent);
            }
        }
        resultMap.put("pubId", contentId);
        resultMap.put("list", pathList);
        return ResultUtil.success(resultMap);
    }
    
    /**
     * 上传本地病历记录
     * 
     * @param user
     * @param files
     * @param request
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/uploadPatientRecord", method = RequestMethod.POST)
    public ResultBody uploadPatientRecord(@CurrentUser User user,
            @RequestParam(value = "attach", required = false) MultipartFile file,
            HttpServletRequest request) throws Exception {
        logger.info("===========进入上传本地病历记录，user=" + user.getMobilePhone());

        // 文件
        if (file == null) {
            throw new InvalidParamException("attach");
        }

        if (file.isEmpty()) {
            return ResultUtil.error("不能上传空文件");
        }

        // 文件名
        final String fileName = file.getOriginalFilename();
        // 获取文件类型
        String extension = FilenameUtils.getExtension(fileName).toLowerCase();
        String filePath = null;
        if (Arrays.asList(FileUtil.CONTENT_TYPE_MAP.get("file").split(",")).contains(extension)) {// 上传文件
            StringBuilder buff = new StringBuilder();
            buff.append("/files/backup/");
            buff.append(DateUtil.getShortDateStr());
            buff.append("/");
            buff.append(fileName);
            filePath = buff.toString();
        } else {
            return ResultUtil.error("上传格式不符合");
        }

        String pathRoot = PropConfig.getInstance().getPropertyValue(Constants.UPLOAD_PATH);
        Map<String, Object> resultMap = new HashMap<String, Object>();

        try {
            File uploadedFile = new File(pathRoot + filePath);
            FileUtils.writeByteArrayToFile(uploadedFile, file.getBytes());
        } catch (Exception e) {
            logger.error("上传本地病历记录异常", e);
            return ResultUtil.error("上传失败");
        }
        
        UploadPatientRecord uploadContent =
                uploadPatientRecordBo.getByUserId(user.getUserId().intValue());
        if (uploadContent != null) {
            uploadContent.setSrc(PropConfig.getInstance().getPropertyValue(Constants.STATIC_URL)
                    .concat(filePath));
            uploadContent.setLastUpdateDtime(DateUtil.currentDate());
            uploadContent.setRemark("[病历归档]" + fileName);
            uploadPatientRecordBo.updateByUserId(uploadContent);
        } else {
            uploadContent = new UploadPatientRecord();
            uploadContent.setUserId(user.getUserId().intValue());
            uploadContent.setSrc(PropConfig.getInstance().getPropertyValue(Constants.STATIC_URL)
                    .concat(filePath));
            uploadContent.setAddTime(DateUtil.currentDate());
            uploadContent.setRemark("[病历归档]" + fileName);
            uploadPatientRecordBo.save(uploadContent);
        }
        resultMap.put("url", uploadContent.getSrc());
        return ResultUtil.success(resultMap);
    }
    
    /**
     * 上传复诊病历文件
     * 
     * @param user
     * @param files
     * @param request
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/uploadPatient", method = RequestMethod.POST)
    public ResultBody patientUpload(@CurrentUser User user,
            @RequestParam(value = "attach", required = false) MultipartFile[] files,
            HttpServletRequest request) throws Exception {
        logger.info("===========进入上传病历文件，user=" + user.getMobilePhone());
        
        // 用户Id
        final Long userId = user.getUserId();
        
        // 上传病历名
        String title = request.getParameter("title");
        
        // 询问问题Id（第一次询问问题没有，继续询问必须带入）
        String questionId = request.getParameter("questionId");
        
        // 复诊病历Id
        String patientRecordId = request.getParameter("patientRecordId");
        if (StringUtil.isEmpty(patientRecordId)) {
            throw new InvalidParamException("patientRecordId");
        }
        
        // 上传病历，接收人[医生用户]
        String receiveUserId = request.getParameter("receiveUserId");
        if (StringUtil.isEmpty(receiveUserId)) {
            throw new InvalidParamException("receiveUserId");
        }
        
        // 上传病历问题内容
        String content = request.getParameter("content");
        if (StringUtil.isEmpty(content)) {
            throw new InvalidParamException("content");
        }
        
        String orderNo = request.getParameter("orderNo");
        
        if (files == null || files.length == 0) {// 只咨询问题，不上传病历
            String messageId = null;
            if (StringUtil.isNotEmpty(questionId)) {
                // 此问题已询问过，可继续询问
                AskQuestionAnswer askQuestionAnswer = askQuestionAnswerBo.get(Long.valueOf(questionId));
                if (askQuestionAnswer == null) {
                    return ResultUtil.error("问题不存在，不能继续询问");
                }
                messageId = askQuestionAnswer.getMessageId();
                askQuestionAnswer.setStatus("2");// 直接覆盖上一条为已结束
                askQuestionAnswer.setLastUpdateDtime(DateUtil.currentDate());
                askQuestionAnswerBo.update(askQuestionAnswer);
            } else {
                if (StringUtil.isNotEmpty(orderNo)) {
                    messageId = orderNo;
                } else {
                    messageId = NumberUtil.getNewId();
                }
            }
            AskQuestionAnswer newAskQuestionAnswer = new AskQuestionAnswer();
            newAskQuestionAnswer.setMessageId(messageId);
            newAskQuestionAnswer.setPatientRecordId(Integer.valueOf(patientRecordId));
            newAskQuestionAnswer.setUserId(userId.intValue());
            newAskQuestionAnswer.setToUser(Integer.valueOf(receiveUserId));
            newAskQuestionAnswer.setTitle(title);
            newAskQuestionAnswer.setQuestion(content);
            newAskQuestionAnswer.setStatus("1");
            newAskQuestionAnswer.setAddTime(DateUtil.currentDate());
            askQuestionAnswerBo.save(newAskQuestionAnswer);
            try {
                User u = userBo.get(userId);
                PushModel pushModel = new PushModel();
                pushModel.setTitle("复诊");
                pushModel.setContent(u.getRealName() + "给您发送了病历。");
                pushModel.addParam(Constants.TAG_ID, Constants.TAG_DOCTOR_ID);
                pushModel.addAlias(receiveUserId);
                jPushService.pushToAndroid(pushModel);
            } catch (Exception e) {
                // TODO: handle exception
                logger.info("【资讯问题】极光推送异常1", e);
            }
            return ResultUtil.success();
        } else {
            // 上传病历限制一个
            if (files.length > 1) {
                return ResultUtil.error("上传病历不能多个");
            }
            String pathRoot = PropConfig.getInstance().getPropertyValue(Constants.UPLOAD_PATH);
            // 判断file数组不能为空并且长度大于0
            if (files != null && files.length > 0) {
                String messageId = null;
                if (StringUtil.isNotEmpty(questionId)) {// 判断同一问题id
                    AskQuestionAnswer askQuestionAnswer =
                            askQuestionAnswerBo.get(Long.valueOf(questionId));
                    if (askQuestionAnswer == null) {
                        return ResultUtil.error("问题不存在，不能继续询问");
                    }
                    messageId = askQuestionAnswer.getMessageId();
                    askQuestionAnswer.setStatus("2");// 直接覆盖上一条为已结束
                    askQuestionAnswer.setLastUpdateDtime(DateUtil.currentDate());
                    askQuestionAnswerBo.update(askQuestionAnswer);
                } else {
                    if (StringUtil.isNotEmpty(orderNo)) {
                        messageId = orderNo;
                    } else {
                        messageId = NumberUtil.getNewId();
                    }
                }
                // 循环获取file数组中得文件
                for (int i = 0; i < files.length; i++) {
                    MultipartFile file = files[i];
                    if (!file.isEmpty()) {
                        // 文件名
                        final String fileName = file.getOriginalFilename();
                        // 获取文件类型
                        String extension = FilenameUtils.getExtension(fileName).toLowerCase();
                        String filePath = null;
                        if (Arrays.asList(FileUtil.CONTENT_TYPE_MAP.get("file").split(","))
                                .contains(extension)) {// 上传文件
                            // filePath = "/files/archive".concat(FileUtil.randomPathname(extension));
                            StringBuilder buff = new StringBuilder();
                            buff.append("/files/archive/");
                            buff.append(DateUtil.getShortDateStr());
                            buff.append("/");
                            buff.append(fileName);
                            filePath = buff.toString();
                        } else {
                            return ResultUtil.error("上传格式不符合");
                        }
                        
                        try {
                            File uploadedFile = new File(pathRoot + filePath);
                            FileUtils.writeByteArrayToFile(uploadedFile, file.getBytes());
                        } catch (Exception e) {
                            logger.error("上传复诊病历文件异常", e);
                            return ResultUtil.error("上传失败");
                        }
                        
                        // 保存咨询问题
                        AskQuestionAnswer askQuestionAnswer = new AskQuestionAnswer();
                        askQuestionAnswer.setMessageId(messageId);
                        askQuestionAnswer.setPatientRecordId(Integer.valueOf(patientRecordId));
                        askQuestionAnswer.setUserId(userId.intValue());
                        askQuestionAnswer.setToUser(Integer.valueOf(receiveUserId));
                        askQuestionAnswer.setTitle(title);
                        askQuestionAnswer.setQuestion(content);
                        askQuestionAnswer.setStatus("1");
                        askQuestionAnswer.setAddTime(DateUtil.currentDate());
                        
                        // 保存上传病历
                        UploadPatient uploadContent = new UploadPatient();
                        uploadContent.setMessageId(messageId);
                        uploadContent.setUserId(userId.intValue());
                        uploadContent.setContent(content);
                        uploadContent.setSrc(PropConfig.getInstance()
                                .getPropertyValue(Constants.STATIC_URL).concat(filePath));
                        uploadContent.setAddTime(DateUtil.currentDate());
                        StringBuilder buff = new StringBuilder();
                        buff.append("[复诊病历]");
                        buff.append(user.getRealName());
                        buff.append("-");
                        buff.append(fileName);
                        uploadContent.setRemark(buff.toString());
                        uploadPatientBo.saveUploadPatientAndQuestion(uploadContent, askQuestionAnswer);
                        try {
                            User u = userBo.get(userId);
                            PushModel pushModel = new PushModel();
                            pushModel.setTitle("复诊");
                            pushModel.setContent(u.getRealName() + "给您发送了病历。");
                            pushModel.addParam(Constants.TAG_ID, Constants.TAG_DOCTOR_ID);
                            pushModel.addAlias(receiveUserId);
                            jPushService.pushToAndroid(pushModel);
                            logger.info("======================"+pushModel.getContent()+"医生用户userId="+receiveUserId);
                        } catch (Exception e) {
                            // TODO: handle exception
                            logger.info("【资讯问题】极光推送异常2", e);
                        }
                    }
                }
                return ResultUtil.success();
            }
        }
        
        return ResultUtil.error("上传失败");
    }
}
