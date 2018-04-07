package com.github.lkq.timeron.config;

import com.github.lkq.timeron.hierarchy.lv3.Son;
import com.github.lkq.timeron.measure.TimeRecorders;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

class InterceptContextTest {


    @Mock
    private CGLibInterceptorAdaptor interceptorAdaptor;
    @Mock
    private TimeRecorders timeRecorders;
    @Mock
    private ProxyFactory proxyFactory;
    @Mock
    private Son proxy;

    @BeforeEach
    void setUp() {
        initMocks(this);
    }

    @Test
    void canStubMethodCalls() throws Throwable {

        InterceptContext context = new InterceptContext(interceptorAdaptor, proxyFactory);
        Son son = context.intercept(Son.class);

        son.tagInSon(null);

        context.completeIntercept();

        verify(interceptorAdaptor, times(1)).intercept(any(), eq(Son.class.getMethod("tagInSon", String.class)), any(), any());
        verify(interceptorAdaptor, times(1)).completeIntercept();
    }

    @Test
    void canCreateProxy() {

        InterceptContext context = new InterceptContext(interceptorAdaptor, proxyFactory);
        Son kingson = new Son("kingson");
        given(proxyFactory.create(kingson)).willReturn(proxy);
        Son actualProxy = context.createProxy(kingson);

        assertThat(actualProxy, is(proxy));

    }
}