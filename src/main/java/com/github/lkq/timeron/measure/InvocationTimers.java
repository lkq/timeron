package com.github.lkq.timeron.measure;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class InvocationTimers {
    private Map<Method, InvocationTimer> timers = new HashMap<>();

    public InvocationTimer get(Method method) {
        return timers.get(method);
    }
}
