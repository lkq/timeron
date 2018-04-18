package com.github.lkq.timeron.intercept;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MethodExtractor {

    public List<MeasuredMethod> extract(Class<?> clz, Map<Class, List<Method>> interceptedMethods) throws NoSuchMethodException {
        List<Method> methods = new ArrayList<>();
        recursiveExtract(clz, interceptedMethods, methods);
        List<MeasuredMethod> measuredMethods = new ArrayList<>();
        for (Method method : methods) {
            measuredMethods.add(new MeasuredMethod(clz, method));
        }

        return measuredMethods;
    }

    private void recursiveExtract(Class<?> clz, Map<Class, List<Method>> interceptedMethods, List<Method> methods) {
        if (clz != null && !Object.class.equals(clz)) {
            if (interceptedMethods.containsKey(clz)) {
                methods.addAll(interceptedMethods.get(clz));
            }

            Class<?>[] interfaces = clz.getInterfaces();
            if (interfaces.length > 0) {
                for (Class<?> iface : interfaces) {
                    recursiveExtract(iface, interceptedMethods, methods);
                }
            }

            recursiveExtract(clz.getSuperclass(), interceptedMethods, methods);
        }
    }
}
