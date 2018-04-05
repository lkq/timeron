package com.github.lkq.timeron.measure;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class TimeRecorders {
    private Map<Method, TimeRecorder> timers = new HashMap<>();

    public TimeRecorder getTimer(Method method) {
        return timers.get(method);
    }

    public void addTimer(Method method, TimeRecorder timeRecorder) {
        this.timers.put(method, timeRecorder);
    }

    public Map<Method, TimeRecorder> getTimers() {
        return timers;
    }

}
