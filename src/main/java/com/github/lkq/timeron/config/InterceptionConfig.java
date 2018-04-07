package com.github.lkq.timeron.config;

import com.github.lkq.timeron.TimerException;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

public class InterceptionConfig {

    private Set<Method> interceptedMethods = new HashSet<>();
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
        this.interceptedMethods.add(this.interceptingMethod);
        this.interceptingMethod = null;
    }

    public boolean isMethodIntercepted(Method method) {
        return interceptedMethods.contains(method);
    }
}
