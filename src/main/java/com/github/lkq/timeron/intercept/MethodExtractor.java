package com.github.lkq.timeron.intercept;

import com.github.lkq.timeron.ErrorReporter;
import com.github.lkq.timeron.TimerException;

import java.lang.reflect.Method;
import java.util.*;

public class MethodExtractor {

    public List<Method> interceptedMethods(Class clz, Map<Class, List<Method>> interceptedMethods) {
        if (interceptedMethods.containsKey(clz)) {
            return interceptedMethods.get(clz);
        } else {
            List<Method> methods = extractInterceptedMethodsFromClassHierarchy(clz);
            if (methods.size() <= 0) {
                ErrorReporter.missingMeasurementConfig(clz);
                throw new TimerException("no intercepted method defined for class: " + clz.getName());
            }
            interceptedMethods.put(clz, methods);
            return methods;
        }
    }

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

    private List<Method> extractInterceptedMethodsFromClassHierarchy(Class clz) {
        return new ArrayList<>();
    }
}
