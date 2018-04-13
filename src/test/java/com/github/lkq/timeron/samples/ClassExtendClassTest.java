package com.github.lkq.timeron.samples;

import com.github.lkq.timeron.Timer;
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

/**
 * measure(Mother.implInMother)
 *      mother.implInMother()       - measured
 *      son.implInMother()          - measured
 *      daughter.implInMother()     - measured
 *
 * measure(Mother.implInMotherOverrideInSon)
 *      mother.implInMotherOverrideInSon()      - measured
 *      son.implInMotherOverrideInSon()         - measured
 *      daughter.implInMotherOverrideInSon()    - measured
 *
 * measure(Son.implInMother)
 *      mother.implInMother()   - not measured
 *      son.implInMother()      - measured
 *      daughter.implInMother() - not measured
 *
 * measure(Son.implInMotherOverrideInSon)
 *      mother.implInMotherOverrideInSon()      - not measured
 *      son.implInMotherOverrideInSon()         - measured
 *      daughter.implInMotherOverrideInSon()    - not measured
 */

public class ClassExtendClassTest {

    private Logger logger = Logger.getLogger(AbstractClassImplTest.class.getSimpleName());

    @Test
    void measurementOnParentMethodCanBeInheritedByAllChild() throws JSONException {
        Timer timer = new Timer();
        Mother motherInterceptor = timer.intercept(Mother.class);
        timer.measure(motherInterceptor.implInMother(""));

        Mother mother = timer.on(new Mother());
        Son son = timer.on(new Son());
        Daughter daughter = timer.on(new Daughter());

        execute(() -> mother.implInMother("test"), "implInMother-test", 10);
        execute(() -> son.implInMother("test"), "implInMother-test", 10);
        execute(() -> daughter.implInMother("test"), "implInMother-test", 10);

        String stats = timer.getStats();
        logger.info("actual:" + stats);
        JSONAssert.assertEquals("[{\"com.github.lkq.timeron.samples.ClassExtendClassTest$Mother.implInMother\":{\"total\":1,\"count\":30,\"avg\":1234}}]", stats,
                new CustomComparator(JSONCompareMode.STRICT,
                        new Customization("[0].com.github.lkq.timeron.samples.ClassExtendClassTest$Mother.implInMother.total", (o1, o2) -> ((int)o2) > 0),
                        new Customization("[0].com.github.lkq.timeron.samples.ClassExtendClassTest$Mother.implInMother.avg", (o1, o2) -> ((int)o2) > 0)
                ));

    }

    @Disabled("pending fix")
    @Test
    void measurementOnParentMethodCanBeInheritedByOverrideMethodInChild() throws JSONException {
        Timer timer = new Timer();
        Mother motherInterceptor = timer.intercept(Mother.class);
        timer.measure(motherInterceptor.implInMotherOverrideInSon(""));

        Mother mother = timer.on(new Mother());
        Son son = timer.on(new Son());
        Daughter daughter = timer.on(new Daughter());

        execute(() -> mother.implInMotherOverrideInSon("test"), "implInMotherOverrideInSon-test", 10);
        execute(() -> son.implInMotherOverrideInSon("test"), "implInMotherOverrideInSon-test", 10);
        execute(() -> daughter.implInMotherOverrideInSon("test"), "implInMotherOverrideInSon-test", 10);

        String stats = timer.getStats();
        logger.info("actual:" + stats);
        JSONAssert.assertEquals("[{\"com.github.lkq.timeron.samples.ClassExtendClassTest$Mother.implInMotherOverrideInSon\":{\"total\":1,\"count\":10,\"avg\":1234}}," +
                        "{\"com.github.lkq.timeron.samples.ClassExtendClassTest$Son.implInMotherOverrideInSon\":{\"total\":1,\"count\":10,\"avg\":1234}}," +
                        "{\"com.github.lkq.timeron.samples.ClassExtendClassTest$Mother.implInMotherOverrideInSon\":{\"total\":1,\"count\":10,\"avg\":1234}}]", stats,
                new CustomComparator(JSONCompareMode.STRICT,
                        new Customization("[0].com.github.lkq.timeron.samples.ClassExtendClassTest$Mother.implInMother.total", (o1, o2) -> ((int)o2) > 0),
                        new Customization("[0].com.github.lkq.timeron.samples.ClassExtendClassTest$Mother.implInMother.avg", (o1, o2) -> ((int)o2) > 0)
                ));

    }

