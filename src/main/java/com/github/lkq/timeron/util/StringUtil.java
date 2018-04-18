package com.github.lkq.timeron.util;

public class StringUtil {

    public static boolean isBlank(String value) {
        return value == null || "".equals(value.trim());
    }

}
