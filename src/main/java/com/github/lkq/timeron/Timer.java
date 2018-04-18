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
     * create a CGLib proxy over the target object
     * the proxy will measure the method call performance base on the Timer.measure() setup
     * @param target
     * @param <T>
     * @return
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
     * SomeClass some = timer.intercept(SomeClass.class);
     * timer.measure(some.someMethod());
     *
     * @param clz
     * @param <T>
     * @return
     */
    public <T> T intercept(Class<T> clz) {
        return context.intercept(clz);
    }

    /**
     * add config to measure on the specific method call
     * @param methodCall
     * @param <T>
     */
    public <T> void measure(T methodCall) {
        context.completeIntercept();
    }

    /**
     * get the method call performance statistics
     * @return
     */
    public String getStats() {
        ReportBuilder reportBuilder = new ReportBuilder();
        return reportBuilder.buildJSON(timeRecorderFactory.getTimers());
    }
}
