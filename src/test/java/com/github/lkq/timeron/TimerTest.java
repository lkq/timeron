package com.github.lkq.timeron;

import com.github.lkq.timeron.hierarchy.lv3.Son;
import org.junit.jupiter.api.Test;

class TimerTest {
    @Test
    void canSetupMethodCallInterception() {
        Timer timer = new Timer();
        Son son = timer.interceptor(Son.class);
        timer.measure(() -> son.implInSon(""));
    }

    @Test
    void name() {
        printHierarchy(Son.class);
    }

    void printHierarchy(Class clz) {
        if (clz != null) {
            System.out.println("clz: " + clz);
            printHierarchy(clz.getSuperclass());
            for (Class iface : clz.getInterfaces()) {
                printHierarchy(iface);
            }
        } else {
            System.out.println("null");
        }
    }
}