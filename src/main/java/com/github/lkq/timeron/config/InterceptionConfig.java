package com.github.lkq.timeron.config;

import com.github.lkq.timeron.TimerException;

import java.lang.reflect.Method;
import java.util.*;

public class InterceptionConfig {

    private Map<Class, List<Method>> interceptedMethods = new HashMap<>();
    private Method interceptingMethod;

    public void startIntercept(Method method) {
        if (this.interceptingMethod != null) {
            throw new TimerException("unfinished interception detected on " + this.interceptingMethod);
        }
        this.interceptingMethod = method;
    }

    public void completeIntercept() {
        if (this.interceptingMethod == null) {
            throw new TimerException("no interception in progress");
        }
        Class<?> clz = interceptingMethod.getDeclaringClass();
        List<Method> methods = this.interceptedMethods.computeIfAbsent(clz, k -> new ArrayList<>());
        methods.add(interceptingMethod);
        this.interceptingMethod = null;
    }

    public List<Method> getInterceptedMethods(Class<?> clz) {
        if (interceptedMethods.containsKey(clz)) {
            return interceptedMethods.get(clz);
        }
        return Collections.emptyList();
    }
}
