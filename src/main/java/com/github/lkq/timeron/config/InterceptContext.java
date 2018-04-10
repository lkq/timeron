package com.github.lkq.timeron.config;

import com.github.lkq.timeron.ErrorReporter;
import com.github.lkq.timeron.TimerException;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.Factory;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.objenesis.Objenesis;
import org.objenesis.ObjenesisStd;

import java.lang.reflect.Method;

public class InterceptContext implements MethodInterceptor {

    private InterceptionConfig interceptionConfig;
    private Objenesis objenesis = new ObjenesisStd();
    private ProxyFactory proxyFactory;

    public InterceptContext(InterceptionConfig interceptionConfig, ProxyFactory proxyFactory) {
        this.interceptionConfig = interceptionConfig;
        this.proxyFactory = proxyFactory;
    }

    public <T> T createProxy(T target) {
        return proxyFactory.create(target, interceptionConfig.getInterceptedMethods(target.getClass()));
    }

    public <T> T intercept(Class<T> clz) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(clz);
        enhancer.setCallbackFilter(method -> 0);
        enhancer.setCallbackType(this.getClass());

        Class proxyClz = enhancer.createClass();

        T proxyInstance = (T) objenesis.newInstance(proxyClz);

        ((Factory) proxyInstance).setCallbacks(new MethodInterceptor[]{this});
        return proxyInstance;
    }

    public void completeIntercept() {
        try {
            interceptionConfig.completeIntercept();
        } catch (TimerException e) {
            ErrorReporter.missingMethodInvocation();
            throw e;
        }
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        interceptionConfig.startIntercept(method);
        return null;
    }
}
