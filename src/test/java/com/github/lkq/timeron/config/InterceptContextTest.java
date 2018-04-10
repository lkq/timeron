package com.github.lkq.timeron.config;

import com.github.lkq.timeron.hierarchy.lv3.Son;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.lang.reflect.Method;
import java.util.Collections;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

class InterceptContextTest {


    @Mock
    private InterceptionConfig interceptionConfig;
    @Mock
    private ProxyFactory proxyFactory;
    @Mock
    private Son proxy;
    private InterceptContext context;

    @BeforeEach
    void setUp() {
        initMocks(this);
        context = new InterceptContext(interceptionConfig, proxyFactory);
    }

    @Test
    void canStubMethodCalls() throws Throwable {

        Son son = context.intercept(Son.class);

        son.tagInSon(null);

        context.completeIntercept();

        verify(interceptionConfig, times(1)).startIntercept(Son.class.getMethod("tagInSon", String.class));
        verify(interceptionConfig, times(1)).completeIntercept();
    }

    @Test
    void canCreateProxy() {

        InterceptContext context = new InterceptContext(interceptionConfig, proxyFactory);
        Son kingson = new Son("kingson");
        given(proxyFactory.create(kingson, Collections.emptyList())).willReturn(proxy);
        Son actualProxy = context.createProxy(kingson);

        assertThat(actualProxy, is(proxy));

    }

    @Test
    void willStartInterceptionWhenInterceptMethodCalled() throws Throwable {
        Method tagInSon = Son.class.getMethod("tagInSon", String.class);
        context.intercept(null, tagInSon, null, null);
        context.completeIntercept();

        verify(interceptionConfig, times(1)).startIntercept(tagInSon);
    }

    @Test
    void willCompleteInterceptionWhenInterceptMethodCalled() throws Throwable {
        Method tagInSon = Son.class.getMethod("tagInSon", String.class);
        context.intercept(null, tagInSon, null, null);
        context.completeIntercept();

        verify(interceptionConfig, times(1)).completeIntercept();
    }
}