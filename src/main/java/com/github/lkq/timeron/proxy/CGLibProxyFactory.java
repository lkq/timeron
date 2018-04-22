package com.github.lkq.timeron.proxy;

import com.github.lkq.timeron.TimerException;
import com.github.lkq.timeron.intercept.MeasuredMethod;
import com.github.lkq.timeron.measure.TimeRecorderFactory;
import com.github.lkq.timeron.thirdparty.cglib.proxy.Callback;
import com.github.lkq.timeron.thirdparty.cglib.proxy.Enhancer;
import com.github.lkq.timeron.thirdparty.cglib.proxy.Factory;
import org.objenesis.Objenesis;
import org.objenesis.ObjenesisStd;

import java.util.List;

public class CGLibProxyFactory {

    private final TimeRecorderFactory timeRecorderFactory;
    private Objenesis objenesis = new ObjenesisStd();

    public CGLibProxyFactory(TimeRecorderFactory timeRecorderFactory) {
        this.timeRecorderFactory = timeRecorderFactory;
    }

    public <T> T create(T target, List<MeasuredMethod> measuredMethods) {
        try {
            Class<?> rootClass = target.getClass();

            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(rootClass);

            CGLibTimerInterceptor callback = new CGLibTimerInterceptor(target, measuredMethods, timeRecorderFactory);
            enhancer.setCallbackFilter(method -> 0);
            enhancer.setCallbackType(callback.getClass());
            return (T) createProxyClassAndInstance(enhancer, new Callback[]{callback});
        } catch (Exception e) {
            throw new TimerException("failed to create proxy", e);
        }

    }

    protected Object createProxyClassAndInstance(Enhancer enhancer, Callback[] callbacks) {
        Class<?> proxyClass = enhancer.createClass();
        Object proxyInstance;

        // TODO: add some cache to improve performance
        proxyInstance = objenesis.newInstance(proxyClass);

        // TODO: If objenesis doesn't work...

        ((Factory) proxyInstance).setCallbacks(callbacks);
        return proxyInstance;
    }

}
