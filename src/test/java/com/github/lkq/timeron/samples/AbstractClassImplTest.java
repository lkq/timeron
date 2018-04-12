package com.github.lkq.timeron.samples;

import com.github.lkq.timeron.Timer;
import com.github.lkq.timeron.hierarchy.lv2.Mother;
import com.github.lkq.timeron.hierarchy.lv3.Son;
import org.json.JSONException;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.Customization;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.comparator.CustomComparator;

import java.util.function.Supplier;
import java.util.logging.Logger;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class AbstractClassImplTest {

    private Logger logger = Logger.getLogger(AbstractClassImplTest.class.getName());

    private void execute(Supplier<String> supplier, String expectedRetVal, int repeats) {
        for (int i = 0; i < repeats - 1; i++) {
            supplier.get();
        }

        String retVal = supplier.get();
        assertThat(retVal, is(expectedRetVal));
    }

    @Test
    void canMeasureDeclaredMethod() throws JSONException {
        Timer timer = new Timer();
        Son son = timer.intercept(Son.class);
        timer.measure(son.implInSon(""));

        Son kingson = timer.on(new Son("kingson"));

        execute(() -> kingson.implInSon("test"), "implInSon-test", 10);

        String stats = timer.getStats();
        logger.info("actual:" + stats);
        JSONAssert.assertEquals("[{\"com.github.lkq.timeron.hierarchy.lv3.Son.implInSon\":{\"total\":1,\"count\":10,\"avg\":1234}}]", stats,
                new CustomComparator(JSONCompareMode.STRICT,
                        new Customization("[0].com.github.lkq.timeron.hierarchy.lv3.Son.implInSon.total", (o1, o2) -> ((int)o2) > 0),
                        new Customization("[0].com.github.lkq.timeron.hierarchy.lv3.Son.implInSon.avg", (o1, o2) -> ((int)o2) > 0)
                ));
    }

    /**
     * Mother:
     *      -implInMother
     *      -fromMotherImplInSon
     * Son:
     *      -implInSon
     *      -fromMotherImplInSon
     *
     * measure(Mother.implInMother)
     *      mother.implInMother - measured
     *      son.implInMother    - measured
     *
     * measure(Son.implInMother)
     *      mother.implInMother - not measured
     *      son.implInMother    - measured
     *
     * measure(Mother.fromMotherImplInSon)
     *      son.fromMotherImplInSon - measured
     *
     * measure(Son.fromMotherImplInSon)
     *      son.fromMotherImplInSon - measured
     */
    @Test
    void canMeasureSuperClassDeclaredMethodUsingSuperClass() throws JSONException {
        Timer timer = new Timer();
        Mother mother = timer.intercept(Mother.class);
        timer.measure(mother.implInMother(""));

        Son kingson = timer.on(new Son("kingson"));

        execute(() -> kingson.implInMother("test"), "implInMother-test", 10);

        String stats = timer.getStats();
        logger.info("actual:" + stats);
        JSONAssert.assertEquals("[{\"com.github.lkq.timeron.hierarchy.lv2.Mother.implInMother\":{\"total\":1,\"count\":10,\"avg\":1234}}]", stats,
                new CustomComparator(JSONCompareMode.STRICT,
                        new Customization("[0].com.github.lkq.timeron.hierarchy.lv2.Mother.implInMother.total", (o1, o2) -> ((int)o2) > 0),
                        new Customization("[0].com.github.lkq.timeron.hierarchy.lv2.Mother.implInMother.avg", (o1, o2) -> ((int)o2) > 0)
                ));
    }

    @Test
    void canMeasureSuperClassDeclaredMethodUsingChildClass() throws JSONException {
        Timer timer = new Timer();
        Son son = timer.intercept(Son.class);
        timer.measure(son.implInMother(""));

        Son kingson = timer.on(new Son("kingson"));

        execute(() -> kingson.implInMother("test"), "implInMother-test", 10);

        String stats = timer.getStats();
        logger.info("actual:" + stats);
        JSONAssert.assertEquals("[{\"com.github.lkq.timeron.hierarchy.lv2.Mother.implInMother\":{\"total\":1,\"count\":10,\"avg\":1234}}]", stats,
                new CustomComparator(JSONCompareMode.STRICT,
                        new Customization("[0].com.github.lkq.timeron.hierarchy.lv2.Mother.implInMother.total", (o1, o2) -> ((int)o2) > 0),
                        new Customization("[0].com.github.lkq.timeron.hierarchy.lv2.Mother.implInMother.avg", (o1, o2) -> ((int)o2) > 0)
                ));
    }

    @Disabled("pending fix")
    @Test
    void canMeasureSuperClassDeclaredAbstractMethodUsingSuperClass() throws JSONException {
        Timer timer = new Timer();
        Mother mother = timer.intercept(Mother.class);
        timer.measure(mother.fromMotherImplInSon(""));

        Son kingson = timer.on(new Son("kingson"));

        execute(() -> kingson.fromMotherImplInSon("test"), "fromMotherImplInSon-test", 10);

        String stats = timer.getStats();
        logger.info("actual:" + stats);
        JSONAssert.assertEquals("[{\"com.github.lkq.timeron.hierarchy.lv2.Son.fromMotherImplInSon\":{\"total\":1,\"count\":10,\"avg\":1234}}]", stats,
                new CustomComparator(JSONCompareMode.STRICT,
                        new Customization("[0].com.github.lkq.timeron.hierarchy.lv2.Son.fromMotherImplInSon.total", (o1, o2) -> ((int)o2) > 0),
                        new Customization("[0].com.github.lkq.timeron.hierarchy.lv2.Son.fromMotherImplInSon.avg", (o1, o2) -> ((int)o2) > 0)
                ));
    }

    @Disabled("pending fix")
    @Test
    void canMeasureSuperClassDeclaredAbstractMethodUsingChildClass() throws JSONException {
        Timer timer = new Timer();
        Son son = timer.intercept(Son.class);
        timer.measure(son.fromMotherImplInSon(""));

        Son kingson = timer.on(new Son("kingson"));

        execute(() -> kingson.fromMotherImplInSon("test"), "fromMotherImplInSon-test", 10);

        String stats = timer.getStats();
        logger.info("actual:" + stats);
        JSONAssert.assertEquals("[{\"com.github.lkq.timeron.hierarchy.lv2.Son.fromMotherImplInSon\":{\"total\":1,\"count\":10,\"avg\":1234}}]", stats,
                new CustomComparator(JSONCompareMode.STRICT,
                        new Customization("[0].com.github.lkq.timeron.hierarchy.lv2.Son.fromMotherImplInSon.total", (o1, o2) -> ((int)o2) > 0),
                        new Customization("[0].com.github.lkq.timeron.hierarchy.lv2.Son.fromMotherImplInSon.avg", (o1, o2) -> ((int)o2) > 0)
                ));
    }

    @Disabled("pending fix")
    @Test
    void canMeasureSuperClassDeclaredAbstractMethodImplementedByChildClass() throws JSONException {
        Timer timer = new Timer();
        Mother mother = timer.intercept(Mother.class);
        timer.measure(mother.fromMotherImplInSon(""));

        Son kingson = timer.on(new Son("kingson"));

        execute(() -> kingson.fromMotherImplInSon("test"), "fromMotherImplInSon-test", 10);

        String stats = timer.getStats();
        logger.info("actual:" + stats);
        JSONAssert.assertEquals("[{\"com.github.lkq.timeron.hierarchy.lv2.Son.fromMotherImplInSon\":{\"total\":1,\"count\":10,\"avg\":1234}}]", stats,
                new CustomComparator(JSONCompareMode.STRICT,
                        new Customization("[0].com.github.lkq.timeron.hierarchy.lv2.Son.fromMotherImplInSon.total", (o1, o2) -> ((int)o2) > 0),
                        new Customization("[0].com.github.lkq.timeron.hierarchy.lv2.Son.fromMotherImplInSon.avg", (o1, o2) -> ((int)o2) > 0)
                ));
    }

    @Disabled("fix later")
    @Test
    void interceptChildClassMethodOverrideFromSuperClassWillMeasureUnderChildClassName() throws JSONException {
        Timer timer = new Timer();
        Son son = timer.intercept(Son.class);
        timer.measure(son.fromMotherImplInSon(""));

        Son kingson = timer.on(new Son("kingson"));

        execute(() -> kingson.fromMotherImplInSon("test"), "fromMotherImplInSon-test", 10);

        String stats = timer.getStats();
        logger.info("actual:" + stats);
        JSONAssert.assertEquals("[{\"com.github.lkq.timeron.hierarchy.lv3.Son.fromMotherImplInSon\":{\"total\":1,\"count\":10,\"avg\":1234}}]", stats,
                new CustomComparator(JSONCompareMode.STRICT,
                        new Customization("[0].com.github.lkq.timeron.hierarchy.lv3.Son.fromMotherImplInSon.total", (o1, o2) -> ((int)o2) > 0),
                        new Customization("[0].com.github.lkq.timeron.hierarchy.lv3.Son.fromMotherImplInSon.avg", (o1, o2) -> ((int)o2) > 0)
                ));
    }

    @Disabled("implementation not ready yet")
    @Test
    void interceptSuperClassMethodCanMeasuredChildClassMethodUnderChildClassName() throws JSONException {
        Timer timer = new Timer();
        Mother mother = timer.intercept(Mother.class);
        timer.measure(mother.fromMotherImplInSon(""));

        Son kingson = timer.on(new Son("kingson"));

        execute(() -> kingson.fromMotherImplInSon("test"), "fromMotherImplInSon-test", 10);

        String stats = timer.getStats();
        logger.info("actual:" + stats);
        JSONAssert.assertEquals("[{\"com.github.lkq.timeron.hierarchy.lv3.Son.fromMotherImplInSon\":{\"total\":1,\"count\":10,\"avg\":1234}}]", stats,
                new CustomComparator(JSONCompareMode.STRICT,
                        new Customization("[0].com.github.lkq.timeron.hierarchy.lv3.Son.fromMotherImplInSon.total", (o1, o2) -> ((int)o2) > 0),
                        new Customization("[0].com.github.lkq.timeron.hierarchy.lv3.Son.fromMotherImplInSon.avg", (o1, o2) -> ((int)o2) > 0)
                ));
    }

    @Test
    void willNotMeasurePerformanceOnNotInterceptedClassMethod() throws JSONException {
        Timer timer = new Timer();

        Son kingson = timer.on(new Son("kingson"));

        execute(() -> kingson.fromSonTagInGrandson("test"), "fromSonTagInGrandson-test", 10);

        String stats = timer.getStats();
        logger.info("actual:" + stats);
        JSONAssert.assertEquals("[]", stats,true);
    }
}
