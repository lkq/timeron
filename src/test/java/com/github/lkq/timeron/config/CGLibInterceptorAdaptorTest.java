package com.github.lkq.timeron.config;

import com.github.lkq.timeron.hierarchy.lv3.Son;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.lang.reflect.Method;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

class CGLibInterceptorAdaptorTest {

    @Mock
    private InterceptionConfig interceptionConfig;

    private CGLibInterceptorAdaptor interceptorAdaptor;

    @BeforeEach
    void setUp() {
        initMocks(this);
        interceptorAdaptor = new CGLibInterceptorAdaptor(interceptionConfig);
    }

    @Test
    void willStartInterceptionWhenInterceptMethodCalled() throws Throwable {
        Method tagInSon = Son.class.getMethod("tagInSon", String.class);
        interceptorAdaptor.intercept(null, tagInSon, null, null);
        interceptorAdaptor.completeIntercept();

        verify(interceptionConfig, times(1)).startIntercept(tagInSon);
    }

    @Test
    void willCompleteInterceptionWhenInterceptMethodCalled() throws Throwable {
        Method tagInSon = Son.class.getMethod("tagInSon", String.class);
        interceptorAdaptor.intercept(null, tagInSon, null, null);
        interceptorAdaptor.completeIntercept();

        verify(interceptionConfig, times(1)).completeIntercept();
    }
}