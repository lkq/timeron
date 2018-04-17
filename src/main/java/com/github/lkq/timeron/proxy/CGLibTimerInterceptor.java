package com.github.lkq.timeron.proxy;

import com.github.lkq.timeron.measure.TimeRecorder;
import com.github.lkq.timeron.measure.TimeRecorderFactory;
import com.github.lkq.timeron.util.ReflectionUtil;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class CGLibTimerInterceptor implements MethodInterceptor{
    private Object target;
    private TimeRecorderFactory timeRecorderFactory;

    private Map<Method, TimeRecorder> timeRecorders;
    private Map<String, TimeRecorder> timeRecorderMap;

    public <T> CGLibTimerInterceptor(T target, List<Method> interceptedMethods, TimeRecorderFactory timeRecorderFactory) {
        Objects.requireNonNull(target, "proxy target is required");
        Objects.requireNonNull(interceptedMethods, "interceptedMethods is required");
        Objects.requireNonNull(timeRecorderFactory, "timeRecorderFactory is required");

        this.target = target;
        this.timeRecorderFactory = timeRecorderFactory;

        Class<?> clz = target.getClass();

        timeRecorderMap = new HashMap<>();
        for (Method method : interceptedMethods) {
            timeRecorderMap.put(ReflectionUtil.signature(clz, method), timeRecorderFactory.create(ReflectionUtil.signature(clz, method)));
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
