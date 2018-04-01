package com.github.lkq.timeron.measure;

public class InvocationTimer {

    private long totalTime = 0;
    private long invocationCount = 0;

    public void record(long startTime, long stopTime) {
        if (stopTime > startTime) {
            invocationCount++;
            totalTime += stopTime - startTime;
        }
    }
}
