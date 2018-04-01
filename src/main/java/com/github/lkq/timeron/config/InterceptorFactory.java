package com.github.lkq.timeron.config;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.Factory;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.objenesis.Objenesis;
import org.objenesis.ObjenesisStd;

import java.lang.reflect.Method;

public class InterceptorFactory implements MethodInterceptor {

    private Objenesis objenesis = new ObjenesisStd();
    private InterceptContext context;

    public InterceptorFactory(InterceptContext context) {
        this.context = context;
    }

    public <T> T create(Class<T> clz) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(clz);
        enhancer.setCallbackFilter(method -> 0);
        enhancer.setCallbackType(this.getClass());

        Class proxyClz = enhancer.createClass();

        T proxyInstance = (T) objenesis.newInstance(proxyClz);

        ((Factory) proxyInstance).setCallbacks(new MethodInterceptor[]{this});
        return proxyInstance;
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        this.context.interceptBegin(method);
        return null;
    }
}
