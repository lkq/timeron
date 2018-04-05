package com.github.lkq.timeron;

import com.github.lkq.timeron.hierarchy.lv3.Son;
import com.github.lkq.timeron.measure.TimeRecorder;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.skyscreamer.jsonassert.JSONAssert;

import java.lang.reflect.Method;
import java.util.HashMap;

import static org.mockito.BDDMockito.given;
import static org.mockito.MockitoAnnotations.initMocks;

class ReportBuilderTest {

    private ReportBuilder reportBuilder;
    @Mock
    private TimeRecorder tagInSonTimer;

    @BeforeEach
    void setUp() {
        initMocks(this);
        reportBuilder = new ReportBuilder();
    }

    @Test
    void canBuildReportFromTimers() throws NoSuchMethodException, JSONException {
        HashMap<Method, TimeRecorder> timers = new HashMap<>();
        timers.put(Son.class.getMethod("tagInSon", String.class), tagInSonTimer);
        given(tagInSonTimer.avg()).willReturn(1234L);
        given(tagInSonTimer.total()).willReturn(2345L);
        given(tagInSonTimer.count()).willReturn(3L);
        String stats = reportBuilder.buildJSON(timers);

        JSONAssert.assertEquals("[{" +
                "\"com.github.lkq.timeron.hierarchy.lv3.Son.tagInSon\":{" +
                "\"avg\":1234," +
                "\"total\":2345," +
                "\"count\":3" +
                "}" +
                "}]", stats, true);
    }
}