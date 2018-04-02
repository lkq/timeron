package com.github.lkq.timeron;

import com.github.lkq.timeron.hierarchy.lv3.Son;
import com.github.lkq.timeron.measure.InvocationTimer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.lang.reflect.Method;
import java.util.HashMap;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.MockitoAnnotations.initMocks;

class ReportBuilderTest {

    private ReportBuilder reportBuilder;
    @Mock
    private InvocationTimer tagInSonTimer;

    @BeforeEach
    void setUp() {
        initMocks(this);
        reportBuilder = new ReportBuilder();
    }

    @Test
    void canBuildReportFromTimers() throws NoSuchMethodException {
        HashMap<Method, InvocationTimer> timers = new HashMap<>();
        timers.put(Son.class.getMethod("tagInSon", String.class), tagInSonTimer);
        given(tagInSonTimer.avg()).willReturn(1234L);
        String stats = reportBuilder.buildJSON(timers);

        assertThat(stats, is("[{" +
                "\"com.github.lkq.timeron.hierarchy.lv3.Son.tagInSon\":{" +
                "\"avg\":1234" +
                "}" +
                "}]"));
    }
}