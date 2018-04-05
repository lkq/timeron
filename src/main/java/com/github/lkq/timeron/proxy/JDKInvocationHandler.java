package com.github.lkq.timeron.proxy;

import com.github.lkq.timeron.measure.TimeRecorder;
import com.github.lkq.timeron.measure.TimeRecorders;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class JDKInvocationHandler implements InvocationHandler {

    private Object target;
    private TimeRecorders timeRecorders;

    public JDKInvocationHandler(Object target, TimeRecorders timeRecorders) {
        this.target = target;
        this.timeRecorders = timeRecorders;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        TimeRecorder timer = getTimer(method);
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

    private TimeRecorder getTimer(Method method) {
        return timeRecorders.getTimer(method);
    }
}
