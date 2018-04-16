package com.github.lkq.timeron.intercept;

import com.github.lkq.timeron.util.StringUtil;

import java.lang.reflect.Method;

public class MeasuredMethod {
    private final String signature;
    private Class<?> clz;
    private Method method;

    public MeasuredMethod(Class<?> clz, Method method) {
        this.clz = clz;
        this.method = method;
        this.signature = StringUtil.signature(clz, method);
    }

    public String signature() {
        return signature;
    }

}
