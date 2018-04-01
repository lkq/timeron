package com.github.lkq.timeron.proxy;

import com.github.lkq.timeron.measure.InvocationTimer;
import com.github.lkq.timeron.measure.TimerConfig;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class CGLibMethodInterceptor implements MethodInterceptor{
    private Object target;
    private TimerConfig timerConfig;

    public <T> CGLibMethodInterceptor(T target, TimerConfig timerConfig) {
        this.target = target;
        this.timerConfig = timerConfig;
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        InvocationTimer invocationTimer = getTimer(method);
        if (invocationTimer != null) {
            long startTime = System.nanoTime();
            Object retVal = method.invoke(target, args);
            long stopTime = System.nanoTime();
            invocationTimer.record(startTime, stopTime);
            return retVal;
        } else {
            return method.invoke(target, args);
        }
    }

    private InvocationTimer getTimer(Method method) {
        return timerConfig.getTimer(method);
    }
}
