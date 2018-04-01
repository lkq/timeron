package com.github.lkq.timeron.measure;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class TimerConfig {
    private Map<Method, InvocationTimer> timers = new HashMap<>();

    public InvocationTimer getTimer(Method method) {
        return timers.get(method);
    }
}
