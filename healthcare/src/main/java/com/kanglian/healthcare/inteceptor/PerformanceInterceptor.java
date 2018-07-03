package com.kanglian.healthcare.inteceptor;

import java.lang.reflect.Method;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import com.google.gson.Gson;

/**
 * 一个简单的用于性能监测的拦截器.
 * 
 * @author xl.liu
 */
@Aspect
@Component
public class PerformanceInterceptor {

    /** logger */
    private static final Logger logger       = LoggerFactory.getLogger(PerformanceInterceptor.class);

    /** 以毫秒表示的阈值 */
    private int                 threshold    = 1000;

    /** 性能监测开关 */
    private boolean             filerApplied = true;

    /**
     * 定义拦截规则：拦截com.realdoctor.**.web.controller包下面的所有类中，有@RequestMapping注解的方法。
     */
    @Pointcut("execution(* com.kanglian.healthcare.back..web..*(..)) || @annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public void controllerMethodPointcut() {}

    /**
     * 判断方法调用的时间是否超过阈值，如果是，则打印性能日志.
     * 
     * @param pjp
     */
    @Around("controllerMethodPointcut()") // 指定拦截器规则；也可以直接把“execution(* com.realdoctor.........)”写进这里
    public Object Interceptor(ProceedingJoinPoint pjp) throws Throwable {
        // 关闭监测模式
        if (!filerApplied) {
            return pjp.proceed();
        }

        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod(); // 获取被拦截的方法

        String name = method.getDeclaringClass().getName() + "." + method.getName();

        try {
            Set<Object> allParams = new LinkedHashSet<>(); // 保存所有请求参数，用于输出到日志中
            Object[] args = pjp.getArgs();
            for (Object arg : args) {
                if (arg instanceof Map<?, ?>) {
                    // 提取方法中的MAP参数，用于记录进日志中
                    @SuppressWarnings("unchecked")
                    Map<String, Object> map = (Map<String, Object>) arg;
                    allParams.add(map);
                } else if (arg instanceof HttpServletRequest) {
                    HttpServletRequest request = (HttpServletRequest) arg;
                    // 获取query string 或 posted form data参数
                    @SuppressWarnings("unchecked")
                    Map<String, String[]> paramMap = request.getParameterMap();
                    if (paramMap != null && paramMap.size() > 0) {
                        allParams.add(paramMap);
                    }
                } else if (arg instanceof HttpServletResponse) {
                    // do nothing...
                } else {
                    allParams.add(arg);
                }
            }
            Gson gson = new Gson();
            logger.debug("请求开始，方法：" + name + "\r\n=======>>>入参：" + gson.toJson(allParams));
        } catch (Exception ex) {
            // TODO: handle exception
            logger.info(ex.getMessage(), ex);
        }

        long startTime = System.currentTimeMillis();
        try {
            return pjp.proceed();
        } finally {
            long elapseTime = System.currentTimeMillis() - startTime;
            if (elapseTime > threshold) {
                logger.info("方法" + name + "的执行时间超过阈值，实际执行时间为" + elapseTime + "毫秒。");
            } else {
                if (logger.isDebugEnabled()) {
                    logger.debug("方法" + name + "的执行时间为" + elapseTime + "毫秒。");
                }
            }
        }

    }

    // ----- 容器方法 ------

    /**
     * @param threshold The threshold to set.
     */
    public void setThreshold(int threshold) {
        this.threshold = threshold;
    }
}
