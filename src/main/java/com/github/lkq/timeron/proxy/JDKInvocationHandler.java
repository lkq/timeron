package com.github.lkq.timeron.proxy;

import com.github.lkq.timeron.measure.InvocationTimer;
import com.github.lkq.timeron.measure.InvocationTimers;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class JDKInvocationHandler implements InvocationHandler {

    private Object target;
    private InvocationTimers invocationTimers;

    public JDKInvocationHandler(Object target, InvocationTimers invocationTimers) {
        this.target = target;
        this.invocationTimers = invocationTimers;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        InvocationTimer timer = getTimer(method);
        if (timer != null) {
            long startTime = System.nanoTime();
            Object retVal = method.invoke(target, args);
            long stopTime = System.nanoTime();
            timer.record(startTime, stopTime);
            return retVal;
        } else {
            return method.invoke(target, args);
        }
    }

    private InvocationTimer getTimer(Method method) {
        return invocationTimers.get(method);
    }
}
