package com.kanglian.healthcare.back.service;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.easyway.business.framework.util.DateUtil;
import com.kanglian.healthcare.back.dal.pojo.Hospital;
import com.kanglian.healthcare.back.dal.pojo.HospitalDept;
import com.kanglian.healthcare.back.dal.pojo.HospitalDoctor;
import com.kanglian.healthcare.back.dal.pojo.HospitalDoctorDTO;
import com.kanglian.healthcare.back.dal.pojo.User;

/**
 * 数据导入
 * 
 * @author xl.liu
 */
@Service
public class DataimportBo {

    private final static Logger logger = LoggerFactory.getLogger(DataimportBo.class);
    @Autowired
    private HospitalBo          hospitalBo;
    @Autowired
    private HospitalDeptBo      hospitalDeptBo;
    @Autowired
    private HospitalDoctorBo    hospitalDoctorBo;
    @Autowired
    private UserBo              userBo;

    /**
     * 导入医生数据
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public void doctorImport(final List<HospitalDoctorDTO> dataList) {
        logger.info("==============进入导入医生数据");
        if (dataList == null || dataList.size() == 0) {
            return;
        }

        for (HospitalDoctorDTO hdd : dataList) {
            String hid = hdd.getId();
            logger.info("==============正在导入id=【{}】数据，data={}",
                    new Object[] {hid, JSON.toJSONString(hdd)});
            final String phone = hdd.getPhone();
            if (StringUtils.hasText(phone)) {
                User u = userBo.queryUser(phone);
                if (u != null) {
                    logger.info("==============导入id=【{}】数据已存在，直接更新。mobilePhone=",
                            new Object[] {hid, phone});
                    u.setRealName(hdd.getDoctorName());
                    u.setLastUpdateDtime(DateUtil.currentDate());
                    userBo.update(u);

                    // 1、更新医院
                    Hospital hospital = hospitalBo.getByCode(hdd.getHospitalCode());
                    if (hospital != null) {
                        hospital.setHospitalName(hdd.getHospitalName());
                        hospital.setHospitalCode(hdd.getHospitalCode());
                        hospital.setHospitalLevel(hdd.getHospitalLevel());
                        hospital.setProvince(hdd.getProvince());
                        hospital.setCity(hdd.getCity());
                        hospital.setLng(hdd.getLng());
                        hospital.setLat(hdd.getLat());
                        hospital.setAppointmentNum(0);
                        hospitalBo.update(hospital);
                        logger.info("==============更新医院数据，data=" + JSON.toJSONString(hospital));
                    }

                    // 2、更新科室
                    HospitalDept dept = new HospitalDept();
                    dept.setHospitalId(hospital.getHospitalId());
                    dept.setDeptCode(hdd.getDeptCode());
                    HospitalDept hospitalDept = hospitalDeptBo.getHospitalDept(dept);
                    if (hospitalDept != null) {
                        hospitalDept.setDeptName(hdd.getDeptName());
                        hospitalDept.setDeptCode(hdd.getDeptCode());
                        hospitalDept.setHospitalId(hospital.getHospitalId());
                        hospitalDept.setHospitalName(hospital.getHospitalName());
                        hospitalDeptBo.update(hospitalDept);
                        logger.info("==============写入科室数据，data=" + JSON.toJSONString(hospitalDept));
                    }

                    // 3、更新医生
                    HospitalDoctor doctor = new HospitalDoctor();
                    doctor.setHospitalId(hospitalDept.getHospitalId());
                    doctor.setDeptId(hospitalDept.getDeptId());
                    doctor.setDoctorCode(hdd.getDoctorCode());
                    HospitalDoctor hospitalDoctor = hospitalDoctorBo.getHospitalDoctor(doctor);
                    if (hospitalDoctor != null) {
                        hospitalDoctor.setHospitalId(hospital.getHospitalId());
                        hospitalDoctor.setDeptId(hospitalDept.getDeptId());
                        hospitalDoctor.setDoctorLicense(hdd.getDoctorLicense());
                        hospitalDoctor.setDoctorName(hdd.getDoctorName());
                        hospitalDoctor.setDoctorCode(hdd.getDoctorCode());
                        hospitalDoctor.setField(hdd.getGoodAt());
                        hospitalDoctor.setDoctorIntro(hdd.getDoctorIntro());
                        hospitalDoctor.setPositional(hdd.getPositional());
                        hospitalDoctorBo.update(hospitalDoctor);
                        logger.info(
                                "==============更新医生数据，data=" + JSON.toJSONString(hospitalDoctor));
                    }
                } else {
                    // 1、保存医院
                    Hospital hospital = hospitalBo.getByCode(hdd.getHospitalCode());
                    if (hospital == null) {
                        hospital = new Hospital();
                        hospital.setHospitalName(hdd.getHospitalName());
                        hospital.setHospitalCode(hdd.getHospitalCode());
                        hospital.setHospitalLevel(hdd.getHospitalLevel());
                        hospital.setProvince(hdd.getProvince());
                        hospital.setCity(hdd.getCity());
                        hospital.setLng(hdd.getLng());
                        hospital.setLat(hdd.getLat());
                        hospital.setAppointmentNum(0);
                        hospitalBo.save(hospital);
                        logger.info("==============写入医院数据，data=" + JSON.toJSONString(hospital));
                    }

                    // 2、保存科室
                    HospitalDept dept = new HospitalDept();
                    dept.setHospitalId(hospital.getHospitalId());
                    dept.setDeptCode(hdd.getDeptCode());
                    HospitalDept hospitalDept = hospitalDeptBo.getHospitalDept(dept);
                    if (hospitalDept == null) {
                        hospitalDept = new HospitalDept();
                        hospitalDept.setDeptName(hdd.getDeptName());
                        hospitalDept.setDeptCode(hdd.getDeptCode());
                        hospitalDept.setHospitalId(hospital.getHospitalId());
                        hospitalDept.setHospitalName(hospital.getHospitalName());
                        hospitalDeptBo.save(hospitalDept);
                        logger.info("==============写入科室数据，data=" + JSON.toJSONString(hospitalDept));
                    }

                    // 3、保存医生
                    HospitalDoctor doctor = new HospitalDoctor();
                    doctor.setHospitalId(hospitalDept.getHospitalId());
                    doctor.setDeptId(hospitalDept.getDeptId());
                    doctor.setDoctorCode(hdd.getDoctorCode());
                    HospitalDoctor hospitalDoctor = hospitalDoctorBo.getHospitalDoctor(doctor);
                    if (hospitalDoctor == null) {
                        hospitalDoctor = new HospitalDoctor();
                        hospitalDoctor.setHospitalId(hospital.getHospitalId());
                        hospitalDoctor.setDeptId(hospitalDept.getDeptId());
                        hospitalDoctor.setDoctorLicense(hdd.getDoctorLicense());
                        hospitalDoctor.setDoctorName(hdd.getDoctorName());
                        hospitalDoctor.setDoctorCode(hdd.getDoctorCode());
                        hospitalDoctor.setField(hdd.getGoodAt());
                        hospitalDoctor.setDoctorIntro(hdd.getDoctorIntro());
                        hospitalDoctor.setPositional(hdd.getPositional());
                        hospitalDoctorBo.save(hospitalDoctor);
                        logger.info(
                                "==============写入医生数据，data=" + JSON.toJSONString(hospitalDoctor));
                    }

                    // 4、创建用户
                    User user = new User();
                    user.setMobilePhone(phone);
                    user.setPwd("123456");
                    user.setRoleId(1);
                    user.setRealName(hdd.getDoctorName());
                    userBo.regist(user);
                    logger.info("==============写入用户数据，data=" + JSON.toJSONString(user));
                }
            } else {
                logger.info("==============导入id=【{}】数据，手机号为空", hid);
            }
        }
    }
}
