package com.kanglian.healthcare.back.web;

import java.io.File;
import java.io.FileInputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.easyway.business.framework.springmvc.result.ResultBody;
import com.easyway.business.framework.springmvc.result.ResultUtil;
import com.easyway.business.framework.util.DateUtil;
import com.kanglian.healthcare.back.constant.Constants;
import com.kanglian.healthcare.back.pojo.HospitalDoctorDTO;
import com.kanglian.healthcare.back.service.DataimportBo;
import com.kanglian.healthcare.util.BeanToMapUtil;
import com.kanglian.healthcare.util.FileUtil;
import com.kanglian.healthcare.util.PropConfig;
import com.kanglian.healthcare.util.excel.POIUtil;

/**
 * 导入数据
 * 
 * @author xl.liu
 */
@RestController
@RequestMapping(value = "/dataimport/")
public class DataimportController extends BaseController {

    private final static Map<String, String> mapping = new HashMap<String, String>();
    static {
        mapping.put("id", "id");
        mapping.put("省份", "province");
        mapping.put("城市", "city");
        mapping.put("医院名", "hospitalName");
        mapping.put("医院编号", "hospitalCode");
        mapping.put("医院等级", "hospitalLevel");
        mapping.put("经度", "lng");
        mapping.put("纬度", "lat");
        mapping.put("科室名", "deptName");
        mapping.put("科室编号", "deptCode");
        mapping.put("姓名", "doctorName");
        mapping.put("医生编号", "doctorCode");
        mapping.put("手机号", "phone");
        mapping.put("医生执照", "doctorLicense");
        mapping.put("擅长", "goodAt");
        mapping.put("医生简介", "doctorIntro");
        mapping.put("职称", "positional");
    }

    @Autowired
    private DataimportBo dataimportBo;

    @GetMapping("/doctorList")
    public ResultBody doctorImport() throws Exception {
        String filePath = PropConfig.getInstance().getPropertyValue(Constants.IMPORT_DOCTOR_FILE);
        if (!StringUtils.hasText(filePath)) {
            return ResultUtil.error("导入文件不存在");
        }
        File file = new File(filePath);
        if (!file.exists()) {
            return ResultUtil.error("没有文件导入");
        }
        
        // 文件名
        final String fileName = file.getName();
        // 获取文件类型
        String extension = FilenameUtils.getExtension(fileName).toLowerCase();
        if (!Arrays.asList(FileUtil.CONTENT_TYPE_MAP.get("excel").split(",")).contains(extension)) {
            return ResultUtil.error("不是excel文件");
        }
        
        FileInputStream fis = new FileInputStream(file);
        List<Map<String, Object>> mapList = POIUtil.getListByExcel(fis, fileName, mapping);
        if (mapList != null && mapList.size() > 0) {
            POIUtil.printLog2(mapList);
            List<HospitalDoctorDTO> dataList =
                    BeanToMapUtil.convertListMap2ListBean(mapList, HospitalDoctorDTO.class);
            try {
                dataimportBo.doctorImport(dataList);
                StringBuilder buff = new StringBuilder();
                buff.append(filePath.substring(0, filePath.lastIndexOf(".")));
                buff.append(DateUtil.getLongDateStr());
                buff.append(filePath.substring(filePath.lastIndexOf(".")));
                filePath = buff.toString();
                logger.info("========修改导入文件，防止重复导入-->>"+filePath);
                FileUtils.moveFile(file, new File(filePath));
            } catch (Exception e) {
                return ResultUtil.error("导入失败");
            }
            return ResultUtil.success(dataList);
        }
        return ResultUtil.error("无数据导入");
    }
}
