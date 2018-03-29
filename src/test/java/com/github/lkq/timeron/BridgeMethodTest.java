package com.github.lkq.timeron;

import com.github.lkq.timeron.annotation.Timer;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.logging.Logger;

public class BridgeMethodTest {
    private static Logger logger = Logger.getLogger(BridgeMethodTest.class.getName());
    @Test
    void printBridgeMethodInfomation() throws NoSuchMethodException {
        for (Method method : IestImplementation.class.getDeclaredMethods()) {
            logger.info("found declared method: " + method.toString());
            logger.info("annotated with: " + method.getDeclaredAnnotation(Timer.class));
        }
    }

    interface TestInterface<T> {
        void callMe(T name);
    }

    static class IestImplementation implements TestInterface<Integer> {
        @Timer(name = "callMe")
        @Override
        public void callMe(Integer name) {
            logger.info("calling me " + name);
        }
    }

}
