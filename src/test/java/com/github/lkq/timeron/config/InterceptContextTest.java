package com.github.lkq.timeron.config;

import com.github.lkq.timeron.hierarchy.lv3.Son;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

class InterceptContextTest {


    @Mock
    private Interceptor interceptor;

    @BeforeEach
    void setUp() {
        initMocks(this);
    }

    @Test
    void canStubMethodCalls() throws Throwable {

        InterceptContext context = new InterceptContext(interceptor);
        Son son = context.intercept(Son.class);

        son.tagInSon(null);

        context.finishInterception();

        verify(interceptor, times(1)).intercept(any(), eq(Son.class.getMethod("tagInSon", String.class)), any(), any());
        verify(interceptor, times(1)).finishInterception();
    }
}