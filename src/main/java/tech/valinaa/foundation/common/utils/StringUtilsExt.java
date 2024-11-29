package tech.valinaa.foundation.common.utils;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.ParameterizedMessageFactory;
import tech.valinaa.foundation.common.constants.StringConstants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.apache.commons.lang3.StringUtils.equalsIgnoreCase;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isEmpty;
import static org.apache.commons.lang3.StringUtils.split;
import static org.apache.commons.lang3.StringUtils.splitPreserveAllTokens;

/**
 * String工具类扩展
 *
 * @author Valinaa
 * @Date 2024/11/25 19:27
 */
public final class StringUtilsExt {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final ParameterizedMessageFactory FACTORY = ParameterizedMessageFactory.INSTANCE;
    
    private StringUtilsExt() {
    }
    
    /**
     * 如果exam为null或空字符串（包括全空格），就用replace替换，否则返回exam
     */
    public static String blankOr(String exam, String replace) {
        return isBlank(exam) ? replace : exam;
    }
    
    /**
     * 如果exam为null或空字符串（包括全空格），就用replace替换，否则返回exam
     */
    public static String emptyOr(String exam, String replace) {
        return isEmpty(exam) ? replace : exam;
    }
    
    /**
     * 用于空字符串为通配的情况下，判断comp是否等于exam
     * <p>
     * 如果 exam为null或空白字符串（空或全部是空格）就放行
     * 否则 判断字符串是否等于comp
     *
     * @param exam 用于判断的字符串，如果为null或空字符串 则视为通配符
     * @param comp 用于比较的字符串
     */
    public static boolean isBlankOrEquals(String exam, String comp) {
        return isBlank(exam) || exam.equalsIgnoreCase(comp);
    }
    
    public static boolean splitContains(String config, String item) {
        return splitContains(config, item, StringConstants.COMMA);
    }
    
    public static boolean splitContains(String config, String item, String split) {
        if (Objects.isNull(config)) {
            return false;
        }
        
        if (null == item) {
            item = StringConstants.EMPTY;
        }
        
        item = item.trim();
        for (String str : splitPreserveAllTokens(config, split)) {
            str = str.trim();
            if (str.equals(item)) {
                return true;
            }
        }
        return false;
    }
    
    public static <T> boolean splitContains(String config, T compareItem, Function<String, T> mapper) {
        return splitContains(config, compareItem, mapper, StringConstants.COMMA);
    }
    
