package com.github.lkq.timeron.util;

import java.lang.reflect.Method;

public class StringUtil {

    public static boolean isBlank(String value) {
        return value == null || "".equals(value.trim());
    }

    public static String toString(Method method) {
        StringBuilder keyBuilder = new StringBuilder(100)
                .append(method.getDeclaringClass().getName())
                .append(".")
                .append(method.getName())
                .append("(");
        String comma = "";
        for (Class<?> type : method.getParameterTypes()) {
            keyBuilder.append(comma)
                    .append(type.getSimpleName());
        }
        keyBuilder.append(")");
        return keyBuilder.toString();
    }
}
