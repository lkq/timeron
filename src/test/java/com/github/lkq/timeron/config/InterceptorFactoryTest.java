package com.github.lkq.timeron.config;

import com.github.lkq.timeron.hierarchy.lv3.Son;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

class InterceptorFactoryTest {
    @Mock
    private InterceptContext interceptContext;

    @BeforeEach
    void setUp() {
        initMocks(this);
    }

    @Test
    void canStubMethodCalls() throws NoSuchMethodException {
        InterceptorFactory factory = new InterceptorFactory(interceptContext);
        Son son = factory.create(Son.class);

        son.tagInSon(null);

        verify(interceptContext, times(1)).interceptBegin(Son.class.getMethod("tagInSon", String.class));
    }
}