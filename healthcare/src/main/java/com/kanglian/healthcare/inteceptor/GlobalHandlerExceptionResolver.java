package com.kanglian.healthcare.inteceptor;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.easyway.business.framework.common.enums.EnumBase;
import com.easyway.business.framework.common.exception.BaseRuntimeException;
import com.easyway.business.framework.springmvc.result.ResultBody;
import com.easyway.business.framework.springmvc.result.ResultUtil;

/**
 * 统一错误码异常处理
 * 
 * @author xl.liu
 */
@RestControllerAdvice
public class GlobalHandlerExceptionResolver {

    private final static Logger logger = LoggerFactory.getLogger(GlobalHandlerExceptionResolver.class);

    /**
     * 全局异常捕捉处理
     * 
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public ResultBody errorHandler(Exception ex) {
        logger.error(ex.getMessage(), ex);
        return ResultUtil.error(ex.getMessage());
    }

    /**
     * 拦截捕捉自定义异常
     * 
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = BaseRuntimeException.class)
    public ResultBody errorHandlerOverJson(HttpServletRequest request, BaseRuntimeException exception) {
        EnumBase errorInfo = exception.getErrorEnum();
        if (errorInfo != null) {
            logger.error(errorInfo.message(), exception);
            return ResultUtil.error(errorInfo.message());
        }
        logger.error(exception.getMessage(), exception);
        return ResultUtil.error(exception.getCode(), exception.getMessage());
    }
}
