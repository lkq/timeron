package com.github.lkq.timeron.proxy;

import com.github.lkq.timeron.family.Father;
import com.github.lkq.timeron.family.Son;
import com.github.lkq.timeron.measure.InvocationTimers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.MockitoAnnotations.initMocks;

class JDKDynamicProxyTest {

    @Mock
    private InvocationTimers invocationTimers;

    @BeforeEach
    void setUp() {
        initMocks(this);
    }

    @Test
    void canProxyIfInterfaceMethodAnnotatedWithTimer() {
        Father proxy = new JDKDynamicProxy<>(new Son("Kingson"), invocationTimers).getProxy();
        assertThat(proxy.fatherAnnotated("fatherAnnotated"), is("fatherAnnotated"));
        assertThat(proxy.grandpaAnnotated("grandpaAnnotated"), is("grandpaAnnotated"));
    }
}