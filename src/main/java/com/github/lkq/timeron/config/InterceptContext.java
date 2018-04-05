package com.github.lkq.timeron.config;

import com.github.lkq.timeron.ErrorReporter;
import com.github.lkq.timeron.TimerException;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.Factory;
import net.sf.cglib.proxy.MethodInterceptor;
import org.objenesis.Objenesis;
import org.objenesis.ObjenesisStd;

public class InterceptContext {

    private Interceptor interceptor;
    private Objenesis objenesis = new ObjenesisStd();
    private ProxyFactory proxyFactory;

    public InterceptContext(Interceptor interceptor, ProxyFactory proxyFactory) {
        this.interceptor = interceptor;
        this.proxyFactory = proxyFactory;
    }

    public <T> T createProxy(T target) {
        return proxyFactory.create(target);
    }

    public <T> T intercept(Class<T> clz) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(clz);
        enhancer.setCallbackFilter(method -> 0);
        enhancer.setCallbackType(interceptor.getClass());

        Class proxyClz = enhancer.createClass();

        T proxyInstance = (T) objenesis.newInstance(proxyClz);

        ((Factory) proxyInstance).setCallbacks(new MethodInterceptor[]{interceptor});
        return proxyInstance;
    }

    public void finishInterception() {
        try {
            interceptor.finishInterception();
        } catch (TimerException e) {
            new ErrorReporter().missingMethodInvocation();
            throw e;
        }
    }
}
