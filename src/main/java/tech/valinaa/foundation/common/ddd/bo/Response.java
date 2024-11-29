package tech.valinaa.foundation.common.ddd.bo;


import tech.valinaa.foundation.common.exception.BaseResponseStatus;

import java.io.Serial;

/**
 * Response to caller
 *
 * @author fulan.zjf 2017年10月21日 下午8:53:17
 */
public class Response implements BaseResponseStatus {
    
    @Serial
    private static final long serialVersionUID = 1L;
    
    private boolean success;
    
    private int code;
    
    private String description;
    
    public boolean isSuccess() {
        return success;
    }
    
    public void setSuccess(boolean success) {
        this.success = success;
    }
    
    @Override
    public int code() {
        return code;
    }
    
    @Override
    public String description() {
        return description;
    }
    
    @Override
    public String toString() {
        return "Response [success=" + success + ", code=" + code + ", description=" + description + "]";
    }
    
    public int getCode() {
        return code;
    }
    
    protected void setCode(int code) {
        this.code = code;
    }
    
    public String getDescription() {
        return description;
    }
    
    protected void setDescription(String description) {
        this.description = description;
    }
}
