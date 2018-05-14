package com.kanglian.healthcare.back.listener;

import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.kanglian.healthcare.back.constants.Constants;
import com.kanglian.healthcare.back.service.CodetableBo;
import com.kanglian.healthcare.util.RedisCache;

@Component
public class InitInfoListener {

    /** logger */
    private static final Logger logger = LoggerFactory.getLogger(InitInfoListener.class);

    @Autowired
    private CodetableBo         codetableBo;
    @Autowired
    private RedisCache          redisCache;

    @PostConstruct
    public void init() {
        try {
            if (redisCache.getCacheObject(Constants.REDIS_CACHE_CODETABLE) == null) {
                logger.info("================初始化字典码表");
                codetableBo.initCacheData();
            }
            redisCache.setCacheObject(Constants.REDIS_CACHE_CODETABLE, "1");
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

}
