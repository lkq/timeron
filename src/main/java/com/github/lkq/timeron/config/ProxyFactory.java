package com.github.lkq.timeron.config;

import com.github.lkq.timeron.proxy.CGLibProxyFactory;

import java.lang.reflect.Method;
import java.util.List;

public class ProxyFactory {

    private final CGLibProxyFactory cglibProxyFactory;

    public ProxyFactory() {
        this.cglibProxyFactory = new CGLibProxyFactory();
    }

    public <T> T create(T target, List<Method> interceptedMethods) {
        return cglibProxyFactory.create(target, interceptedMethods);
    }
}
