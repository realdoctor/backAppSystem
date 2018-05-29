package com.kanglian.healthcare.back.web;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
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
import com.kanglian.healthcare.authorization.annotation.Authorization;
import com.kanglian.healthcare.authorization.annotation.CurrentUser;
import com.kanglian.healthcare.back.constants.Constants;
import com.kanglian.healthcare.back.dal.pojo.User;
import com.kanglian.healthcare.back.dal.pojo.UserPic;
import com.kanglian.healthcare.back.service.UserPicBo;
import com.kanglian.healthcare.exception.InvalidOperationException;
import com.kanglian.healthcare.util.FileUtil;
import com.kanglian.healthcare.util.JsonUtil;
import com.kanglian.healthcare.util.PropConfig;
import net.coobird.thumbnailator.Thumbnails;

@Authorization
@Controller
@RequestMapping("/upload")
public class UploadController {

    private static final Logger logger = LoggerFactory.getLogger(UploadController.class);
    
    @Autowired
    private UserPicBo userPicBo;
    
    @ResponseBody
    @RequestMapping(value = "/uploadImg", method = RequestMethod.POST)
    private ResultBody uploadImg(@CurrentUser User user,
            @RequestParam(value = "attach", required = false) MultipartFile imageFile,
            HttpServletRequest request) throws Exception {
        logger.debug("===========进入上传图片，user=" + JsonUtil.beanToJson(user));

        if(imageFile == null) {
            throw new InvalidOperationException("attach");
        }
        
        if (imageFile.isEmpty()) {
            return ResultUtil.error("不能上传空文件");
        }

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
            return ResultUtil.error("上传图片格式不符合");
        }

        // 获得物理路径webapp所在路径
        String pathRoot = request.getSession().getServletContext().getRealPath("");
        pathRoot = PropConfig.getInstance().getPropertyValue(Constants.UPLOAD_IMAGE_PATH);
        String thumbnailPath = "";
        if (!imageFile.isEmpty()) {
            String originalPath = "/images".concat(FileUtil.randomPathname(extension));
            originalPath = originalPath.substring(0, originalPath.lastIndexOf(".")) + "_appTh." + extension;
            File uploadedFile = new File(pathRoot + originalPath);
            FileUtils.writeByteArrayToFile(uploadedFile, imageFile.getBytes());
            try {
                thumbnailPath = "/images".concat(FileUtil.randomPathname(extension));
                thumbnailPath = thumbnailPath.substring(0, thumbnailPath.lastIndexOf(".")) + "_appTh.png";
                Thumbnails.of(new File(pathRoot + originalPath)).size(200, 200)
                        .keepAspectRatio(false).toFile(new File(pathRoot + thumbnailPath));
                if (pathRoot != null 
                        && pathRoot.startsWith("/")) {
                    Runtime.getRuntime().exec("chmod 777 -R " + (pathRoot + originalPath));
                    Runtime.getRuntime().exec("chmod 777 -R " + (pathRoot + thumbnailPath));
                }
            } catch (Exception e) {
                // TODO: handle exception
            }
//            FileUtils.forceDelete(uploadedFile);
            logger.info("===============原始图路径" + originalPath);
            logger.info("======================生成缩略图路径" + thumbnailPath);
            UserPic userPic = userPicBo.get(user.getUserId());
            if (userPic != null) {
                // 更新头像，删除原来上传的
                try {
                    FileUtils.forceDelete(new File(pathRoot.concat(userPic.getPic())));
                    FileUtils.forceDelete(new File(pathRoot.concat(userPic.getThumbnailPic())));
                } catch (Exception e) {
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
        }

        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("imageUrl", PropConfig.getInstance().getPropertyValue(Constants.DOMAIN_URL).concat("/static").concat(thumbnailPath));
        return ResultUtil.success(resultMap);
    }
}
