package com.github.lkq.timeron.measure;

public class InvocationTimer {

    private final String name;
    private long totalTime = 0;
    private long invocationCount = 0;

    public InvocationTimer(String name) {
        this.name = name;
    }

    public void record(long startTime, long stopTime) {
        if (stopTime > startTime) {
            invocationCount++;
            totalTime += stopTime - startTime;
        }
    }

    public String name() {
        return name;
    }
}
