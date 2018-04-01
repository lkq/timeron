package com.github.lkq.timeron.config;

import com.github.lkq.timeron.hierarchy.lv3.Son;
import com.github.lkq.timeron.measure.InvocationTimer;
import com.github.lkq.timeron.measure.TimerConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.lang.reflect.Method;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

class InterceptorTest {

    @Mock
    private TimerConfig timerConfig;

    @BeforeEach
    void setUp() {
        initMocks(this);
    }

    @Test
    void canCreateTimerOnFinishInterception() throws Throwable {
        Interceptor interceptor = new Interceptor(timerConfig);
        Method tagInSon = Son.class.getMethod("tagInSon", String.class);
        interceptor.intercept(null, tagInSon, null, null);
        interceptor.finishInterception();

        verify(timerConfig, times(1)).addTimer(eq(tagInSon), any(InvocationTimer.class));

    }
}