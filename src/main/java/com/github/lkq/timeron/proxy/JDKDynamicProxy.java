package com.github.lkq.timeron.proxy;

import com.github.lkq.timeron.measure.TimerConfig;

import java.lang.reflect.Proxy;

public class JDKDynamicProxy<T> implements TimerProxy<T> {

    private T target;
    private TimerConfig timerConfig;

    public JDKDynamicProxy(T target, TimerConfig timerConfig) {
        this.target = target;
        this.timerConfig = timerConfig;
    }

    public T getProxy() {
        Class<?>[] interfaces = target.getClass().getInterfaces();
        return (T) Proxy.newProxyInstance(target.getClass().getClassLoader(), interfaces, new JDKInvocationHandler(target, timerConfig));
    }
}
