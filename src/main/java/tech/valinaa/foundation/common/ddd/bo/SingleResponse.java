package tech.valinaa.foundation.common.ddd.bo;


import tech.valinaa.foundation.common.enums.ResponseCode;
import tech.valinaa.foundation.common.exception.BaseResponseStatus;

/**
 * Response with single record to return
 *
 * @author Valinaa
 */
public class SingleResponse<T> extends Response {
    
    private T data;
    
    public static <T> SingleResponse<T> buildSuccess() {
        SingleResponse<T> response = new SingleResponse<>();
        response.setSuccess(true);
        response.setCode(ResponseCode.SUCCESS.intValue());
        response.setDescription(ResponseCode.SUCCESS.getDesc());
        return response;
    }
    
    public static <T> SingleResponse<T> buildFailure(int errCode, String errMessage) {
        SingleResponse<T> response = new SingleResponse<>();
        response.setSuccess(false);
        response.setCode(errCode);
        response.setDescription(errMessage);
        return response;
    }
    
    public static <T> SingleResponse<T> of(T data) {
        SingleResponse<T> response = new SingleResponse<>();
        response.setSuccess(true);
        response.setData(data);
        response.setCode(ResponseCode.SUCCESS.intValue());
        response.setDescription(ResponseCode.SUCCESS.getDesc());
        return response;
    }
    
    public static <T> SingleResponse<T> buildFailure(ResponseCode responseCode) {
        SingleResponse<T> response = new SingleResponse<>();
        response.setSuccess(false);
        response.setCode(responseCode.intValue());
        response.setDescription(responseCode.getDesc());
        return response;
    }
    
    public static <T> SingleResponse<T> buildFailure(BaseResponseStatus status) {
        SingleResponse<T> response = new SingleResponse<>();
        response.setSuccess(false);
        response.setCode(status.code());
        response.setDescription(status.description());
        return response;
    }
    
    public T getData() {
        return data;
    }
    
    public void setData(T data) {
        this.data = data;
    }
    
}
