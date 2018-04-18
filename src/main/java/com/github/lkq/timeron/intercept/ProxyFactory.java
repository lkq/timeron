package com.github.lkq.timeron.intercept;

import com.github.lkq.timeron.measure.TimeRecorderFactory;
import com.github.lkq.timeron.proxy.CGLibProxyFactory;

import java.util.List;

public class ProxyFactory {

    private final CGLibProxyFactory cglibProxyFactory;

    public ProxyFactory(TimeRecorderFactory timeRecorderFactory) {
        this.cglibProxyFactory = new CGLibProxyFactory(timeRecorderFactory);
    }

    public <T> T create(T target, List<MeasuredMethod> interceptedMethods) {
        return cglibProxyFactory.create(target, interceptedMethods);
    }
}
