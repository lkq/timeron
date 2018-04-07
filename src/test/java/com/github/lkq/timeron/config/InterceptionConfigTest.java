package com.github.lkq.timeron.config;

import com.github.lkq.timeron.TimerException;
import com.github.lkq.timeron.hierarchy.lv3.Son;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.MockitoAnnotations.initMocks;

class InterceptionConfigTest {

    private InterceptionConfig interceptionConfig;

    @BeforeEach
    void setUp() {
        initMocks(this);
        interceptionConfig = new InterceptionConfig();
    }

    @Test
    void canSetupMethodInterception() throws Throwable {
        Method tagInSon = Son.class.getMethod("tagInSon", String.class);
        interceptionConfig.startIntercept(tagInSon);
        interceptionConfig.completeIntercept();

        assertTrue(interceptionConfig.isMethodIntercepted(tagInSon));

    }

    @Test
    void willThrowExceptionWhenTryToFinishInterceptionWithoutStarted() throws NoSuchMethodException {
        TimerException exception = Assertions.assertThrows(TimerException.class, () -> interceptionConfig.completeIntercept());
        assertThat(exception.getMessage(), is("no interception in progress"));
    }

    @Test
    void willThrowExceptionWhenTryToStartInterceptionWhenAlreadyInProgress() throws Throwable {
        Method tagInSon = Son.class.getMethod("tagInSon", String.class);
        interceptionConfig.startIntercept(tagInSon);
        TimerException exception = Assertions.assertThrows(TimerException.class, () -> interceptionConfig.startIntercept(tagInSon));
        assertThat(exception.getMessage(), is("unfinished interception detected on public java.lang.String com.github.lkq.timeron.hierarchy.lv3.Son.tagInSon(java.lang.String)"));
    }
}