package com.github.lkq.timeron.intercept;

import com.github.lkq.timeron.util.ReflectionUtil;

import java.lang.reflect.Method;

public class MeasuredMethod {
    private final String signature;
    private Class<?> clz;
    private Method method;

    public MeasuredMethod(Class<?> clz, Method method) throws NoSuchMethodException {
        // throw if the method is not defined in clz or its super class / interface
        clz.getMethod(method.getName(), method.getParameterTypes());
        this.clz = clz;
        this.method = method;
        this.signature = ReflectionUtil.signature(clz, method);
    }

    public String signature() {
        return signature;
    }

    public Class<?> clz() {
        return clz;
    }

    @Override
    public String toString() {
        return "MeasuredMethod{" +
                "signature='" + signature + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MeasuredMethod that = (MeasuredMethod) o;

        return signature != null ? signature.equals(that.signature) : that.signature == null;
    }

    @Override
    public int hashCode() {
        return signature != null ? signature.hashCode() : 0;
    }
}
