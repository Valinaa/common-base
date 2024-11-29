package tech.valinaa.foundation.common.enums;

/**
 * 状态码
 *
 * @author Valinaa
 * @Date 2024/11/25 19:03
 */
public enum ResponseCode {
    /**
     * Success
     */
    SUCCESS(20000, "Success"),
    
    /**
     * Retry
     */
    RETRY_AFTER(20010, "Retry"),
    
    /**
     * Degrade
     */
    SERVICE_DEGRADE(20101, "Degrade"),
    
    /**
     * Discard App
     */
    APP_DISCARD(20102, "APP Discard"),
    
    /**
     * Reject
     */
    REJECT_SERVICE(20103, "Reject Service"),
    
    /**
     * DB Error
     **/
    DB_FAILURE(30000, "DB Error"),
    
    /**
     * Redis Erro
     */
    REDIS_FAILURE(30010, "Redis Error"),
    
    /**
     * Null Request
     */
    NULL_REQUEST(40000, "Null Request"),
    
    /**
     * Invalid Request
     */
    BAD_REQUEST(40001, "Invalid Request"),
    
    /**
     * Invalid Response
     */
    BAD_RESPONSE(40002, "Invalid Response"),
    
    /**
     * Null Response
     */
    NULL_RESPONSE(40003, "Null Response"),
    
    /**
     * Duplicate Request
     */
    DUPLICATED_REQUEST(40010, "Duplicate Request"),
    
    /**
     * Processing Request
     */
    IN_PROCESSING_REQUEST(40011, "Processing Request"),
    
    /**
     * Serve Internal Error
     */
    SERVER_ERROR(99999, "Serve Internal Error"),
    
    /**
     * NA
     */
    NA(999999999, "NA");
    
    private final int code;
    private final String desc;
    
    ResponseCode(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }
    
    public static ResponseCode valueOf(int intValue, ResponseCode defaultValue) {
        var responseCodeArray = ResponseCode.values();
        for (var responseCode : responseCodeArray) {
            if (intValue == responseCode.intValue()) {
                return responseCode;
            }
        }
        return defaultValue;
    }
    
    public int intValue() {
        return code;
    }
    
    public String getDesc() {
        return desc;
    }
}
