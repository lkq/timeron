package com.github.lkq.timeron.proxy;

import com.github.lkq.timeron.measure.InvocationTimer;
import com.github.lkq.timeron.measure.InvocationTimers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.lang.reflect.Method;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

class JDKInvocationHandlerTest {

    private JDKInvocationHandler handler;
    @Mock
    private InvocationTimers invocationTimers;
    @Mock
    private InvocationTimer invocationTimer;

    static class TestClass {
        String measuredMethod(String arg) {
            return arg;
        }
    }

    @BeforeEach
    void setUp() {
        initMocks(this);
    }

    @Test
    void willPassThroughInvocationIfNoTimerDefined() throws Throwable {

        handler = new JDKInvocationHandler(new TestClass(), invocationTimers);
        Method measuredMethod = TestClass.class.getDeclaredMethod("measuredMethod", String.class);

        given(invocationTimers.get(measuredMethod)).willReturn(null);

        Object retVal = handler.invoke(null, measuredMethod, new Object[]{"arg"});

        assertThat(retVal, is("arg"));
    }

    @Test
    void willMeasureInvocationIfTimerDefined() throws Throwable {

        handler = new JDKInvocationHandler(new TestClass(), invocationTimers);
        Method measuredMethod = TestClass.class.getDeclaredMethod("measuredMethod", String.class);

        given(invocationTimers.get(measuredMethod)).willReturn(invocationTimer);

        Object retVal = handler.invoke(null, measuredMethod, new Object[]{"arg"});

        assertThat(retVal, is("arg"));
        verify(invocationTimer, times(1)).record(anyLong(), anyLong());
    }
}