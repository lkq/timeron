package com.github.lkq.timeron;

import com.github.lkq.timeron.annotation.AnnotationFinder;
import com.github.lkq.timeron.measure.TimerConfig;
import com.github.lkq.timeron.proxy.CGLIBProxy;
import com.github.lkq.timeron.proxy.JDKDynamicProxy;

public class TimerProxyFactory {
    AnnotationFinder annotationFinder;

    public TimerProxyFactory(AnnotationFinder annotationFinder) {
        this.annotationFinder = annotationFinder;
    }

    public <T> TimerProxy<T> createProxy(T target) {
        TimerConfig timerConfig = new TimerConfig();
        if (canUseJDKProxy(target)) {
            return new JDKDynamicProxy<>(target, timerConfig);
        }
        return new CGLIBProxy<>(target, timerConfig);
    }

    private <T> boolean canUseJDKProxy(T target) {
        return !annotationFinder.annotatedMethodPresentInClassHierarchy(target.getClass(), com.github.lkq.timeron.annotation.Timer.class);
    }
}
