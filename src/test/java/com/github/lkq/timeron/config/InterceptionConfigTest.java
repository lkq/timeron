package com.github.lkq.timeron.config;

import com.github.lkq.timeron.TimerException;
import com.github.lkq.timeron.hierarchy.lv2.Mother;
import com.github.lkq.timeron.hierarchy.lv3.Son;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.List;

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

    @Disabled("pending implementation")
    @Test
    void canSetupMethodInterception() throws Throwable {
        Method tagInSon = Son.class.getMethod("tagInSon", String.class);
        interceptionConfig.startIntercept(tagInSon);
        interceptionConfig.completeIntercept();

        assertTrue(false, "implementation changed");

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

    @Test
    void canGetInterceptedMethodsByClass() throws NoSuchMethodException {
        interceptionConfig.startIntercept(Son.class.getMethod("tagInSon", String.class));
        interceptionConfig.completeIntercept();
        interceptionConfig.startIntercept(Son.class.getMethod("tagInMother", String.class));
        interceptionConfig.completeIntercept();

        List<Method> interceptedMethods = interceptionConfig.getInterceptedMethods(Son.class);
        assertThat(interceptedMethods.size(), is(1));
        assertThat(interceptedMethods.get(0), is(Son.class.getMethod("tagInSon", String.class)));
    }

    @Test
    void canGetInterceptedAbstractSuperMethodsByClass() throws NoSuchMethodException {
        interceptionConfig.startIntercept(Son.class.getMethod("tagInSon", String.class));
        interceptionConfig.completeIntercept();
        interceptionConfig.startIntercept(Son.class.getMethod("tagInMother", String.class));
        interceptionConfig.completeIntercept();

        List<Method> interceptedMethods = interceptionConfig.getInterceptedMethods(Mother.class);
        assertThat(interceptedMethods.size(), is(1));
        assertThat(interceptedMethods.get(0), is(Son.class.getMethod("tagInMother", String.class)));
    }
}