package tech.valinaa.foundation.common.ddd.bo;


import tech.valinaa.foundation.common.enums.ResponseCode;
import tech.valinaa.foundation.common.exception.BaseResponseStatus;

import java.io.Serial;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Response with batch record to return,
 * usually use in conditional query
 *
 * @author Valinaa
 */
public class MultiResponse<T> extends Response {
    
    @Serial
    private static final long serialVersionUID = 1L;
    
    private Collection<T> data;
    
    public static <T> MultiResponse<T> buildSuccess() {
        MultiResponse<T> response = new MultiResponse<>();
        response.setSuccess(true);
        response.setCode(ResponseCode.SUCCESS.intValue());
        response.setDescription(ResponseCode.SUCCESS.getDesc());
        return response;
    }
    
    public static <T> MultiResponse<T> buildFailure(int errCode, String errMessage) {
        MultiResponse<T> response = new MultiResponse<>();
        response.setSuccess(false);
        response.setCode(errCode);
        response.setDescription(errMessage);
        return response;
    }
    
    public static <T> MultiResponse<T> of(Collection<T> data) {
        MultiResponse<T> response = new MultiResponse<>();
        response.setSuccess(true);
        response.setData(data);
        response.setCode(ResponseCode.SUCCESS.intValue());
        response.setDescription(ResponseCode.SUCCESS.getDesc());
        return response;
    }
    
    public static <T> MultiResponse<T> buildFailure(ResponseCode responseCode) {
        MultiResponse<T> response = new MultiResponse<>();
        response.setSuccess(false);
        response.setCode(responseCode.intValue());
        response.setDescription(responseCode.getDesc());
        return response;
    }
    
    public static <T> MultiResponse<T> buildFailure(BaseResponseStatus status) {
        MultiResponse<T> response = new MultiResponse<>();
        response.setSuccess(false);
        response.setCode(status.code());
        response.setDescription(status.description());
        return response;
    }
    
    public List<T> getData() {
        return null == data ? Collections.emptyList() : new ArrayList<>(data);
    }
    
    public void setData(Collection<T> data) {
        this.data = data;
    }
    
    public boolean isEmpty() {
        return data == null || data.isEmpty();
    }
    
    public boolean isNotEmpty() {
        return !isEmpty();
    }
    
}
