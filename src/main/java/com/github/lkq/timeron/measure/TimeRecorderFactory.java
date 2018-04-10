package com.github.lkq.timeron.measure;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class TimeRecorderFactory {
    private Map<Method, TimeRecorder> timeRecorders = new HashMap<>();

    public TimeRecorder create(Method method) {
        return timeRecorders.computeIfAbsent(method, (k) -> new TimeRecorder());
    }

    public Map<Method, TimeRecorder> getTimers() {
        return timeRecorders;
    }
}
