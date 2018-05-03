package com.kanglian.healthcare.exception;

import com.easyway.business.framework.common.enums.BaseResultCodeEnum;
import com.easyway.business.framework.common.enums.EnumBase;
import com.easyway.business.framework.common.exception.BaseRuntimeException;

/**
 * 数据库操作异常类
 * 
 * @author liuxl
 */
public class DBException extends BaseRuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = 1686799594310356340L;

    public DBException(){
        this(BaseResultCodeEnum.DATA_ERROR);
    }

    public DBException(Throwable cause){
        super(BaseResultCodeEnum.DATA_ERROR, cause);
    }

    public DBException(EnumBase baseEnum){
        super(baseEnum);
    }

    public DBException(EnumBase baseEnum, String message){
        super(baseEnum, message);
    }

    public DBException(EnumBase baseEnum, Throwable cause){
        super(baseEnum, cause);
    }

    public DBException(String errorCode, String message, Throwable cause){
        super(errorCode, message, cause);
    }

    public DBException(String errorCode, String message){
        super(errorCode, message);
    }

    public DBException(String message, Throwable cause){
        super(message, cause);
    }

    public DBException(String message){
        super(message);
    }

}