    public static <T> boolean splitContains(String config, T compareItem, Function<String, T> mapper,
                                            String separator) {
        
        if (isBlank(config) || mapper == null) {
            return false;
        }
        
        if (separator == null) {
            separator = StringConstants.COMMA;
        }
        
        for (String str : split(config, separator)) {
            T temp = mapper.apply(str);
            if (compareItem == null && temp == null) {
                return true;
            } else if (compareItem != null && compareItem.equals(temp)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * 使用separator分隔集合。
     *
     * @param collection 用于拼接的集合
     * @param separator  用于分隔字符串的分隔符
     */
    public static <T> String join(Collection<T> collection, String separator) {
        if (CollectionUtils.isEmpty(collection)) {
            return "";
        }
        
        return ((Stream<?>) collection.stream())
                .filter(Objects::nonNull)
                .map(String::valueOf)
                .filter(StringUtils::isNotBlank)
                .collect(Collectors.joining(separator));
    }
    
    /**
     * 以 comparable接口方式对比2个字符串
     */
    public static int compare(String a, String b) {
        if (a == null) {
            return b == null ? 0 : -1;
        }
        return b == null ? 1 : a.compareTo(b);
    }
    
    /**
     * 以 comparable接口方式对比2个字符串
     */
    public static int compareIgnoreCase(String a, String b) {
        if (a == null) {
            return b == null ? 0 : -1;
        }
        return b == null ? 1 : a.compareToIgnoreCase(b);
    }
    
    public static String trim(String str, char chars) {
        return trim(str, chars, false);
    }
    
    public static String trim(String str, char chars, boolean ignorcase) {
        if (isBlank(str)) {
            return str;
        }
        int startIndex = getTrimStartIndex(str, chars, ignorcase);
        if (startIndex >= str.length()) {
            return StringConstants.EMPTY;
        }
        int lastIndex = getTrimEndIndex(str, chars, ignorcase);
        if (lastIndex + 1 <= startIndex) {
            return StringConstants.EMPTY;
        }
        return str.substring(startIndex, lastIndex + 1);
    }
    
    public static String lTrim(String str, char chars) {
        return lTrim(str, chars, false);
    }
    
    public static String lTrim(String str, char chars, boolean ignorcase) {
        if (isBlank(str)) {
            return str;
        }
        int startIndex = getTrimStartIndex(str, chars, ignorcase);
        if (startIndex >= str.length()) {
            return StringConstants.EMPTY;
        }
        return str.substring(startIndex);
    }
    
    public static String rTrim(String str, char chars) {
        return rTrim(str, chars, false);
    }
    
    public static String rTrim(String str, char chars, boolean ignorcase) {
        if (isBlank(str)) {
            return str;
        }
        int lastIndex = getTrimEndIndex(str, chars, ignorcase);
        if (lastIndex < 0) {
            return StringConstants.EMPTY;
        }
        return str.substring(0, lastIndex + 1);
    }
    
    /**
     * 返回指定字符串不等于 chars 的最后一个字符的索引
     *
     * @param str       指定字符串
     * @param chars     指定字符
     * @param ignorcase 是否忽略大小写
     * @return 返回指定字符串不等于 chars 的最后一个字符的索引; 如果 str 的字符都为 chars，返回 -1。
     */
    private static int getTrimEndIndex(String str, char chars, boolean ignorcase) {
        int lastIndex = str.length() - 1;
        while (lastIndex >= 0 && ComparatorUtils.compare(str.charAt(lastIndex), chars, ignorcase) == 0) {
            lastIndex--;
        }
        return lastIndex;
    }
    
    /**
     * 返回指定字符串不等于chars的第一个字符的索引。
     *
     * @param str       指定字符串
     * @param chars     指定字符
     * @param ignorcase 是否忽略大小写
     * @return 返回指定 str 中不等于 chars 的第一个字符的索引; 如果 str 的字符都为 chars，返回 str 的长度。
     */
    private static int getTrimStartIndex(String str, char chars, boolean ignorcase) {
        int index = 0;
        while (index < str.length() && ComparatorUtils.compare(str.charAt(index), chars, ignorcase) == 0) {
            index++;
        }
        return index;
    }
    
    /**
     * 按照Log方式进行格式化对msgPattern中的{}，使用args进行替换
     */
    public static String logFormat(String msgPattern, Object... args) {
        if (isBlank(msgPattern)) {
            return "";
        }
        try {
            return FACTORY.newMessage(msgPattern, args).getFormattedMessage();
        } catch (Exception e) {
            LOGGER.error(String.format("Unable to format msg: %s", msgPattern), e);
            return msgPattern;
        }
    }
    
    /**
     * 把字符串按默认分隔符[,] 分割成List对象
     */
    public static List<String> asStringList(String configPropertyStr) {
        return asStringList(configPropertyStr, StringConstants.COMMA);
    }
    
    /**
     * 把字符串根据给定的分隔符，分割成List对象
     */
    public static List<String> asStringList(String str, String separator) {
        if (isBlank(str)) {
            return new ArrayList<>();
        }
        
        if (isBlank(separator)) {
            separator = StringConstants.COMMA;
        }
        
        return splitToList(str, separator, Function.identity());
    }
    
    /**
     * 把整数字符串根据默认分隔符分割，返回List<Integer>对象
     */
    public static List<Integer> asIntegerList(String configPropertyStr) {
        return asIntegerList(configPropertyStr, StringConstants.COMMA);
    }
    
    /**
     * 把整数字符串根据给定的分隔符分割，返回List<Integer>对象
     */
    public static List<Integer> asIntegerList(String configPropertyStr, String separator) {
        if (isBlank(configPropertyStr)) {
            return new ArrayList<>();
        }
        
        if (isBlank(separator)) {
            separator = StringConstants.COMMA;
        }
        
        return splitToList(configPropertyStr, separator, Integer::valueOf);
    }
    
    /**
     * 判断Null，返回空字符串
     */
    public static String convertIfNull(String str) {
        if (str == null) {
            return StringConstants.EMPTY;
        }
        return str;
    }
    
    
    /**
     * 将字符串按照分隔符拆分成多个对象，并且转换成指定类型List集合
     *
     * @param str       需要拆分的字符串
     * @param separator 字符串分隔符
     * @param converter 转换器
     */
    public static <T> List<T> splitToList(String str, String separator, Function<String, T> converter) {
        return Arrays.stream(split(str, separator))
                .map(converter)
                .toList();
    }
    
    /**
     * 将字符串按照分隔符拆分成多个对象，并且转换成指定类型Set集合
     *
     * @param str       需要拆分的字符串
     * @param separator 字符串分隔符
     * @param converter 转换器
     */
    public static <T> Set<T> splitToSet(String str, String separator, Function<String, T> converter) {
        return Arrays.stream(split(str, separator))
                .map(converter)
                .collect(Collectors.toSet());
    }
    
    public static boolean notEquals(String s1, String s2) {
        return !StringUtils.equals(s1, s2);
    }
    
    public static boolean notEqualsIgnoreCase(String s1, String s2) {
        return !equalsIgnoreCase(s1, s2);
    }
}

