package com.github.lkq.timeron.config;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class CGLibInterceptorAdaptor implements MethodInterceptor {

    private InterceptionConfig interceptionConfig;

    public CGLibInterceptorAdaptor(InterceptionConfig interceptionConfig) {
        this.interceptionConfig = interceptionConfig;
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        interceptionConfig.startIntercept(method);
        return null;
    }

    public void completeIntercept() {
        interceptionConfig.completeIntercept();
    }
}
