package com.kanglian.healthcare.quartz.task;

import java.io.File;
import java.io.FileInputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import com.easyway.business.framework.util.DateUtil;
import com.kanglian.healthcare.back.constants.Constants;
import com.kanglian.healthcare.back.dal.pojo.HospitalDoctorDTO;
import com.kanglian.healthcare.back.service.DataimportBo;
import com.kanglian.healthcare.quartz.common.AbstractTask;
import com.kanglian.healthcare.util.BeanToMapUtil;
import com.kanglian.healthcare.util.FileUtil;
import com.kanglian.healthcare.util.PropConfig;
import com.kanglian.healthcare.util.excel.POIUtil;

/**
 * 数据导入
 * 
 * @author xl.liu
 */
public class DataimportTask extends AbstractTask {

    /** logger */
    private static final Logger              logger  = LoggerFactory.getLogger(DataimportTask.class);

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

    public DataimportTask() {
        this.taskName = "数据导入";
    }

    @Override
    public boolean canProcess() {
        return true;
    }

    @Override
    public void process() {
        try {
            String filePath =
                    PropConfig.getInstance().getPropertyValue(Constants.IMPORT_DOCTOR_FILE);
            if (!StringUtils.hasText(filePath)) {
                logger.info("===================导入文件不存在");
                return;
            }
            File file = new File(filePath);
            if (!file.exists()) {
                logger.info("===================没有文件导入");
                return;
            }

            // 文件名
            final String fileName = file.getName();
            // 获取文件类型
            String extension = FilenameUtils.getExtension(fileName).toLowerCase();
            if (!Arrays.asList(FileUtil.CONTENT_TYPE_MAP.get("excel").split(","))
                    .contains(extension)) {
                logger.info("===================不是excel文件，fileName=" + fileName);
                return;
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
                    FileUtils.moveFile(file, new File(filePath));
                    logger.info("===================导入成功");
                    logger.info("========修改文件，防止重复导入-->>" + filePath);
                } catch (Exception e) {
                    logger.error("===================导入失败", e);
                }
            } else {
                logger.info("===================无数据导入，fileName=" + fileName);
            }
        } catch (Exception e) {
            logger.error("数据导入异常", e);
        }
    }

}
