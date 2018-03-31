package com.github.lkq.timeron;

import com.github.lkq.timeron.annotation.Timer;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.logging.Logger;

public class ReflectionPlayground {
    private static Logger logger = Logger.getLogger(ReflectionPlayground.class.getName());
    @Test
    void printBridgeMethodInfomation() throws NoSuchMethodException {
        Method callMe = TestImplementation.class.getDeclaredMethod("callMe", Integer.class);
        Method callMe1 = TestImplementation.class.getDeclaredMethod("callMe", String.class, String.class);
        System.out.println(callMe.hashCode());
        System.out.println(callMe1.hashCode());
        System.out.println(callMe.equals(callMe1));
        HashMap<Method, String> map = new HashMap<>();
        map.put(callMe, "abc");
        map.put(callMe1, "def");
        System.out.println(map.get(callMe));
        System.out.println(map.get(callMe1));
        for (Method method : TestImplementation.class.getDeclaredMethods()) {
            logger.info("found declared method: " + method.toString());
            logger.info("annotated with: " + method.getDeclaredAnnotation(Timer.class));
        }
    }

    interface TestInterface<T> {
        void callMe(T name);

        void callMe(String name, String title);
    }

    static class TestImplementation implements TestInterface<Integer> {
        @Timer(name = "callMe")
        @Override
        public void callMe(Integer name) {
            logger.info("calling me " + name);
        }

        @Override
        public void callMe(String name, String title) {

        }
    }

}
