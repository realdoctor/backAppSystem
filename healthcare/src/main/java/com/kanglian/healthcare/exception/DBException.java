package com.kanglian.healthcare.exception;

import com.easyway.business.framework.common.enums.BaseResultCodeEnum;
import com.easyway.business.framework.common.exception.BaseException;

/**
 * 数据库操作异常类
 * 
 * @author liuxl
 */
public class DBException extends BaseException {

    /**
     * 
     */
    private static final long serialVersionUID = 1686799594310356340L;

    public DBException() {
        super(BaseResultCodeEnum.DATA_ERROR);
    }

    public DBException(Throwable cause) {
        super(BaseResultCodeEnum.DATA_ERROR, cause);
    }

    public DBException(String message, Throwable cause) {
        super(message, cause);
    }

    public DBException(String message) {
        super(message);
    }

}
