package com.github.lkq.timeron;

import com.github.lkq.timeron.annotation.AnnotationFinder;
import com.github.lkq.timeron.proxy.CGLIBProxy;
import com.github.lkq.timeron.proxy.JDKDynamicProxy;

public class TimerProxyFactory {
    AnnotationFinder annotationFinder;

    public TimerProxyFactory(AnnotationFinder annotationFinder) {
        this.annotationFinder = annotationFinder;
    }

    public <T> TimerProxy<T> createProxy(T target) {
        if (canUseJDKProxy(target)) {
            return new JDKDynamicProxy<>(target);
        }
        return new CGLIBProxy<>(target);
    }

    private <T> boolean canUseJDKProxy(T target) {
        return !annotationFinder.annotatedMethodPresentInClassHierarchy(target.getClass(), com.github.lkq.timeron.annotation.Timer.class);
    }
}
