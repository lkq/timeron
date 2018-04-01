package com.github.lkq.timeron;

import com.github.lkq.timeron.annotation.AnnotationFinder;
import com.github.lkq.timeron.config.InterceptContext;
import com.github.lkq.timeron.config.Interceptor;

public class Timer {

    private final TimerProxyFactory proxyFactory;
    private final InterceptContext context = new InterceptContext(new Interceptor());

    public Timer() {
        proxyFactory = new TimerProxyFactory(new AnnotationFinder());
    }

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
        TimerProxy<T> proxy = proxyFactory.createProxy(target);
        return proxy.getProxy();
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
     * @param beginInterception
     * @param <T>
     */
    public <T> void measure(T beginInterception) {
        context.finishInterception();
    }
}
