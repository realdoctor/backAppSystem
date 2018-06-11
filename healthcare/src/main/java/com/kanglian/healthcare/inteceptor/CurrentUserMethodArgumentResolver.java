package com.kanglian.healthcare.inteceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import com.kanglian.healthcare.authorization.AuthConfig;
import com.kanglian.healthcare.authorization.annotation.CurrentUser;
import com.kanglian.healthcare.authorization.util.TokenUtil;
import com.kanglian.healthcare.back.dal.pojo.User;
import com.kanglian.healthcare.back.service.UserBo;
import com.kanglian.healthcare.exception.InvalidOperationException;
import com.kanglian.healthcare.util.JsonUtil;

/**
 * 增加方法注入，将含有CurrentUser注解的方法参数注入当前登录用户
 * 
 * @author xl.liu
 */
public class CurrentUserMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Autowired
    private UserBo userBo;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        // 如果参数类型是User并且有CurrentUser注解则支持
        if (parameter.getParameterType().isAssignableFrom(User.class)
                && parameter.hasParameterAnnotation(CurrentUser.class)) {
            return true;
        }
        return false;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        String accessToken = webRequest.getHeader(AuthConfig.AUTHORIZATION);
        String userJsonString = TokenUtil.parseToken(accessToken);
        if (userJsonString != null) {
            // 从token取出用户
            User user = (User) JsonUtil.jsonToBean(userJsonString, User.class);
            return user;
        } else {
            // 取出鉴权时存入的登录用户Id
            Long currentUserId = (Long) webRequest.getAttribute(AuthConfig.CURRENT_USER_ID,
                    RequestAttributes.SCOPE_REQUEST);
            if (currentUserId != null) {
                // 从数据库中查询并返回
                User user = userBo.get(currentUserId);
                if (user != null) {
                    return user;
                }
                // 有key但是得不到用户，抛出异常
                throw new MissingServletRequestPartException(AuthConfig.CURRENT_USER_ID);
            }
        }
        // 没有key就直接返回null
        throw new InvalidOperationException();
    }
}
