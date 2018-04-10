package com.github.lkq.timeron.config;

import com.github.lkq.timeron.measure.TimeRecorderFactory;
import com.github.lkq.timeron.proxy.CGLibProxyFactory;

import java.lang.reflect.Method;
import java.util.List;

public class ProxyFactory {

    private final CGLibProxyFactory cglibProxyFactory;

    public ProxyFactory(TimeRecorderFactory timeRecorderFactory) {
        this.cglibProxyFactory = new CGLibProxyFactory(timeRecorderFactory);
    }

    public <T> T create(T target, List<Method> interceptedMethods) {
        return cglibProxyFactory.create(target, interceptedMethods);
    }
}
