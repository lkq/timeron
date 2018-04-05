package com.github.lkq.timeron.proxy;

import com.github.lkq.timeron.measure.TimeRecorders;

import java.lang.reflect.Proxy;

public class JDKDynamicProxy<T> implements TimerProxy<T> {

    private T target;
    private TimeRecorders timeRecorders;

    public JDKDynamicProxy(T target, TimeRecorders timeRecorders) {
        this.target = target;
        this.timeRecorders = timeRecorders;
    }

    public T create() {
        Class<?>[] interfaces = target.getClass().getInterfaces();
        return (T) Proxy.newProxyInstance(target.getClass().getClassLoader(), interfaces, new JDKInvocationHandler(target, timeRecorders));
    }
}
