package com.github.lkq.timeron.proxy;

import com.github.lkq.timeron.measure.InvocationTimer;
import com.github.lkq.timeron.measure.TimerConfig;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class JDKInvocationHandler implements InvocationHandler {

    private Object target;
    private TimerConfig timerConfig;

    public JDKInvocationHandler(Object target, TimerConfig timerConfig) {
        this.target = target;
        this.timerConfig = timerConfig;
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
        return timerConfig.getTimer(method);
    }
}
