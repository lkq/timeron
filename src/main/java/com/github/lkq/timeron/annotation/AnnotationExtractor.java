package com.github.lkq.timeron.annotation;

import java.lang.reflect.Method;
import java.util.*;

public class AnnotationExtractor {
    public Map<Class<?>, Set<Method>> extractAnnotatedMethods(Class<?> clz, Class<Timer> annoClz) {
        HashMap<Class<?>, Set<Method>> measuredClzMethods = new HashMap<>();
        while (clz != null) {
            List<Method> measuredMethods = new ArrayList<>();
            Method[] declaredMethods = clz.getDeclaredMethods();
            for (Method method : declaredMethods) {
                Timer annotation = method.getDeclaredAnnotation(annoClz);
                if (annotation != null) {
                }
            }

        }
        return measuredClzMethods;
    }
}
