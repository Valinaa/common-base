package tech.valinaa.foundation.common.utils;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;

import java.util.List;

/**
 * JSON工具类
 *
 * @author Valinaa
 * @Date 2024/11/25 16:17
 */
public class JsonUtils {
    private JsonUtils() {
    }
    
    /**
     * 将对象转换为JSON字符串
     *
     * @param obj 要转换为JSON的对象
     * @return JSON字符串
     */
    public static String toJsonString(Object obj) {
        return JSON.toJSONString(obj);
    }
    
    /**
     * 将对象转换为byte[]
     *
     * @param obj 要转换为JSON的对象
     * @return byte[]
     */
    public static byte[] toJsonBytes(Object obj) {
        return JSON.toJSONBytes(obj);
    }
    
    /**
     * 将JSON字符串转换为指定类型的对象
     *
     * @param jsonString JSON字符串
     * @param clazz      目标对象类型的Class
     * @param <T>        目标对象的类型
     * @return 转换后的目标对象
     */
    public static <T> T parseObject(String jsonString, Class<T> clazz) {
        return JSON.parseObject(jsonString, clazz);
    }
    
    /**
     * 将JSON字符串转换为指定类型的list
     *
     * @param jsonString JSON字符串
     * @param clazz      目标对象类型的Class
     * @param <T>        目标对象的类型
     * @return 转换后的目标对象
     */
    public static <T> List<T> parseArray(String jsonString, Class<T> clazz) {
        return JSON.parseArray(jsonString, clazz);
    }
    
    /**
     * 将JSON字符串转换为JSONObject对象
     *
     * @param jsonString JSON字符串
     * @return 转换后的JSONObject对象
     */
    public static JSONObject parseJsonObject(String jsonString) {
        return JSON.parseObject(jsonString);
    }
    
    /**
     * 将JSON字符串转换为JSONArray对象
     *
     * @param jsonString JSON字符串
     * @return 转换后的JSONArray对象
     */
    public static JSONArray parseJsonArray(String jsonString) {
        return JSON.parseArray(jsonString);
    }
    
    /**
     * 将Java对象转换为JSONObject对象
     *
     * @param obj Java对象
     * @return 转换后的JSONObject对象
     */
    public static JSONObject toJsonObject(Object obj) {
        return (JSONObject) JSON.toJSON(obj);
    }
    
    /**
     * 将Java对象转换为JSONArray对象
     *
     * @param obj Java对象
     * @return 转换后的JSONArray对象
     */
    public static JSONArray toJsonArray(Object obj) {
        return (JSONArray) JSON.toJSON(obj);
    }
}
