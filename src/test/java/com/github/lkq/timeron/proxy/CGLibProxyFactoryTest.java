package com.github.lkq.timeron.proxy;

import com.github.lkq.timeron.hierarchy.lv3.Son;
import com.github.lkq.timeron.measure.TimeRecorders;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.Collections;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.MockitoAnnotations.initMocks;

class CGLibProxyFactoryTest {

    @Mock
    private TimeRecorders timeRecorders;

    @BeforeEach
    void setUp() {
        initMocks(this);
    }

    @Test
    void canCreateProxy() {
        Son proxy = new CGLibProxyFactory().create(new Son("Kingson"), Collections.emptyList());
        assertThat(proxy.tagInGrandpa("test"), is("tagInGrandpa-test"));
        assertThat(proxy.tagInSon("test"), is("tagInSon-test"));
    }
}