package com.github.lkq.timeron.proxy;

import com.github.lkq.timeron.hierarchy.lv3.Son;
import com.github.lkq.timeron.measure.InvocationTimers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.MockitoAnnotations.initMocks;

class CGLIBProxyTest {

    @Mock
    private InvocationTimers invocationTimers;

    @BeforeEach
    void setUp() {
        initMocks(this);
    }

    @Test
    void canCreateProxy() {
        Son proxy = new CGLIBProxy<>(new Son("Kingson"), invocationTimers).getProxy();
        assertThat(proxy.tagInGrandpa("test"), is("tagInGrandpa-test"));
        assertThat(proxy.tagInSon("test"), is("tagInSon-test"));
    }
}