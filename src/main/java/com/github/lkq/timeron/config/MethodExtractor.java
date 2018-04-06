package com.github.lkq.timeron.config;

import com.github.lkq.timeron.ErrorReporter;
import com.github.lkq.timeron.TimerException;

import java.lang.reflect.Method;
import java.util.*;

public class MethodExtractor {
    private Map<Class, List<Method>> interceptedMethods = new HashMap<>();

    public List<Method> extractInterceptedMethods(Class clz) {
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

    private List<Method> extractInterceptedMethodsFromClassHierarchy(Class clz) {
        return new ArrayList<>();
    }
}
