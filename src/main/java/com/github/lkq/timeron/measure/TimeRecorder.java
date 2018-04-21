package com.github.lkq.timeron.measure;

import java.util.concurrent.atomic.AtomicLong;

public class TimeRecorder {

    private AtomicLong totalTime = new AtomicLong(0);
    private AtomicLong invocationCount = new AtomicLong(0);

    public void record(long startTime, long stopTime) {
        if (stopTime > startTime) {
            invocationCount.incrementAndGet();
            totalTime.addAndGet(stopTime - startTime);
        }
    }

    public long avg() {
        if (invocationCount.longValue() > 0) {
            return totalTime.longValue() / invocationCount.longValue();
        }
        return -1;
    }

    public long total() {
        return totalTime.longValue();
    }

    public long count() {
        return invocationCount.longValue();
    }

    @Override
    public String toString() {
        return "TimeRecorder{" +
                "totalTime=" + totalTime +
                ", invocationCount=" + invocationCount +
                ", avg=" + avg() +
                '}';
    }
}
