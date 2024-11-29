package tech.valinaa.foundation.common.exception;


import tech.valinaa.foundation.common.enums.ResponseCode;

import java.io.Serial;

/**
 * Base Exception is the parent of all exceptions
 *
 * @author Valinaa
 */
public abstract class BaseException extends RuntimeException {
    
    @Serial
    private static final long serialVersionUID = 1L;
    
    private final int errCode;
    
    protected BaseException(String errMessage, int errCode) {
        super(errMessage);
        this.errCode = errCode;
    }
    
    protected BaseException(int errCode, String errMessage) {
        super(errMessage);
        this.errCode = errCode;
    }
    
    protected BaseException(String errMessage, Throwable e, int errCode) {
        super(errMessage, e);
        this.errCode = errCode;
    }
    
    protected BaseException(Throwable e, int errCode) {
        super(e);
        this.errCode = errCode;
    }
    
    protected BaseException(int errCode, String errMessage, Throwable e) {
        super(errMessage, e);
        this.errCode = errCode;
    }
    
    protected BaseException(ResponseCode responseCode, Throwable e) {
        super(responseCode.getDesc(), e);
        errCode = responseCode.intValue();
    }
    
    protected BaseException(ResponseCode responseCode) {
        super(responseCode.getDesc());
        errCode = responseCode.intValue();
    }
    
    protected BaseException(BaseResponseStatus responseCode) {
        super(responseCode.description());
        errCode = responseCode.code();
    }
    
    protected BaseException(BaseResponseStatus responseCode, Throwable e) {
        super(responseCode.description(), e);
        errCode = responseCode.code();
    }
    
    public int getErrCode() {
        return errCode;
    }
}
