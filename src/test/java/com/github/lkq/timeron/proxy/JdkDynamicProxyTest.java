package com.github.lkq.timeron.proxy;

import com.github.lkq.timeron.Timer;
import com.github.lkq.timeron.family.Father;
import com.github.lkq.timeron.family.Son;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class JdkDynamicProxyTest {

    @Test
    void canProxyIfInterfaceMethodAnnotatedWithTimer() {
        Father proxy = new Timer().on(new Son("Kingson"));
        assertThat(proxy.fatherAnnotated("fatherAnnotated"), is("fatherAnnotated"));
        assertThat(proxy.grandpaAnnotated("grandpaAnnotated"), is("grandpaAnnotated"));
    }
}