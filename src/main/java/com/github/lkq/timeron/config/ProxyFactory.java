package com.github.lkq.timeron.config;

import com.github.lkq.timeron.measure.TimeRecorders;
import com.github.lkq.timeron.proxy.CGLibProxyFactory;

public class ProxyFactory {

    private final CGLibProxyFactory cglibProxyFactory;
    private TimeRecorders timeRecorders;

    public ProxyFactory(TimeRecorders timeRecorders) {
        this.timeRecorders = timeRecorders;
        this.cglibProxyFactory = new CGLibProxyFactory(timeRecorders);
    }

    public <T> T create(T target) {
        return cglibProxyFactory.create(target);
    }
}
