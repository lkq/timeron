package com.github.lkq.timeron.util;

import java.lang.reflect.Method;

public class StringUtil {
    public static boolean isBlank(String value) {
        return value == null || "".equals(value.trim());
    }

    public static String signature(Class<?> clz, Method method) {
        Class<?>[] parameterTypes = method.getParameterTypes();
        StringBuilder signature = new StringBuilder(clz.getName()).append(".").append(method.getName()).append("(");
        String comma = "";
        for (Class<?> parameterType : parameterTypes) {
            signature.append(comma).append(parameterType.getSimpleName());
            comma = ",";
        }
        signature.append(")");

        return signature.toString();
    }
}
