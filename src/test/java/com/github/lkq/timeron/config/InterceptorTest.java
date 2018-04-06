package com.github.lkq.timeron.config;

import com.github.lkq.timeron.TimerException;
import com.github.lkq.timeron.hierarchy.lv3.Son;
import com.github.lkq.timeron.measure.TimeRecorder;
import com.github.lkq.timeron.measure.TimeRecorders;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.lang.reflect.Method;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

class InterceptorTest {

    @Mock
    private TimeRecorders timeRecorders;
    private Interceptor interceptor;

    @BeforeEach
    void setUp() {
        initMocks(this);
        interceptor = new Interceptor(timeRecorders);
    }

    @Test
    void canCreateTimerOnFinishInterception() throws Throwable {
        Method tagInSon = Son.class.getMethod("tagInSon", String.class);
        interceptor.intercept(null, tagInSon, null, null);
        interceptor.finishInterception();

        verify(timeRecorders, times(1)).addTimer(eq(tagInSon), any(TimeRecorder.class));

    }

    @Test
    void willThrowExceptionWhenTryToFinishInterceptionWithoutStarted() throws NoSuchMethodException {
        TimerException exception = Assertions.assertThrows(TimerException.class, () -> interceptor.finishInterception());
        assertThat(exception.getMessage(), is("interception not started"));
    }

    @Test
    void willThrowExceptionWhenTryToStartInterceptionWhenAlreadyInProgress() throws Throwable {
        Method tagInSon = Son.class.getMethod("tagInSon", String.class);
        interceptor.intercept(null, tagInSon, null, null);
        TimerException exception = Assertions.assertThrows(TimerException.class, () -> interceptor.intercept(null, tagInSon, null, null));
        assertThat(exception.getMessage(), is("unfinished interception detected on public java.lang.String com.github.lkq.timeron.hierarchy.lv3.Son.tagInSon(java.lang.String)"));
    }
}