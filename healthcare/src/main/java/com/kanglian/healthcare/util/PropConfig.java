package com.kanglian.healthcare.util;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

/**
 * 加载属性配置文件
 * 
 * @author xl.liu
 */
public class PropConfig {

    /** logger */
    private final static Logger       logger         = LoggerFactory.getLogger(PropConfig.class);
    private final Map<String, String> betchConfigMap = new LinkedHashMap<String, String>();
    private static final String       CFG_FILE       = "config.properties";

    private static class SingletonHolder {

        private static final PropConfig INSTANCE = new PropConfig();
    }

    public static PropConfig getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public PropConfig(){
        init(CFG_FILE);
    }

    /**
     * 从配置文件中加载配置
     * <p>
     * 一般就是config.properties文件
     * </p>
     */
    private void init(String propFile) {
        try {
            loadConfig(propFile);
        } catch (Exception ex) {
            throw new RuntimeException(MessageFormat.format("can not find system's config [{0}] details [{1}]",
                                                            propFile, ex.getMessage()));
        }
    }

    /**
     * 从配置文件中加载配置
     * 
     * @param currentFile NULL default [config.properties]
     */
    public void loadConfig(String currentFile) {
        if (currentFile == null || currentFile.length() == 0) {
            currentFile = CFG_FILE;
        }

        try {
            logger.debug(MessageFormat.format("============>>>>开始加载{0}文件", currentFile));
            File file = new File(currentFile); // 这里表示从jar同级目录加载
            if (!file.exists()) {
                logger.debug("============>>>>如果同级目录没有，就去config下面找");
                file = new File("config/" + currentFile);
                logger.debug("-----+++++++-----" + file.getAbsolutePath());
                if (file.exists()) {
                    logger.debug(MessageFormat.format("============>>>>从config目录加载了{0}文件", currentFile));
                }
            }
            Resource resource = new FileSystemResource(file);
            if (!resource.exists()) {
                logger.debug("============>>>>从config目录下还是找不到，那就直接用classpath下的");
                resource = new ClassPathResource("/config/" + currentFile);
            }

            Properties systemProperties = PropertiesLoaderUtils.loadProperties(resource);
            if (systemProperties != null) {
                logger.debug(MessageFormat.format("============>>>>配置文件{0}加载成功，message={1}",
                                                  new Object[] { currentFile, systemProperties.toString() }));
                systemProperties.list(System.out);
            }

            Set<Map.Entry<Object, Object>> entrySet = systemProperties.entrySet();
            for (Map.Entry<Object, Object> entry : entrySet) {
                String key = entry.getKey().toString();
                String value = entry.getValue().toString();

                // 从系统参数中取值，如果系统参数中有值，系统参数的值生效
                String sysValue = System.getProperty(key);
                if (sysValue != null && sysValue.length() > 0) {
                    System.out.println("在Java -D参数中发现key[" + key + "]，将使用系统参数设置的值[" + sysValue + "] 替换原有的值 [" + value
                                       + "]");
                    value = sysValue;
                }
                this.setProperty(key, value);
            }
        } catch (IOException ex) {
            throw new RuntimeException(MessageFormat.format("can not find system's config [{0}] details [{1}]",
                                                            currentFile, ex.getMessage()));
        }
    }

    /**
     * 获取属性
     * 
     * @param key
     * @return
     */
    public String getPropertyValue(String key) {
        return StringUtils.trim(this.betchConfigMap.get(key));
    }

    public void setProperty(String key, String value) {
        this.betchConfigMap.put(key, value);
    }

    public Map<String, String> getConfig() {
        // 对map进行clone，保证map的安全性
        Map<String, String> map = new HashMap<String, String>();
        map.putAll(this.betchConfigMap);
        return map;
    }

    public void setConfig(Map<String, String> map) {
        this.betchConfigMap.putAll(map);
    }

}
