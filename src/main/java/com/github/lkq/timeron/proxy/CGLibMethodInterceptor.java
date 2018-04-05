package com.github.lkq.timeron.proxy;

import com.github.lkq.timeron.measure.TimeRecorder;
import com.github.lkq.timeron.measure.TimeRecorderFactory;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class CGLibMethodInterceptor implements MethodInterceptor{
    private Object target;
    private TimeRecorderFactory timeRecorderFactory;

    private Map<Method, TimeRecorder> timeRecorders;

    public <T> CGLibMethodInterceptor(T target, List<Method> interceptedMethods, TimeRecorderFactory timeRecorderFactory) {
        Objects.requireNonNull(target, "proxy target is required");
        Objects.requireNonNull(interceptedMethods, "interceptedMethods is required");
        Objects.requireNonNull(timeRecorderFactory, "timeRecorderFactory is required");

        this.target = target;
        this.timeRecorderFactory = timeRecorderFactory;

        timeRecorders = new HashMap<>();
        for (Method method : interceptedMethods) {
            timeRecorders.put(method, null);
        }

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
        if (timeRecorders.containsKey(method)) {
            TimeRecorder timeRecorder = timeRecorders.get(method);
            if (timeRecorder == null) {
                timeRecorder = timeRecorderFactory.create();
                timeRecorders.put(method, timeRecorder);
            }
            return timeRecorder;
        }
        return null;
    }
}
