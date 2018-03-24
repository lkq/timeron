package com.github.lkq.timeron;

public class Timer {

    private final TimerProxyFactory proxyFactory;

    public Timer() {
        proxyFactory = new TimerProxyFactory();
    }

    public <T> T on(T target) {
        TimerProxy<T> proxy = proxyFactory.createProxy(target);
        return proxy.getProxy();
    }
}
