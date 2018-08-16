package com.kanglian.healthcare.inteceptor;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.lang.reflect.Method;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.easyway.business.framework.springmvc.result.ResultUtil;
import com.easyway.business.framework.util.StringUtil;
import com.kanglian.healthcare.authorization.AuthConfig;
import com.kanglian.healthcare.authorization.annotation.Authorization;
import com.kanglian.healthcare.authorization.util.TokenUtil;
import com.kanglian.healthcare.back.dal.pojo.User;
import com.kanglian.healthcare.back.listener.InitInfoListener;
import com.kanglian.healthcare.back.service.UserBo;
import com.kanglian.healthcare.util.JsonUtil;

/**
 * 对请求进行身份验证
 * 
 * @author xl.liu
 */
public class AuthorizationInterceptor extends HandlerInterceptorAdapter {

    /** logger */
    private static final Logger logger = LoggerFactory.getLogger(AuthorizationInterceptor.class);

    @Autowired
    private UserBo              userBo;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
            Object handler) throws Exception {
        // 如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        
        String urlStr = request.getRequestURI().trim();
        logger.info("=========>>>拦截请求Uri：{}", urlStr);
        if (InitInfoListener.noFilter(urlStr)) {
            logger.info("=========>>>不需要拦截，直接放行");
            return true;
        }
        
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        // 从header中得到token
        String token = request.getHeader(AuthConfig.AUTHORIZATION);
        String name = method.getDeclaringClass().getName() + "." + method.getName();
        logger.info("=========>>>进入请求方法：{}", name);
        logger.info("=================对请求进行身份验证，token=" + token);
        if (StringUtil.isNotEmpty(token)) {
            // 验证token
            String userJsonString = TokenUtil.parseToken(token);
            if (userJsonString != null) {
                // 如果token验证成功，将token对应的用户id存在request中，便于之后注入
                User user = (User) JsonUtil.jsonToBean(userJsonString, User.class);
                request.setAttribute(AuthConfig.CURRENT_USER_ID, user.getUserId());
                logger.info("=================身份已验证，user=" + JSON.toJSONString(user, new SerializerFeature[] {SerializerFeature.WriteDateUseDateFormat}));
                String sessionId = userBo.getKey(user.getUserId(), token);
                logger.info("=================SessionId=" + sessionId);
                if (StringUtil.isNotEmpty(sessionId)) {
                    logger.info("============================token验证通过，直接放行");
                    return true;
                } else {
                    logger.info("============================session已过期");
                }
            } else {
                logger.info("============================token已过期");
            }
        }
        
        // 如果验证token失败，并且方法注明了Authorization，返回401错误
        if (method.getAnnotation(Authorization.class) != null // 查看方法上是否有注解
                || handlerMethod.getBeanType().getAnnotation(Authorization.class) != null) { // 查看方法所在的Controller是否有注解
            logger.info("============================返回客户端，请重新登录");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setCharacterEncoding("UTF-8");
            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            BufferedWriter writer =
                    new BufferedWriter(new OutputStreamWriter(response.getOutputStream()));
            writer.write(JsonUtil.beanToJson(ResultUtil.error("-2", "令牌失效，请重新登录")));
            writer.close();
            return false;
        }
        
        logger.info("============================不需要签名的请求，绿色放行");
        // 为了防止以恶意操作直接在REQUEST_CURRENT_KEY写入key，将其设为null
        request.setAttribute(AuthConfig.CURRENT_USER_ID, null);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        String methodName = method.getName();
        if ("updatePwd".equals(methodName) || "logout".equals(methodName)
                || "refreshToken".equals(methodName)) {
            Long userId = (Long) request.getAttribute(AuthConfig.CURRENT_USER_ID);
            String token = request.getHeader(AuthConfig.AUTHORIZATION);
            userBo.delRelationshipByToken(userId, token);
        }
    }
    
}
