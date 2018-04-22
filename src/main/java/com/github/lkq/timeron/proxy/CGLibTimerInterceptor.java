package com.github.lkq.timeron.proxy;

import com.github.lkq.timeron.intercept.MeasuredMethod;
import com.github.lkq.timeron.measure.TimeRecorder;
import com.github.lkq.timeron.measure.TimeRecorderFactory;
import com.github.lkq.timeron.util.ReflectionUtil;
import com.github.lkq.timeron.thirdparty.cglib.proxy.MethodInterceptor;
import com.github.lkq.timeron.thirdparty.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class CGLibTimerInterceptor implements MethodInterceptor {
    private Object target;
    private Map<String, TimeRecorder> timeRecorderMap;

    public <T> CGLibTimerInterceptor(T target, List<MeasuredMethod> interceptedMethods, TimeRecorderFactory timeRecorderFactory) {
        Objects.requireNonNull(target, "proxy target is required");
        Objects.requireNonNull(interceptedMethods, "interceptedMethods is required");
        Objects.requireNonNull(timeRecorderFactory, "timeRecorderFactory is required");

        this.target = target;

        Class<?> clz = target.getClass();

        timeRecorderMap = new HashMap<>();
        for (MeasuredMethod method : interceptedMethods) {
            if (!method.clz().isAssignableFrom(clz)) {
                throw new IllegalArgumentException("measured method " + method + " is not for class" + clz.getName());
            }
            timeRecorderMap.put(method.signature(), timeRecorderFactory.create(method.signature()));
        }
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        Class<?> targetClz = obj.getClass().getSuperclass();
        TimeRecorder timeRecorder = getTimeRecorder(targetClz, method);
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

    private TimeRecorder getTimeRecorder(Class<?> clz, Method method) {
        String signature = ReflectionUtil.signature(clz, method);
        if (timeRecorderMap.containsKey(signature)) {
            return timeRecorderMap.get(signature);
        }
        return null;
    }
}
