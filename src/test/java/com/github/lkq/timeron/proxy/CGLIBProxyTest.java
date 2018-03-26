package com.github.lkq.timeron.proxy;

import com.github.lkq.timeron.family.Son;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class CGLIBProxyTest {

    @Test
    void canCreateProxy() {
        Son proxy = new CGLIBProxy<>(new Son("Kingson")).getProxy();
        assertThat(proxy.sonAnnotated("sonAnnotated"), is("sonAnnotated"));
    }
}