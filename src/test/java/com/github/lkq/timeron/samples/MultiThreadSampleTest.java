package com.github.lkq.timeron.samples;

import com.github.lkq.timeron.Timer;
import com.github.lkq.timeron.measure.TimeRecorder;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.Customization;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.comparator.CustomComparator;

import java.math.BigDecimal;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class MultiThreadSampleTest {

    public static Logger logger = Logger.getLogger(MultiThreadSampleTest.class.getSimpleName());

    private final ExecutorService executor = Executors.newFixedThreadPool(10);

    @Test
    void canRunInMultiThreadEnvironment() throws JSONException, InterruptedException {

        Timer timer = new Timer();
        Mother motherInterceptor = timer.intercept(Mother.class);
        Father fatherInterceptor = timer.intercept(Father.class);

        timer.measure(motherInterceptor.declaredInMother(""));
        timer.measure(fatherInterceptor.declaredInFather(""));

        TimeRecorder motherMethodRecorder = new TimeRecorder();
        TimeRecorder fatherMethodRecorder = new TimeRecorder();
        Son albert = timer.on(new Son(motherMethodRecorder, fatherMethodRecorder));
        Son ben = timer.on(new Son(motherMethodRecorder, fatherMethodRecorder));

        for (int i = 0; i < 20; i++) {
            executor.submit(() -> {
                for (int j = 0; j < 10; j++) {
                    String name = Thread.currentThread().getName();
                    logger.info(name + " for albert: " + albert.declaredInMother("test"));
                    logger.info(name + " for ben: " + ben.declaredInFather("test"));
                }
            });
        }

        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.SECONDS);

        logger.info("pure time for declaredInMother: " + motherMethodRecorder);
        logger.info("pure time for declaredInFather: " + fatherMethodRecorder);
        String stats = timer.getStats();
        logger.info("actual:" + stats);
        JSONAssert.assertEquals("[{\"com.github.lkq.timeron.samples.MultiThreadSampleTest$Son.declaredInMother(String)\":{\"total\":12345,\"count\":200,\"avg\":1234}}," +
                        "{\"com.github.lkq.timeron.samples.MultiThreadSampleTest$Son.declaredInFather(String)\":{\"total\":12345,\"count\":200,\"avg\":1234}}]", stats,
                new CustomComparator(JSONCompareMode.STRICT,
                        new Customization("[0].com.github.lkq.timeron.samples.MultiThreadSampleTest$Son.declaredInMother(String).total", (o1, o2) ->
                                new BigDecimal((int) o2).setScale(2).divide(new BigDecimal(motherMethodRecorder.total()), BigDecimal.ROUND_HALF_EVEN).doubleValue() < 1.2),
                        new Customization("[0].com.github.lkq.timeron.samples.MultiThreadSampleTest$Son.declaredInMother(String).avg", (o1, o2) ->
                                new BigDecimal((int) o2).setScale(2).divide(new BigDecimal(motherMethodRecorder.avg()), BigDecimal.ROUND_HALF_EVEN).doubleValue() < 1.2),
                        new Customization("[1].com.github.lkq.timeron.samples.MultiThreadSampleTest$Son.declaredInFather(String).total", (o1, o2) ->
                                new BigDecimal((int) o2).setScale(2).divide(new BigDecimal(fatherMethodRecorder.total()), BigDecimal.ROUND_HALF_EVEN).doubleValue() < 1.2),
                        new Customization("[1].com.github.lkq.timeron.samples.MultiThreadSampleTest$Son.declaredInFather(String).avg", (o1, o2) ->
                                new BigDecimal((int) o2).setScale(2).divide(new BigDecimal(fatherMethodRecorder.avg()), BigDecimal.ROUND_HALF_EVEN).doubleValue() < 1.2)
                ));
    }

    private static void delay(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) { }
    }

    public interface Mother {
        String declaredInMother(String arg);
    }

    public interface Father {
        String declaredInFather(String arg);
    }

    public static class Son implements Mother, Father {

        private Random random = new Random(System.currentTimeMillis());
        TimeRecorder motherRecorder;
        TimeRecorder fatherRecorder;

        public Son(TimeRecorder motherRecorder, TimeRecorder fatherRecorder) {
            this.motherRecorder = motherRecorder;
            this.fatherRecorder = fatherRecorder;
        }

        @Override
        public String declaredInMother(String arg) {
            long start = System.nanoTime();
            delay(random.nextInt(20));
            motherRecorder.record(start, System.nanoTime());
            return "Son.declaredInMother-" + arg;
        }

        @Override
        public String declaredInFather(String arg) {
            long start = System.nanoTime();
            delay(random.nextInt(20));
            fatherRecorder.record(start, System.nanoTime());
            return "Son.declaredInFather-" + arg;
        }
    }
}
