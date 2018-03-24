package com.github.lkq.timeron.proxy;

import com.github.lkq.timeron.TimerProxy;

import java.lang.reflect.Proxy;

public class JdkDynamicProxy<T> implements TimerProxy<T> {

    private T target;

    public JdkDynamicProxy(T target) {
        this.target = target;
    }

    public T getProxy() {
        Class<?>[] interfaces = target.getClass().getInterfaces();
        return (T) Proxy.newProxyInstance(target.getClass().getClassLoader(), interfaces, new TimerInvocationHandler(target));
    }
}
