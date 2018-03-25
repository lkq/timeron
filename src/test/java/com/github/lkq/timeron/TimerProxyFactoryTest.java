package com.github.lkq.timeron;

import com.github.lkq.timeron.annotation.AnnotationFinder;
import com.github.lkq.timeron.annotation.Timer;
import com.github.lkq.timeron.family.PetDog;
import com.github.lkq.timeron.proxy.CGLIBProxy;
import com.github.lkq.timeron.proxy.JDKDynamicProxy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.MockitoAnnotations.initMocks;

class TimerProxyFactoryTest {

    private TimerProxyFactory proxyFactory;
    @Mock
    private AnnotationFinder annotationFinder;

    @BeforeEach
    void setUp() {
        initMocks(this);
        proxyFactory = new TimerProxyFactory(annotationFinder);
    }

    @Test
    void canCreateJDKDynamicProxy() {
        given(annotationFinder.annotatedMethodPresentInClassHierarchy(PetDog.class, Timer.class)).willReturn(false);
        TimerProxy<PetDog> proxy = proxyFactory.createProxy(new PetDog());
        assertTrue(proxy instanceof JDKDynamicProxy);
    }

    @Test
    void canCreateCGLIBProxy() {
        given(annotationFinder.annotatedMethodPresentInClassHierarchy(PetDog.class, Timer.class)).willReturn(true);
        TimerProxy<PetDog> proxy = proxyFactory.createProxy(new PetDog());
        assertTrue(proxy instanceof CGLIBProxy);
    }
}