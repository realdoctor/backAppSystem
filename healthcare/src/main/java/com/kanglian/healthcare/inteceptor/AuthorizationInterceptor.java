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
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import com.easyway.business.framework.springmvc.result.ResultUtil;
import com.kanglian.healthcare.authorization.Constants;
import com.kanglian.healthcare.authorization.annotation.Authorization;
import com.kanglian.healthcare.authorization.token.impl.RedisTokenManager;
import com.kanglian.healthcare.authorization.util.JwtUtil;
import com.kanglian.healthcare.back.dal.pojo.User;
import com.kanglian.healthcare.util.JsonUtil;
import io.jsonwebtoken.Claims;

/**
 * 对请求进行身份验证
 * 
 * @author xl.liu
 */
public class AuthorizationInterceptor extends HandlerInterceptorAdapter {

    /** logger */
    private static final Logger logger = LoggerFactory.getLogger(AuthorizationInterceptor.class);
    
    @Autowired
    private RedisTokenManager redisTokenManager;
    
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
            Object handler) throws Exception {
        // 如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        // 从header中得到token
        String token = request.getHeader(Constants.AUTHORIZATION);
        logger.info("=================对请求进行身份验证，token="+token);
        if (token != null && token.length() > 0) {
            // 验证token
            Claims claims = JwtUtil.verifyToken(token);
            if (claims != null) {
                // 如果token验证成功，将token对应的用户id存在request中，便于之后注入
                User user = (User) JsonUtil.jsonToBean(claims.getSubject(), User.class);
                request.setAttribute(Constants.CURRENT_USER_ID, user.getUserId());
                if (user.isRefreshToken() || redisTokenManager.getKey(token) != null) {
                    logger.info("============================token验证通过，直接放行");
                    return true;
                }
            }
        }
        // 如果验证token失败，并且方法注明了Authorization，返回401错误
        if (method.getAnnotation(Authorization.class) != null // 查看方法上是否有注解
                || handlerMethod.getBeanType().getAnnotation(Authorization.class) != null) { // 查看方法所在的Controller是否有注解
            logger.info("============================token已过期或未携带签名，请重新登录");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setCharacterEncoding("UTF-8");  
            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            BufferedWriter writer =
                    new BufferedWriter(new OutputStreamWriter(response.getOutputStream()));
            writer.write(JsonUtil.beanToJson(ResultUtil.error("令牌失效，请重新登录")));
            writer.close();
            return false;
        }
        logger.info("============================不需要签名的请求，绿色放行");
        // 为了防止以恶意操作直接在REQUEST_CURRENT_KEY写入key，将其设为null
        request.setAttribute(Constants.CURRENT_USER_ID, null);
        return true;
    }
}
