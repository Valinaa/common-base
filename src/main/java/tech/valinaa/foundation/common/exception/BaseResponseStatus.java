package tech.valinaa.foundation.common.exception;

import java.io.Serializable;

/**
 * 状态码接口类
 *
 * @author kanggao
 * @date 2020/5/21
 */
public interface BaseResponseStatus extends Serializable {
    
    /**
     * 状态码
     *
     * @return 状态码编号
     */
    int code();
    
    /**
     * 描述
     *
     * @return 描述
     */
    String description();
    
}
