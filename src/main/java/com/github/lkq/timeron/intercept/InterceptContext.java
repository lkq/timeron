package com.github.lkq.timeron.intercept;

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

    private Interceptor interceptor;
    private Objenesis objenesis = new ObjenesisStd();
    private ProxyFactory proxyFactory;

    public InterceptContext(Interceptor interceptor, ProxyFactory proxyFactory) {
        this.interceptor = interceptor;
        this.proxyFactory = proxyFactory;
    }

    public <T> T createProxy(T target) throws NoSuchMethodException {
        return proxyFactory.create(target, interceptor.getMeasuredMethods(target.getClass()));
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
            interceptor.completeIntercept();
        } catch (TimerException e) {
            ErrorReporter.missingMethodInvocation();
            throw e;
        }
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        interceptor.startIntercept(method);
        return null;
    }
}
