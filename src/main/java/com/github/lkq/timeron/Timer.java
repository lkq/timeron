package com.github.lkq.timeron;

import com.github.lkq.timeron.annotation.AnnotationFinder;

public class Timer {

    private final TimerProxyFactory proxyFactory;

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
     * return a Dynamic Proxy for target, only the method declared in iface will be intercepted
     * @param target
     * @param iface
     * @param <I>
     * @param <C>
     * @return
     */
    public <I, C extends I> I on(C target, Class<I> iface) {
        throw new TimerException("Pending Implementation");
    }
}
