package tech.valinaa.foundation.common.converter;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * @author Valinaa
 * @Date 2024/11/25 11:22
 */
public class BitConverter {
    private static final int INT32_SIZE = 4;
    private static final int INT64_SIZE = 8;
    
    private BitConverter() {
    }
    
    public static int toInt32(byte[] array, int startIndex) {
        return toInt32(array, startIndex, true);
    }
    
    public static int toInt32(byte[] array, int startIndex, boolean littleEndian) {
        if (array.length - startIndex < INT32_SIZE) {
            throw new ArrayIndexOutOfBoundsException("start index + 4 in out of range");
        } else {
            ByteBuffer buffer = ByteBuffer.wrap(array, startIndex, INT32_SIZE);
            buffer.order(littleEndian ? ByteOrder.LITTLE_ENDIAN : ByteOrder.BIG_ENDIAN);
            return buffer.getInt();
        }
    }
    
    public static byte[] fromInt32(int value) {
        return fromInt32(value, true);
    }
    
    public static byte[] fromInt32(int value, boolean littleEndian) {
        ByteBuffer bf = ByteBuffer.allocate(INT32_SIZE);
        bf.order(littleEndian ? ByteOrder.LITTLE_ENDIAN : ByteOrder.BIG_ENDIAN);
        bf.putInt(value);
        return bf.array();
    }
    
    public static long toInt64(byte[] array, int startIndex) {
        return toInt64(array, startIndex, true);
    }
    
    public static long toInt64(byte[] array, int startIndex, boolean littleEndian) {
        if (array.length - startIndex < INT64_SIZE) {
            throw new ArrayIndexOutOfBoundsException("start index + 8 in out of range");
        } else {
            ByteBuffer buffer = ByteBuffer.wrap(array, startIndex, INT64_SIZE);
            buffer.order(littleEndian ? ByteOrder.LITTLE_ENDIAN : ByteOrder.BIG_ENDIAN);
            return buffer.getLong();
        }
    }
    
    public static byte[] fromInt64(long value) {
        return fromInt64(value, true);
    }
    
    public static byte[] fromInt64(long value, boolean littleEndian) {
        ByteBuffer bf = ByteBuffer.allocate(INT64_SIZE);
        bf.order(littleEndian ? ByteOrder.LITTLE_ENDIAN : ByteOrder.BIG_ENDIAN);
        bf.putLong(value);
        return bf.array();
    }
}
