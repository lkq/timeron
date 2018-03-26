package com.github.lkq.timeron.proxy;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class CGLibMethodInterceptor implements MethodInterceptor{
    private Object target;

    public <T> CGLibMethodInterceptor(T target) {

        this.target = target;
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        return method.invoke(target, args);
    }
}
