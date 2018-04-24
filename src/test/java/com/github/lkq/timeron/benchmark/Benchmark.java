package com.github.lkq.timeron.benchmark;

import com.github.lkq.timeron.Timer;
import com.github.lkq.timeron.measure.TimeRecorder;

import java.util.Random;

public class Benchmark {

    static Random random = new Random(System.nanoTime());

    public static void main(String[] args) {
        Benchmark benchmark = new Benchmark();
        benchmark.benchmarkForCPUHeavyMethod();
        benchmark.benchmarkForIOHeavyMethod();
    }

    public void benchmarkForCPUHeavyMethod() {
        runBenchmark(new CPUHeavyTask(), 100);
    }

    public void benchmarkForIOHeavyMethod() {
        runBenchmark(new IOHeavyTask(), 1000);
    }

    private void runBenchmark(Task target, int repeatCount) {
        Timer timer = new Timer();
        Task interceptor = timer.interceptor(Task.class);
        timer.measure(() -> interceptor.runTask("", 1));

        Task task = timer.on(target);

        for (int i = 0; i < repeatCount; i++) {
            task.runTask("runBenchmark", 1);
        }
        String stats = timer.getStats();
        System.out.println(stats);
        System.out.println(task.timeRecorder());
    }

    public interface Task {
        void runTask(String id, int count);
        TimeRecorder timeRecorder();
    }

    public static class CPUHeavyTask implements Task {
        TimeRecorder timeRecorder = new TimeRecorder();
        @Override
        public void runTask(String id, int count) {
            // calculate double multiplication for 1 million times
            long startTime = System.nanoTime();
            double value;
            for (int i = 0; i < 1000000; i++) {
                value = random.nextDouble() * random.nextDouble();
            }
            timeRecorder.record(startTime, System.nanoTime());
        }

        @Override
        public TimeRecorder timeRecorder() {
            return timeRecorder;
        }
    }

    public static class IOHeavyTask implements Task {
        TimeRecorder timeRecorder = new TimeRecorder();
        @Override
        public void runTask(String id, int count) {
            long startTime = System.nanoTime();
            try {
                Thread.sleep(random.nextInt(20));
            } catch (InterruptedException e) { }
            timeRecorder.record(startTime, System.nanoTime());
        }

        @Override
        public TimeRecorder timeRecorder() {
            return timeRecorder;
        }
    }
}
