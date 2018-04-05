package com.github.lkq.timeron.proxy;

import com.github.lkq.timeron.measure.TimeRecorder;
import com.github.lkq.timeron.measure.TimeRecorders;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class CGLibMethodInterceptor implements MethodInterceptor{
    private Object target;
    private TimeRecorders timeRecorders;

    public <T> CGLibMethodInterceptor(T target, TimeRecorders timeRecorders) {
        this.target = target;
        this.timeRecorders = timeRecorders;
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        TimeRecorder timeRecorder = getTimer(method);
        if (timeRecorder != null) {
            long startTime = System.nanoTime();
            Object retVal = method.invoke(target, args);
            long stopTime = System.nanoTime();
            timeRecorder.record(startTime, stopTime);
            return retVal;
        } else {
            return method.invoke(target, args);
        }
    }

    private TimeRecorder getTimer(Method method) {
        return timeRecorders.getTimer(method);
    }
}
