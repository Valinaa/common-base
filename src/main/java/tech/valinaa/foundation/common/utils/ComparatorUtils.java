package tech.valinaa.foundation.common.utils;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.Function;

/**
 * 对比器
 *
 * @author Valinaa
 * @Date 2024/11/25 19:42
 */
public class ComparatorUtils {
    
    private ComparatorUtils() {
    }
    
    public static int compare(String a, String b, boolean ignoreCase) {
        return ignoreCase ? StringUtilsExt.compareIgnoreCase(a, b) : StringUtilsExt.compare(a, b);
    }
    
    public static int compare(String a, String b) {
        return StringUtilsExt.compare(a, b);
    }
    
    public static int compare(char a, char b, boolean ignoreCase) {
        return ignoreCase ? Character.compare(Character.toUpperCase(a), Character.toUpperCase(b))
                : Character.compare(a, b);
    }
    
    public static int compare(char a, char b) {
        return Character.compare(a, b);
    }
    
    public static int compare(int a, int b) {
        return Integer.compare(a, b);
    }
    
    public static int compare(long a, long b) {
        return Long.compare(a, b);
    }
    
    public static int compare(byte a, byte b) {
        return Byte.compare(a, b);
    }
    
    public static int compare(float a, float b) {
        return Float.compare(a, b);
    }
    
    public static int compare(double a, double b) {
        return Double.compare(a, b);
    }
    
    public static int compare(BigDecimal a, BigDecimal b) {
        return a == null ? (b == null ? 0 : -1) : (b == null ? 1 : a.compareTo(b));
    }
    
    
    /**
     * 对比2个相同类型集合中，多个不同的属性。
     *
     * @param a          对比的集合1
     * @param b          对比的集合2
     * @param keyGetters 获取属性的mapper
     * @param <T>        类型
     * @return 所有的值按顺序，比大小。前面相等才对比后面的。
     */
    public static <T> int compare(List<T> a, List<T> b, List<Function<T, Comparable>> keyGetters) {
        if (keyGetters == null) {
            throw new IllegalArgumentException("Comparator Function could not be null");
        }
        
        if (a == b) {
            return 0;
        } else if (a == null) {
            return -1;
        } else if (b == null) {
            return 1;
        }
        
        if (a.size() < b.size()) {
            return -1;
        } else if (b.size() < a.size()) {
            return 1;
        }
        
        for (int i = 0; i < a.size(); i++) {
            int result = compare(a.get(i), b.get(i), keyGetters);
            if (result != 0) {
                return result;
            }
        }
        return 0;
    }
    
    /**
     * 对比2个相同类型对象中，多个不同的属性。
     *
     * @param a          对比对象1
     * @param b          对比对象2
     * @param keyGetters 获取属性的mapper
     * @param <T>        类型
     * @return 所有的值按顺序，比大小。前面相等才对比后面的。
     */
    @SuppressWarnings("unchecked")
    public static <T> int compare(T a, T b, List<Function<T, Comparable>> keyGetters) {
        if (keyGetters == null) {
            throw new IllegalArgumentException("Comparator Function could not be null");
        }
        
        if (a == b) {
            return 0;
        } else if (a == null) {
            return -1;
        } else if (b == null) {
            return 1;
        }
        
        int result = 0;
        for (Function<T, Comparable> item : keyGetters) {
            Comparable a1 = item.apply(a);
            Comparable b1 = item.apply(b);
            
            if (a1 == null && b1 == null) {
                result = 0;
            } else if (a1 == null) {
                result = -1;
            } else if (b1 == null) {
                result = 1;
            } else {
                result = a1.compareTo(b1);
            }
            if (result != 0) {
                break;
            }
        }
        return result;
    }
}
