package com.github.lkq.timeron.measure;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class TimerConfig {
    private Map<Method, InvocationTimer> timers = new HashMap<>();

    public InvocationTimer getTimer(Method method) {
        return timers.get(method);
    }

    public void addTimer(Method method, InvocationTimer invocationTimer) {
        this.timers.put(method, invocationTimer);
    }

    public Map<Method, InvocationTimer> getTimers() {
        return timers;
    }
}
