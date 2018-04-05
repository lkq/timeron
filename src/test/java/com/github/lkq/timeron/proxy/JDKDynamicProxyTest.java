package com.github.lkq.timeron.proxy;

import com.github.lkq.timeron.hierarchy.lv2.Father;
import com.github.lkq.timeron.hierarchy.lv3.Son;
import com.github.lkq.timeron.measure.TimeRecorders;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.MockitoAnnotations.initMocks;

class JDKDynamicProxyTest {

    @Mock
    private TimeRecorders timeRecorders;

    @BeforeEach
    void setUp() {
        initMocks(this);
    }

    @Test
    void canProxyIfInterfaceMethodAnnotatedWithTimer() {
        Father proxy = new JDKDynamicProxy<>(new Son("Kingson"), timeRecorders).create();
        assertThat(proxy.tagInFather("test"), is("tagInFather-test"));
        assertThat(proxy.tagInGrandpa("test"), is("tagInGrandpa-test"));
    }
}