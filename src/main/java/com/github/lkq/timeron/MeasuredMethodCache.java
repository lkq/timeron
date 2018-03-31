package com.github.lkq.timeron;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MeasuredMethodCache {

    private Map<Class<?>, List<Method>> measuredMethods = new HashMap<>();

    public void put(Class<?> clz, Method method) {
        List<Method> methods = measuredMethods.computeIfAbsent(clz, k -> new ArrayList<>());
        methods.add(method);
    }

    public List<Method> get(Class<?> clz) {
        return measuredMethods.get(clz);
    }
}
