package tech.valinaa.foundation.common.ddd.bo;


import tech.valinaa.foundation.common.enums.ResponseCode;

import java.io.Serial;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Response with batch page record to return,
 * usually use in page query
 * <p/>
 *
 * @author Valinaa
 * @Date 2024/11/25 19:03
 */
public class PageResponse<T> extends Response {
    
    @Serial
    private static final long serialVersionUID = 1L;
    
    private int totalCount = 0;
    
    private int pageSize = 1;
    
    private int pageIndex = 1;
    
    private Collection<T> data;
    
    public static <T> PageResponse<T> buildSuccess() {
        PageResponse<T> response = new PageResponse<>();
        response.setSuccess(true);
        response.setCode(ResponseCode.SUCCESS.intValue());
        response.setDescription(ResponseCode.SUCCESS.getDesc());
        return response;
    }
    
    public static <T> PageResponse<T> buildFailure(int errCode, String errMessage) {
        PageResponse<T> response = new PageResponse<>();
        response.setSuccess(false);
        response.setCode(errCode);
        response.setDescription(errMessage);
        return response;
    }
    
    public static <T> PageResponse<T> of(int pageSize, int pageIndex) {
        PageResponse<T> response = new PageResponse<>();
        response.setSuccess(true);
        response.setData(Collections.emptyList());
        response.setTotalCount(0);
        response.setPageSize(pageSize);
        response.setPageIndex(pageIndex);
        return response;
    }
    
    public static <T> PageResponse<T> of(Collection<T> data, int totalCount, int pageSize, int pageIndex) {
        PageResponse<T> response = new PageResponse<>();
        response.setSuccess(true);
        response.setData(data);
        response.setTotalCount(totalCount);
        response.setPageSize(pageSize);
        response.setPageIndex(pageIndex);
        return response;
    }
    
    public int getTotalCount() {
        return totalCount;
    }
    
    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
    
    public int getPageSize() {
        return Math.max(pageSize, 1);
    }
    
    public void setPageSize(int pageSize) {
        this.pageSize = Math.max(pageSize, 1);
    }
    
    public int getPageIndex() {
        return Math.max(pageIndex, 1);
    }
    
    public void setPageIndex(int pageIndex) {
        this.pageIndex = Math.max(pageIndex, 1);
    }
    
    public List<T> getData() {
        return null == data ? Collections.emptyList() : new ArrayList<>(data);
    }
    
    public void setData(Collection<T> data) {
        this.data = data;
    }
    
    public int getTotalPages() {
        return totalCount % pageSize == 0 ? totalCount
                / pageSize : (totalCount / pageSize) + 1;
    }
    
    public boolean isEmpty() {
        return data == null || data.isEmpty();
    }
    
    public boolean isNotEmpty() {
        return !isEmpty();
    }
    
}
