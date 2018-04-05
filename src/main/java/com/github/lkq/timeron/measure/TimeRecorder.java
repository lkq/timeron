package com.github.lkq.timeron.measure;

public class TimeRecorder {

    private long totalTime = 0;
    private long invocationCount = 0;

    public void record(long startTime, long stopTime) {
        if (stopTime > startTime) {
            invocationCount++;
            totalTime += stopTime - startTime;
        }
    }

    public long avg() {
        if (invocationCount > 0) {
            return totalTime / invocationCount;
        }
        return -1;
    }

    public long total() {
        return totalTime;
    }

    public long count() {
        return invocationCount;
    }
}
