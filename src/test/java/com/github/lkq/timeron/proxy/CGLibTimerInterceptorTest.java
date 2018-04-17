package com.github.lkq.timeron.proxy;

import com.github.lkq.timeron.hierarchy.lv3.Son;
import com.github.lkq.timeron.measure.TimeRecorder;
import com.github.lkq.timeron.measure.TimeRecorderFactory;
import com.github.lkq.timeron.util.ReflectionUtil;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.lang.reflect.Method;
import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

class CGLibTimerInterceptorTest {

    private CGLibTimerInterceptor interceptor;

    @Mock
    private TimeRecorderFactory recorderFactory;
    @Mock
    private TimeRecorder timeRecorder;

    @BeforeEach
    void setUp() {
        initMocks(this);
    }

    @Test
    void willMeasurePerformanceForInterceptedMethod() throws Throwable {
        Method tagInSon = Son.class.getDeclaredMethod("implInSon", String.class);
        given(recorderFactory.create(ReflectionUtil.signature(Son.class, tagInSon))).willReturn(timeRecorder);
        interceptor = new CGLibTimerInterceptor(new Son("kingson"), Arrays.asList(tagInSon), recorderFactory);

        Object retVal = interceptor.intercept(new Son("abc"){}, tagInSon, new String[]{"test"}, null);

        assertThat(retVal, CoreMatchers.is("implInSon-test"));
        verify(timeRecorder, times(1)).record(anyLong(), anyLong());
    }
}