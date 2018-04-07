package com.github.lkq.timeron;

import com.github.lkq.timeron.config.InterceptContext;
import com.github.lkq.timeron.config.CGLibInterceptor;
import com.github.lkq.timeron.config.ProxyFactory;
import com.github.lkq.timeron.measure.TimeRecorders;

public class Timer {

    private final TimeRecorders timeRecorders = new TimeRecorders();
    private final InterceptContext context = new InterceptContext(new CGLibInterceptor(timeRecorders), new ProxyFactory(timeRecorders));

    /**
     * return a proxy object for target, Dynamic Proxy or CGLib proxy will be created
     * depending on where the @Timer tag appears in the class hierarchy.
     * If the @Timer only tagged on parent interface methods declaration, Dynamic Proxy will be created
     * If the @Timer directly tagged on any class/superclass method in the hierarchy, CGLib proxy will be created
     * @param target
     * @param <T>
     * @return
     */
    public <T> T on(T target) {
        return context.createProxy(target);
    }

    /**
     * setup a class for interception
     * @param clz
     * @param <T>
     * @return
     */
    public <T> T intercept(Class<T> clz) {
        return context.intercept(clz);
    }

    /**
     * setup measured method call
     * @param methodCall
     * @param <T>
     */
    public <T> void measure(T methodCall) {
        context.finishInterception();
    }

    public String getStats() {
        ReportBuilder reportBuilder = new ReportBuilder();
        return reportBuilder.buildJSON(timeRecorders.getTimers());
    }
}
