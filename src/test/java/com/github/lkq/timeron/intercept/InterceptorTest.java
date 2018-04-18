package com.github.lkq.timeron.intercept;

import com.github.lkq.timeron.TimerException;
import com.github.lkq.timeron.hierarchy.lv2.Mother;
import com.github.lkq.timeron.hierarchy.lv3.Son;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

class InterceptorTest {

    private Interceptor interceptor;
    @Mock
    private MethodExtractor methodExtractor;
    @Mock
    private List expectedMeasuredMethods;

    @BeforeEach
    void setUp() {
        initMocks(this);
        interceptor = new Interceptor(methodExtractor);
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

        given(methodExtractor.extract(eq(Son.class), any(Map.class))).willReturn(expectedMeasuredMethods);

        List<MeasuredMethod> measuredMethods = interceptor.getMeasuredMethods(Son.class);
        assertThat(measuredMethods, is(expectedMeasuredMethods));

        ArgumentCaptor<Map> methodArgumentCaptor = ArgumentCaptor.forClass(Map.class);
        verify(methodExtractor, times(1)).extract(eq(Son.class), methodArgumentCaptor.capture());
        Map<Class, List<Method>> methodMap = methodArgumentCaptor.getValue();
        assertThat(methodMap.size(), is(2));
        assertThat(methodMap.get(Son.class).get(0), is(Son.class.getDeclaredMethod("implInSon", String.class)));
        assertThat(methodMap.get(Mother.class).get(0), is(Mother.class.getDeclaredMethod("implInMother", String.class)));
    }

    @Test
    void canGetInterceptedAbstractSuperMethodsByClass() throws NoSuchMethodException {
        interceptor.startIntercept(Son.class.getDeclaredMethod("implInSon", String.class));
        interceptor.completeIntercept();
        interceptor.startIntercept(Mother.class.getDeclaredMethod("implInMother", String.class));
        interceptor.completeIntercept();
        interceptor.startIntercept(Mother.class.getDeclaredMethod("declaredInMotherImplInChild", String.class));
        interceptor.completeIntercept();

        given(methodExtractor.extract(eq(Mother.class), any(Map.class))).willReturn(expectedMeasuredMethods);

        List<MeasuredMethod> measuredMethods = interceptor.getMeasuredMethods(Mother.class);
        assertThat(measuredMethods, is(expectedMeasuredMethods));

        ArgumentCaptor<Map> methodArgumentCaptor = ArgumentCaptor.forClass(Map.class);
        verify(methodExtractor, times(1)).extract(eq(Mother.class), methodArgumentCaptor.capture());
        Map<Class, List<Method>> methodMap = methodArgumentCaptor.getValue();

        assertThat(methodMap.size(), is(2));
        assertThat(methodMap.get(Son.class).get(0), is(Son.class.getDeclaredMethod("implInSon", String.class)));
        assertThat(methodMap.get(Mother.class).get(0), is(Mother.class.getDeclaredMethod("implInMother", String.class)));
        assertThat(methodMap.get(Mother.class).get(1), is(Mother.class.getDeclaredMethod("declaredInMotherImplInChild", String.class)));
    }
}