package com.github.lkq.timeron.proxy;

import com.github.lkq.timeron.TimerProxy;
import com.github.lkq.timeron.measure.InvocationTimers;

import java.lang.reflect.Proxy;

public class JDKDynamicProxy<T> implements TimerProxy<T> {

    private T target;
    private InvocationTimers invocationTimers;

    public JDKDynamicProxy(T target, InvocationTimers invocationTimers) {
        this.target = target;
        this.invocationTimers = invocationTimers;
    }

    public T getProxy() {
        Class<?>[] interfaces = target.getClass().getInterfaces();
        return (T) Proxy.newProxyInstance(target.getClass().getClassLoader(), interfaces, new JDKInvocationHandler(target, invocationTimers));
    }
}
