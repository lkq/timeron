package com.github.lkq.timeron.intercept;

import com.github.lkq.timeron.ErrorReporter;
import com.github.lkq.timeron.TimerException;
import com.github.lkq.timeron.thirdparty.cglib.proxy.Enhancer;
import com.github.lkq.timeron.thirdparty.cglib.proxy.Factory;
import com.github.lkq.timeron.thirdparty.cglib.proxy.MethodInterceptor;
import com.github.lkq.timeron.thirdparty.cglib.proxy.MethodProxy;
import com.github.lkq.timeron.thirdparty.objenesis.Objenesis;
import com.github.lkq.timeron.thirdparty.objenesis.ObjenesisStd;

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

    public <T> T createInterceptor(Class<T> clz) {
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
        Class<?> clz = getInterceptingClass(obj, method);
        interceptor.startIntercept(clz, method);
        return null;
    }

    private Class<?> getInterceptingClass(Object obj, Method method) {

        Class<?> clz = obj.getClass();
        if (isProxyClass(clz)) {
            Class<?> superClz = clz.getSuperclass();
            if (superClz == null || superClz.equals(Object.class)) {
                // intercepting interface
                Class<?>[] interfaces = clz.getInterfaces();
                for (Class<?> iface : interfaces) {
                    if (method.getDeclaringClass().isAssignableFrom(iface)) {
                        clz = iface;
                        break;
                    }
                }
            } else {
                // intercepting class
                clz = superClz;
            }
        }
        return clz;
    }

    private boolean isProxyClass(Class clz) {
        if (clz.getName().contains("$$EnhancerByCGLIB$$")) {
            return true;
        }
        return false;
    }
}
