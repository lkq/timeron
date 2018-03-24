package com.github.lkq.timeron;

import com.github.lkq.timeron.proxy.JdkDynamicProxy;

public class TimerProxyFactory {
    public <T> TimerProxy<T> createProxy(T target) {
        return new JdkDynamicProxy<>(target);
    }
}
