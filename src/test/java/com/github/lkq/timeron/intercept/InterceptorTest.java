package com.github.lkq.timeron.intercept;

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

class InterceptorTest {

    private Interceptor interceptor;

    @BeforeEach
    void setUp() {
        initMocks(this);
        interceptor = new Interceptor();
    }

    @Disabled("pending implementation")
    @Test
    void canSetupMethodInterception() throws Throwable {
        Method tagInSon = Son.class.getMethod("implInSon", String.class);
        interceptor.startIntercept(tagInSon);
        interceptor.completeIntercept();

        assertTrue(false, "implementation changed");

    }

    @Test
    void willThrowExceptionWhenTryToFinishInterceptionWithoutStarted() throws NoSuchMethodException {
        TimerException exception = Assertions.assertThrows(TimerException.class, () -> interceptor.completeIntercept());
        assertThat(exception.getMessage(), is("no interception in progress"));
    }

    @Test
    void willThrowExceptionWhenTryToStartInterceptionWhenAlreadyInProgress() throws Throwable {
        Method tagInSon = Son.class.getMethod("implInSon", String.class);
        interceptor.startIntercept(tagInSon);
        TimerException exception = Assertions.assertThrows(TimerException.class, () -> interceptor.startIntercept(tagInSon));
        assertThat(exception.getMessage(), is("unfinished interception detected on public java.lang.String com.github.lkq.timeron.hierarchy.lv3.Son.implInSon(java.lang.String)"));
    }

    @Test
    void canGetInterceptedMethodsByClass() throws NoSuchMethodException {
        interceptor.startIntercept(Son.class.getMethod("implInSon", String.class));
        interceptor.completeIntercept();
        interceptor.startIntercept(Son.class.getMethod("implInMother", String.class));
        interceptor.completeIntercept();

        List<Method> interceptedMethods = interceptor.getInterceptedMethods(Son.class);
        assertThat(interceptedMethods.size(), is(2));
        assertThat(interceptedMethods.get(0), is(Son.class.getMethod("implInSon", String.class)));
        assertThat(interceptedMethods.get(1), is(Son.class.getMethod("implInMother", String.class)));
    }

    @Test
    void canGetInterceptedAbstractSuperMethodsByClass() throws NoSuchMethodException {
        interceptor.startIntercept(Son.class.getMethod("implInSon", String.class));
        interceptor.completeIntercept();
        interceptor.startIntercept(Son.class.getMethod("implInMother", String.class));
        interceptor.completeIntercept();

        List<Method> interceptedMethods = interceptor.getInterceptedMethods(Mother.class);
        assertThat(interceptedMethods.size(), is(1));
        assertThat(interceptedMethods.get(0), is(Son.class.getMethod("implInMother", String.class)));
    }
}