package tech.valinaa.foundation.common.utils;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * BigDecimal工具类
 *
 * @author Valinaa
 * @Date 2024/11/25 19:22
 */
@SuppressWarnings("unused")
public class BigDecimalUtils {
    
    private BigDecimalUtils() {
    }
    
    /**
     * 清除BigDecimal中小数部分的0尾巴
     *
     * @param value         value need clear zero tail
     * @param minTailLength minimum tail length keep, must great than zero;
     * @return BigDecimal cleared zero tail
     */
    public static BigDecimal clearZeroTail(BigDecimal value, int minTailLength) {
        if (value == null) {
            return null;
        }
        
        if (value.scale() == minTailLength) {
            return value;
        }
        
        if (value.scale() < minTailLength) {
            return new BigDecimal(value.unscaledValue().multiply(BigInteger.TEN.pow(minTailLength - value.scale())),
                    minTailLength);
        }
        
        var integerValue = value.unscaledValue();
        int scale = value.scale();
        for (; scale > minTailLength && scale > 0; scale--) {
            var integers = integerValue.divideAndRemainder(BigInteger.TEN);
            if (!BigInteger.ZERO.equals(integers[1])) {
                break;
            }
            integerValue = integers[0];
        }
        
        return new BigDecimal(integerValue, scale);
    }
    
    /**
     * 清除BigDecimal中小数部分的0尾巴
     *
     * @param value value need clear zero tail
     * @return BigDecimal cleared zero tail
     */
    public static BigDecimal clearZeroTail(BigDecimal value) {
        return clearZeroTail(value, 0);
    }
    
    
    /**
     * Convert BigDecimal to Byte Array
     */
    public static byte[] toByteArray(BigDecimal value) {
        if (value == null) {
            return new byte[0];
        }
        
        var tempArray = value.unscaledValue().toByteArray();
        var result = new byte[tempArray.length + 1];
        result[0] = (byte) (value.scale() & 0xFF);
        System.arraycopy(tempArray, 0, result, 1, tempArray.length);
        
        return result;
    }
    
    
    /**
     * Recovery BigDecimal from Byte Array
     */
    public static BigDecimal fromByteArray(byte[] data) {
        if (data == null || data.length < 2) {
            return null;
        }
        
        var tempArray = new byte[data.length - 1];
        System.arraycopy(data, 1, tempArray, 0, tempArray.length);
        
        return new BigDecimal(new BigInteger(tempArray), data[0]);
        
    }
}
