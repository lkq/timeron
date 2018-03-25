package com.github.lkq.timeron;

import com.github.lkq.timeron.annotation.AnnotationFinder;

public class Timer {

    private final TimerProxyFactory proxyFactory;

    public Timer() {
        proxyFactory = new TimerProxyFactory(new AnnotationFinder());
    }

    public <T> T on(T target) {
        TimerProxy<T> proxy = proxyFactory.createProxy(target);
        return proxy.getProxy();
    }
}
