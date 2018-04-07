package com.github.lkq.timeron.config;

import com.github.lkq.timeron.ErrorReporter;
import com.github.lkq.timeron.TimerException;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.Factory;
import net.sf.cglib.proxy.MethodInterceptor;
import org.objenesis.Objenesis;
import org.objenesis.ObjenesisStd;

public class InterceptContext {

    private CGLibInterceptorAdaptor interceptorAdaptor;
    private Objenesis objenesis = new ObjenesisStd();
    private ProxyFactory proxyFactory;

    public InterceptContext(CGLibInterceptorAdaptor interceptorAdaptor, ProxyFactory proxyFactory) {
        this.interceptorAdaptor = interceptorAdaptor;
        this.proxyFactory = proxyFactory;
    }

    public <T> T createProxy(T target) {
        return proxyFactory.create(target);
    }

    public <T> T intercept(Class<T> clz) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(clz);
        enhancer.setCallbackFilter(method -> 0);
        enhancer.setCallbackType(interceptorAdaptor.getClass());

        Class proxyClz = enhancer.createClass();

        T proxyInstance = (T) objenesis.newInstance(proxyClz);

        ((Factory) proxyInstance).setCallbacks(new MethodInterceptor[]{interceptorAdaptor});
        return proxyInstance;
    }

    public void completeIntercept() {
        try {
            interceptorAdaptor.completeIntercept();
        } catch (TimerException e) {
            ErrorReporter.missingMethodInvocation();
            throw e;
        }
    }
}
