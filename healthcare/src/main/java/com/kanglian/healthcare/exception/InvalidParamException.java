package com.kanglian.healthcare.exception;

import com.easyway.business.framework.common.enums.BaseResultCodeEnum;
import com.easyway.business.framework.common.exception.BaseException;

/**
 * 输入参数校验异常
 * 
 * @author liuxl
 */
public class InvalidParamException extends BaseException {

    /**
     * 
     */
    private static final long serialVersionUID = 2165592324950355554L;

    public InvalidParamException() {
        super(BaseResultCodeEnum.ILLEGAL_ARGUMENT);
    }

    public InvalidParamException(Throwable cause) {
        super(BaseResultCodeEnum.ILLEGAL_ARGUMENT, cause);
    }

    public InvalidParamException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * 构造器
     * 
     * @param paramName 错误参数名称
     */
    public InvalidParamException(String paramName) {
        super(BaseResultCodeEnum.ILLEGAL_ARGUMENT, "Invalid param: " + paramName);
    }

}
