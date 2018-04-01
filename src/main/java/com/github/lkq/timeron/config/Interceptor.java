package com.github.lkq.timeron.config;

import com.github.lkq.timeron.TimerException;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class Interceptor implements MethodInterceptor {

    private boolean interceptInProgress;

    private Method method;

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        this.method = method;
        this.interceptInProgress = true;
        return null;
    }

    public void finishInterception() {
        if (!interceptInProgress) {
            throw new TimerException("interception not started");
        }
        interceptInProgress = false;
    }
}
