package com.github.lkq.timeron;

import com.github.lkq.timeron.hierarchy.lv2.Father;
import com.github.lkq.timeron.hierarchy.lv3.Son;
import com.github.lkq.timeron.intercept.MeasuredMethod;
import com.github.lkq.timeron.thirdparty.cglib.proxy.*;
import org.junit.jupiter.api.Test;
import com.github.lkq.timeron.thirdparty.objenesis.ObjenesisStd;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

public class ReflectionPlayground {
    private static Logger logger = Logger.getLogger(ReflectionPlayground.class.getSimpleName());
    @Test
    void printBridgeMethodInfomation() throws NoSuchMethodException {
        Method callMe = TestImplementation.class.getDeclaredMethod("callMe", Integer.class);
        Method callMe1 = TestImplementation.class.getDeclaredMethod("callMe", String.class, String.class);
        System.out.println(callMe.hashCode());
        System.out.println(callMe1.hashCode());
        System.out.println(callMe.equals(callMe1));
        HashMap<Method, String> map = new HashMap<>();
        map.put(callMe, "abc");
        map.put(callMe1, "def");
        System.out.println(map.get(callMe));
        System.out.println(map.get(callMe1));
        for (Method method : TestImplementation.class.getDeclaredMethods()) {
            logger.info("found declared method: " + method.toString());
        }
    }

    @Test
    void cglibEnhancerSetInterface() {
        Son kingson = create(new Son("kingson"), Collections.emptyList());
        kingson.fromFatherTagInSon("test");
    }


    public <T> T create(T target, List<MeasuredMethod> measuredMethods) {
        try {
            Class<?> rootClass = target.getClass();

            Enhancer enhancer = new Enhancer();
            if (!rootClass.isInterface()) {
                enhancer.setSuperclass(rootClass);
            }
            enhancer.setInterfaces(new Class[]{Father.class});

            MethodInterceptor callback = new MethodInterceptor() {
                @Override
                public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
                    return method.invoke(target, args);
                }
            };
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

        ObjenesisStd objenesis = new ObjenesisStd();
        // TODO: add some cache to improve performance
        proxyInstance = objenesis.newInstance(proxyClass);

        // TODO: If objenesis doesn't work...

        ((Factory) proxyInstance).setCallbacks(callbacks);
        return proxyInstance;
    }

    interface TestInterface<T> {
        void callMe(T name);

        void callMe(String name, String title);
    }

    static class TestImplementation implements TestInterface<Integer> {
        @Override
        public void callMe(Integer name) {
            logger.info("calling me " + name);
        }

        @Override
        public void callMe(String name, String title) {

        }
    }

}
