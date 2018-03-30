package com.github.lkq.timeron;

import com.github.lkq.timeron.annotation.AnnotationFinder;
import com.github.lkq.timeron.measure.InvocationTimers;
import com.github.lkq.timeron.proxy.CGLIBProxy;
import com.github.lkq.timeron.proxy.JDKDynamicProxy;

public class TimerProxyFactory {
    AnnotationFinder annotationFinder;
    private InvocationTimers invocationTimers;

    public TimerProxyFactory(AnnotationFinder annotationFinder, InvocationTimers invocationTimers) {
        this.annotationFinder = annotationFinder;
        this.invocationTimers = invocationTimers;
    }

    public <T> TimerProxy<T> createProxy(T target) {
        if (canUseJDKProxy(target)) {
            return new JDKDynamicProxy<>(target, invocationTimers);
        }
        return new CGLIBProxy<>(target);
    }

    private <T> boolean canUseJDKProxy(T target) {
        return !annotationFinder.annotatedMethodPresentInClassHierarchy(target.getClass(), com.github.lkq.timeron.annotation.Timer.class);
    }
}
