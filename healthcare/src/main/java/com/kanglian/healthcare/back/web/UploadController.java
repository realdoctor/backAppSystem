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
import com.kanglian.healthcare.back.dal.pojo.UploadContent;
import com.kanglian.healthcare.back.dal.pojo.UploadPatient;
import com.kanglian.healthcare.back.dal.pojo.User;
import com.kanglian.healthcare.back.dal.pojo.UserPic;
import com.kanglian.healthcare.back.service.UploadContentBo;
import com.kanglian.healthcare.back.service.UploadPatientBo;
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
    private UserPicBo           userPicBo;
    @Autowired
    private UploadContentBo     uploadContentBo;
    @Autowired
    private UploadPatientBo     uploadPatientBo;

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
        
        if (files == null) {
            throw new InvalidParamException("files");
        }
        
        // 说说内容
        String content = request.getParameter("content");
        // 说说价格
        String price = request.getParameter("price");
        if (StringUtil.isNotBlank(price) && !NumberUtil.checkPrice(price)) {
            return ResultUtil.error("价格不正确");
        }
        
        String pathRoot = PropConfig.getInstance().getPropertyValue(Constants.UPLOAD_PATH);
        // 判断file数组不能为空并且长度大于0
        if (files != null && files.length > 0) {
            Map<String, Object> resultMap = new HashMap<String, Object>();
            List<Map<String, String>> pathList = new ArrayList<Map<String, String>>();
            final String contentId = NumberUtil.getNewId();//RandomStringUtils.randomAlphanumeric(20);
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
                    // 上传视频
                    if (Arrays.asList(FileUtil.CONTENT_TYPE_MAP.get("media").split(","))
                            .contains(extension)) {// 上传视频
                        filePath = "/files/video".concat(FileUtil.randomPathname(extension));
                        type = 1;
                    } else if (Arrays.asList(FileUtil.CONTENT_TYPE_MAP.get("image").split(","))
                            .contains(extension)) {// 上传图片
                        filePath = "/files/images".concat(FileUtil.randomPathname(extension));
                        type = 2;
                    } else if (Arrays.asList(FileUtil.CONTENT_TYPE_MAP.get("file").split(","))
                            .contains(extension)) {// 上传文件
                        // filePath = "/files/archive".concat(FileUtil.randomPathname(extension));
                        StringBuilder buff = new StringBuilder();
                        buff.append("/files/archive/");
                        buff.append(DateUtil.getShortDateStr());
                        buff.append("/");
                        buff.append(fileName);
                        buff.append(".").append(extension.toLowerCase());
                        filePath = buff.toString();
                        type = 3;
                    } else {
                        return ResultUtil.error("上传格式不符合");
                    }
                    File uploadedFile = new File(pathRoot + filePath);
                    FileUtils.writeByteArrayToFile(uploadedFile, file.getBytes());
                    
                    if (3 == type) {
                        // 上传病历，接收人
                        String receiveUserId = request.getParameter("receiveUserId");
                        UploadPatient uploadContent = new UploadPatient();
                        uploadContent.setPubId(contentId);
                        uploadContent.setUserId(user.getUserId().intValue());
                        if (StringUtil.isNotEmpty(receiveUserId)) {
                            uploadContent.setReceiveUserId(Integer.valueOf(receiveUserId));
                        }
                        uploadContent.setContent(content);
                        uploadContent.setSrc(PropConfig.getInstance()
                                .getPropertyValue(Constants.STATIC_URL).concat(filePath));
                        uploadContent.setAddTime(DateUtil.currentDate());
                        StringBuilder buff = new StringBuilder();
                        buff.append("[存档病历]");
                        buff.append(user.getRealName());
                        buff.append("-");
                        buff.append(fileName);
                        uploadContent.setRemark(buff.toString());
                        uploadPatientBo.save(uploadContent);
                        
                        Map<String, String> urlMap = new HashMap<String, String>();
                        urlMap.put("url", uploadContent.getSrc());
                        pathList.add(urlMap);
                    } else {
                        // 保存
                        UploadContent uploadContent = new UploadContent();
                        uploadContent.setUserId(user.getUserId().intValue());
                        uploadContent.setPubId(contentId);
                        uploadContent.setType(type);
                        uploadContent.setContent(content);
                        uploadContent.setSrc(PropConfig.getInstance()
                                .getPropertyValue(Constants.STATIC_URL).concat(filePath));
                        if (StringUtil.isNotEmpty(price)) {// 发布视频图片价格
                            uploadContent.setPrice(Double.valueOf(price));
                        } else {
                            uploadContent.setPrice(0d);
                        }
                        uploadContent.setAddTime(DateUtil.currentDate());
                        
                        Map<String, String> urlMap = new HashMap<String, String>();
                        urlMap.put("url", uploadContent.getSrc());
                        String thumbnailUrl = "";
                        try {
                            // 上传视频，生成截图
                            if (1 == type) {
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
                        pathList.add(urlMap);
                    }
                    
                }
            }
            resultMap.put("pubId", contentId);
            resultMap.put("list", pathList);
            return ResultUtil.success(resultMap);
        }
        return ResultUtil.error("上传失败");
    }
}
