package com.github.lkq.timeron.intercept;

import com.github.lkq.timeron.TimerException;

import java.lang.reflect.Method;
import java.util.*;

public class Interceptor {

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

    /**
     * walk through the class hierarchy to extract the intercepted methods
     *
     * @param clz
     * @return
     */
    public List<Method> getInterceptedMethods(Class<?> clz) {
        ArrayList<Method> methods = new ArrayList<>();
        if (interceptedMethods.containsKey(clz)) {
            methods.addAll(interceptedMethods.get(clz));
        }

        methods.addAll(getSuperClassInterceptedMethods(clz, interceptedMethods));
        return methods;
    }

    private List<Method> getSuperClassInterceptedMethods(Class<?> clz, Map<Class, List<Method>> interceptedMethods) {
        Class<?> superClz = clz.getSuperclass();
        List<Method> methods = new ArrayList<>();
        while (superClz != null && !Object.class.equals(superClz)) {
            if (interceptedMethods.containsKey(superClz)) {
                methods.addAll(interceptedMethods.get(superClz));
            }
            superClz = superClz.getSuperclass();
        }
        return methods;
    }
}