    @Disabled("fix later")
    @Test
    void measurementOnChildMethodWontAffectParentClassAndItsOtherSubClass() throws JSONException {
        Timer timer = new Timer();
        Son fakeSon = timer.intercept(Son.class);
        timer.measure(fakeSon.implInMother(""));

        Mother mother = timer.on(new Mother());
        Son son = timer.on(new Son());
        Mother daughter = timer.on(new Daughter());

        execute(() -> mother.implInMother("test"), "implInMother-test", 10);
        execute(() -> son.implInMother("test"), "implInMother-test", 10);
        execute(() -> daughter.implInMother("test"), "implInMother-test", 10);

        String stats = timer.getStats();
        logger.info("actual:" + stats);
        JSONAssert.assertEquals("[{\"com.github.lkq.timeron.samples.ClassExtendClassTest$Mother.implInMother\":{\"total\":1,\"count\":10,\"avg\":1234}}]", stats,
                new CustomComparator(JSONCompareMode.STRICT,
                        new Customization("[0].com.github.lkq.timeron.samples.ClassExtendClassTest$Mother.implInMother.total", (o1, o2) -> ((int)o2) > 0),
                        new Customization("[0].com.github.lkq.timeron.samples.ClassExtendClassTest$Mother.implInMother.avg", (o1, o2) -> ((int)o2) > 0)
                ));
    }

    @Test
    void measurementOnChildOverrideMethodWontAffectParentClassAndItsOtherSubClass() throws JSONException {
        Timer timer = new Timer();
        Son fakeSon = timer.intercept(Son.class);
        timer.measure(fakeSon.implInMotherOverrideInSon(""));

        Mother mother = timer.on(new Mother());
        Son son = timer.on(new Son());
        Mother daughter = timer.on(new Daughter());

        execute(() -> mother.implInMotherOverrideInSon("test"), "implInMotherOverrideInSon-test", 10);
        execute(() -> son.implInMotherOverrideInSon("test"), "Son.implInMotherOverrideInSon-test", 10);
        execute(() -> daughter.implInMotherOverrideInSon("test"), "implInMotherOverrideInSon-test", 10);

        String stats = timer.getStats();
        logger.info("actual:" + stats);
        JSONAssert.assertEquals("[{\"com.github.lkq.timeron.samples.ClassExtendClassTest$Son.implInMotherOverrideInSon\":{\"total\":1,\"count\":10,\"avg\":1234}}]", stats,
                new CustomComparator(JSONCompareMode.STRICT,
                        new Customization("[0].com.github.lkq.timeron.samples.ClassExtendClassTest$Son.implInMotherOverrideInSon.total", (o1, o2) -> ((int)o2) > 0),
                        new Customization("[0].com.github.lkq.timeron.samples.ClassExtendClassTest$Son.implInMotherOverrideInSon.avg", (o1, o2) -> ((int)o2) > 0)
                ));
    }

    private void execute(Supplier<String> supplier, String expectedRetVal, int repeats) {
        for (int i = 0; i < repeats - 1; i++) {
            supplier.get();
        }

        String retVal = supplier.get();
        assertThat(retVal, is(expectedRetVal));
    }

    public static class Mother {
        public String implInMother(String arg) {
            return "implInMother-" + arg;
        }

        public String implInMotherOverrideInSon(String arg) {
            return "implInMotherOverrideInSon-" + arg;
        }
    }

    public static class Son extends Mother {
        @Override
        public String implInMotherOverrideInSon(String arg) {
            return "Son.implInMotherOverrideInSon-" + arg;
        }
    }

    public static class Daughter extends Mother {

    }
}
