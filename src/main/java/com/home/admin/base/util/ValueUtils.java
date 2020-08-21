package com.home.admin.base.util;

/**
 * 对象值工具类
 */
public class ValueUtils extends org.springframework.util.StringUtils {

    /**
     * Object To String
     * @param object
     * @param defaultValue
     * @return
     */
    public static String safeToString(Object object, String defaultValue) {
        return object == null ? defaultValue : String.valueOf(object);
    }

    /**
     * Object To Integer
     * @param object
     * @param defaultValue
     * @return
     */
    public static Integer safeToInteger(Object object, Integer defaultValue) {
        return object == null ? defaultValue : Integer.valueOf(String.valueOf(object));
    }


}
