package com.github.lkq.timeron.util;

import java.lang.reflect.Method;

public class ReflectionUtil {

    /**
     * return true only if all criteria are met:
     * 1. the methods are in the same class hierarchy
     * 2. the methods have same name
     * 3. the methods have same parameter types
     * @param method
     * @param other
     * @return
     */
    public static boolean methodSignatureEquals(Method method, Method other) {
        if (method.getDeclaringClass().isAssignableFrom(other.getDeclaringClass()) || other.getDeclaringClass().isAssignableFrom(method.getDeclaringClass())) {
            if (!method.getName().equals(other.getName())) {
                return false;
            } else {
                Class<?>[] paramTypes = method.getParameterTypes();
                Class<?>[] thatParamTypes = other.getParameterTypes();
                if (paramTypes.length != thatParamTypes.length) {
                    return false;
                }
                for (int i = 0; i < paramTypes.length; i++) {
                    if (!paramTypes[i].equals(thatParamTypes[i])) {
                        return false;
                    }
                }
                return true;
            }
        } else {
            return false;
        }
    }

    /**
     * create method signature with the invoking class.
     *
     * @param clz
     * @param method
     * @return
     */
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
