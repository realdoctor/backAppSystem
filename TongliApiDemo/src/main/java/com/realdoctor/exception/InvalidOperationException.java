package com.realdoctor.exception;

import com.easyway.business.framework.common.enums.BaseResultCodeEnum;
import com.easyway.business.framework.common.exception.BaseRuntimeException;

/**
 * 不正确的操作异常
 * 
 * @author xl.liu
 */
public class InvalidOperationException extends BaseRuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = 5413189051917512242L;

    public InvalidOperationException(){
        super(BaseResultCodeEnum.ILLEGAL_OPERATION);
    }

    public InvalidOperationException(Throwable cause){
        super(BaseResultCodeEnum.ILLEGAL_OPERATION, cause);
    }

    public InvalidOperationException(String operation){
        super(BaseResultCodeEnum.ILLEGAL_OPERATION, "Invalid operation: " + operation);
    }
}
