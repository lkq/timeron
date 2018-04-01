package com.github.lkq.timeron;

import com.github.lkq.timeron.hierarchy.lv3.Son;
import org.junit.jupiter.api.Test;

class TimerTest {
    @Test
    void canSetupMethodCallInterception() {
        Timer timer = new Timer();
        Son son = timer.intercept(Son.class);
        timer.measure(son.tagInSon(""));
    }
}