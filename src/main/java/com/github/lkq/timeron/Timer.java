package com.github.lkq.timeron;

import com.github.lkq.timeron.intercept.InterceptContext;
import com.github.lkq.timeron.intercept.Interceptor;
import com.github.lkq.timeron.intercept.MethodExtractor;
import com.github.lkq.timeron.intercept.ProxyFactory;
import com.github.lkq.timeron.measure.TimeRecorderFactory;

public class Timer {

    private final TimeRecorderFactory timeRecorderFactory = new TimeRecorderFactory();
    private final InterceptContext context = new InterceptContext(new Interceptor(new MethodExtractor()), new ProxyFactory(timeRecorderFactory));

    /**
     * create a proxy over the target object for measuring the method call performance
     * the measured method is defined by Timer.measure()
     * @param target the target object to measure
     * @param <T> generic type
     * @return a call through proxy over the target object which will collect method call performance
     */
    public <T> T on(T target) {
        try {
            return context.createProxy(target);
        } catch (NoSuchMethodException e) {
            throw new TimerException("unable to create proxy for" + target, e);
        }
    }

    /**
     * create a stub instance for setting up measurement, the method call on stub should pass to Timer.measure()
     * e.g
     * SomeClass some = timer.interceptor(SomeClass.class);
     * timer.measure(() -> some.someMethod());
     *
     * @param clz the intercepting class
     * @param <T> generic type
     * @return a stub which could be used for setting up measurement
     */
    public <T> T interceptor(Class<T> clz) {
        return context.createInterceptor(clz);
    }

    /**
     * config to measure on the specific method call
     * @param methodCall intercepting method call
     */
    public void measure(Runnable methodCall) {
        methodCall.run();
        context.completeIntercept();
    }

    /**
     * get the method performance statistics
     * @return method performance statistics
     */
    public String getStats() {
        ReportBuilder reportBuilder = new ReportBuilder();
        return reportBuilder.buildJSON(timeRecorderFactory.getTimers());
    }
}
