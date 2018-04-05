package com.github.lkq.timeron.proxy;

import com.github.lkq.timeron.TimerException;
import com.github.lkq.timeron.measure.TimerConfig;
import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.Factory;
import org.objenesis.Objenesis;
import org.objenesis.ObjenesisStd;

public class CGLIBProxy<T> implements TimerProxy<T> {

    private T target;

    private Objenesis objenesis = new ObjenesisStd();
    private TimerConfig timerConfig;

    public CGLIBProxy(T target, TimerConfig timerConfig) {
        this.target = target;
        this.timerConfig = timerConfig;
    }

    @Override
    public T getProxy() {

        try {
            Class<?> rootClass = target.getClass();

            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(rootClass);

            CGLibMethodInterceptor callback = new CGLibMethodInterceptor(target, timerConfig);
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
