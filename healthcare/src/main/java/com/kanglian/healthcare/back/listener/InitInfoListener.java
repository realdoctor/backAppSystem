package com.kanglian.healthcare.back.listener;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.kanglian.healthcare.back.constants.Constants;
import com.kanglian.healthcare.back.service.CodetableBo;
import com.kanglian.healthcare.util.JsonUtil;
import com.kanglian.healthcare.util.RedisCacheManager;

@Component
public class InitInfoListener {

    /** logger */
    private static final Logger logger = LoggerFactory.getLogger(InitInfoListener.class);

    @Autowired
    private CodetableBo         codetableBo;
    @Autowired
    private RedisCacheManager   redisCacheManager;

    @PostConstruct
    public void init() {
        try {
            loadNoFilterList();
            if (redisCacheManager.getCacheObject(Constants.MARK_CODETABLE_KEY) == null) {
                logger.info("================初始化字典码表");
                codetableBo.initCacheData();
            }
            redisCacheManager.setCacheObject(Constants.MARK_CODETABLE_KEY, "1");
            logger.debug("================"+JsonUtil.object2Json(notNeedtoFilterList));
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    private static List<String> notNeedtoFilterList  = new ArrayList<String>();
    private String              filterPolicyFilePath = "/filterPolicy.xml";

    public static boolean noFilter(String url) {
        return notNeedtoFilterList.contains(url);
    }

    private void loadNoFilterList() {
        SAXReader reader = new SAXReader();
        try {
            InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(filterPolicyFilePath);
            Document doc = reader.read(inputStream);
            Element filters = doc.getRootElement();
            List<Element> filterList = filters.element("actionWirteNames").elements("filter");
            for (Element filter : filterList) {
                notNeedtoFilterList.add(filter.getTextTrim());
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
