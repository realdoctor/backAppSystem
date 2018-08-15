package com.kanglian.healthcare.exception;

import com.easyway.business.framework.common.enums.BaseResultCodeEnum;
import com.easyway.business.framework.common.exception.BaseRuntimeException;

/**
 * 业务异常
 * 
 * @author liuxl
 */
public class BizException extends BaseRuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = 2165592324950355554L;

    public BizException() {
        super(BaseResultCodeEnum.LOGIC_ERROR);
    }

    public BizException(Throwable cause) {
        super(BaseResultCodeEnum.LOGIC_ERROR, cause);
    }

    public BizException(String errorCode, String message, Throwable cause) {
        super(errorCode, message, cause);
    }

    public BizException(String errorCode, String message) {
        super(errorCode, message);
    }

    public BizException(String message, Throwable cause) {
        super(message, cause);
    }

    public BizException(String message) {
        super(message);
    }

}
