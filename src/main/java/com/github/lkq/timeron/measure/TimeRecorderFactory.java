package com.github.lkq.timeron.measure;

import java.util.HashMap;
import java.util.Map;

public class TimeRecorderFactory {
    private Map<String, TimeRecorder> timeRecorders = new HashMap<>();

    public TimeRecorder create(String method) {
        return timeRecorders.computeIfAbsent(method, (k) -> new TimeRecorder());
    }

    public Map<String, TimeRecorder> getTimers() {
        return timeRecorders;
    }
}
