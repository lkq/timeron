package com.github.lkq.timeron.samples;

import com.github.lkq.timeron.Timer;
import org.json.JSONException;
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
 * mother.implInMother()       - measured as Mother
 * son.implInMother()          - measured as Son
 * daughter.implInMother()     - measured as Daughter
 * <p>
 * measure(Mother.implInMotherOverrideInSon)
 * mother.implInMotherOverrideInSon()      - measured as Mother
 * son.implInMotherOverrideInSon()         - measured as Son
 * daughter.implInMotherOverrideInSon()    - measured as Daughter
 * <p>
 * measure(Son.implInMother)
 * mother.implInMother()   - not measured
 * son.implInMother()      - measured as Son
 * daughter.implInMother() - not measured
 * <p>
 * measure(Son.implInMotherOverrideInSon)
 * mother.implInMotherOverrideInSon()      - not measured
 * son.implInMotherOverrideInSon()         - measured as Son
 * daughter.implInMotherOverrideInSon()    - not measured
 */

public class ClassHierarchyTest {

    private Logger logger = Logger.getLogger(AbstractClassHierarchyTest.class.getSimpleName());

    @Test
    void measurementOnParentMethodCanBeInheritedByAllChild() throws JSONException {
        Timer timer = new Timer();
        Mother motherInterceptor = timer.interceptor(Mother.class);
        timer.measure(motherInterceptor.implInMother(""));

        Mother mother = timer.on(new Mother());
        Son son = timer.on(new Son());
        Daughter daughter = timer.on(new Daughter());

        execute(() -> mother.implInMother("test"), "implInMother-test", 10);
        execute(() -> son.implInMother("test"), "implInMother-test", 11);
        execute(() -> daughter.implInMother("test"), "implInMother-test", 12);

        String stats = timer.getStats();
        logger.info("actual:" + stats);
        JSONAssert.assertEquals("[{\"com.github.lkq.timeron.samples.ClassHierarchyTest$Mother.implInMother(String)\":{\"total\":12345,\"count\":10,\"avg\":1234}}," +
                        "{\"com.github.lkq.timeron.samples.ClassHierarchyTest$Son.implInMother(String)\":{\"total\":12345,\"count\":11,\"avg\":1234}}," +
                        "{\"com.github.lkq.timeron.samples.ClassHierarchyTest$Daughter.implInMother(String)\":{\"total\":12345,\"count\":12,\"avg\":1234}}]", stats,
                new CustomComparator(JSONCompareMode.STRICT,
                        new Customization("[0].com.github.lkq.timeron.samples.ClassHierarchyTest$Mother.implInMother(String).total", (o1, o2) -> ((int) o2) > 0),
                        new Customization("[0].com.github.lkq.timeron.samples.ClassHierarchyTest$Mother.implInMother(String).avg", (o1, o2) -> ((int) o2) > 0),
                        new Customization("[1].com.github.lkq.timeron.samples.ClassHierarchyTest$Son.implInMother(String).total", (o1, o2) -> ((int) o2) > 0),
                        new Customization("[1].com.github.lkq.timeron.samples.ClassHierarchyTest$Son.implInMother(String).avg", (o1, o2) -> ((int) o2) > 0),
                        new Customization("[2].com.github.lkq.timeron.samples.ClassHierarchyTest$Daughter.implInMother(String).total", (o1, o2) -> ((int) o2) > 0),
                        new Customization("[2].com.github.lkq.timeron.samples.ClassHierarchyTest$Daughter.implInMother(String).avg", (o1, o2) -> ((int) o2) > 0)
                ));

    }

    @Test
    void measurementOnParentMethodCanBeInheritedByOverrideMethodInChild() throws JSONException {
        Timer timer = new Timer();
        Mother motherInterceptor = timer.interceptor(Mother.class);
        timer.measure(motherInterceptor.implInMotherOverrideInSon(""));

        Mother mother = timer.on(new Mother());
        Son son = timer.on(new Son());
        Daughter daughter = timer.on(new Daughter());

        execute(() -> mother.implInMotherOverrideInSon("test"), "implInMotherOverrideInSon-test", 10);
        execute(() -> son.implInMotherOverrideInSon("test"), "Son.implInMotherOverrideInSon-test", 11);
        execute(() -> daughter.implInMotherOverrideInSon("test"), "implInMotherOverrideInSon-test", 12);

        String stats = timer.getStats();
        logger.info("actual:" + stats);
        JSONAssert.assertEquals("[{\"com.github.lkq.timeron.samples.ClassHierarchyTest$Mother.implInMotherOverrideInSon(String)\":{\"total\":12345,\"count\":10,\"avg\":1234}}," +
                        "{\"com.github.lkq.timeron.samples.ClassHierarchyTest$Son.implInMotherOverrideInSon(String)\":{\"total\":12345,\"count\":11,\"avg\":1234}}," +
                        "{\"com.github.lkq.timeron.samples.ClassHierarchyTest$Daughter.implInMotherOverrideInSon(String)\":{\"total\":12345,\"count\":12,\"avg\":1234}}]", stats,
                new CustomComparator(JSONCompareMode.STRICT,
                        new Customization("[0].com.github.lkq.timeron.samples.ClassHierarchyTest$Mother.implInMotherOverrideInSon(String).total", (o1, o2) -> ((int) o2) > 0),
                        new Customization("[0].com.github.lkq.timeron.samples.ClassHierarchyTest$Mother.implInMotherOverrideInSon(String).avg", (o1, o2) -> ((int) o2) > 0),
                        new Customization("[1].com.github.lkq.timeron.samples.ClassHierarchyTest$Son.implInMotherOverrideInSon(String).total", (o1, o2) -> ((int) o2) > 0),
                        new Customization("[1].com.github.lkq.timeron.samples.ClassHierarchyTest$Son.implInMotherOverrideInSon(String).avg", (o1, o2) -> ((int) o2) > 0),
                        new Customization("[2].com.github.lkq.timeron.samples.ClassHierarchyTest$Daughter.implInMotherOverrideInSon(String).total", (o1, o2) -> ((int) o2) > 0),
                        new Customization("[2].com.github.lkq.timeron.samples.ClassHierarchyTest$Daughter.implInMotherOverrideInSon(String).avg", (o1, o2) -> ((int) o2) > 0)
                ));

    }

    @Test
    void measurementOnChildMethodWillNotAffectParentClassAndItsOtherSubClass() throws JSONException {
        Timer timer = new Timer();
        Son fakeSon = timer.interceptor(Son.class);
        timer.measure(fakeSon.implInMother(""));

        Mother mother = timer.on(new Mother());
        Son son = timer.on(new Son());
        Mother daughter = timer.on(new Daughter());

        execute(() -> mother.implInMother("test"), "implInMother-test", 10);
        execute(() -> son.implInMother("test"), "implInMother-test", 11);
        execute(() -> daughter.implInMother("test"), "implInMother-test", 12);

        String stats = timer.getStats();
        logger.info("actual:" + stats);
        JSONAssert.assertEquals("[{\"com.github.lkq.timeron.samples.ClassHierarchyTest$Son.implInMother(String)\":{\"total\":1,\"count\":11,\"avg\":1234}}]", stats,
                new CustomComparator(JSONCompareMode.STRICT,
                        new Customization("[0].com.github.lkq.timeron.samples.ClassHierarchyTest$Son.implInMother(String).total", (o1, o2) -> ((int) o2) > 0),
                        new Customization("[0].com.github.lkq.timeron.samples.ClassHierarchyTest$Son.implInMother(String).avg", (o1, o2) -> ((int) o2) > 0)
                ));
    }

    @Test
    void measurementOnChildOverrideMethodWillNotAffectParentClassAndItsOtherSubClass() throws JSONException {
        Timer timer = new Timer();
        Son fakeSon = timer.interceptor(Son.class);
        timer.measure(fakeSon.implInMotherOverrideInSon(""));

        Mother mother = timer.on(new Mother());
        Son son = timer.on(new Son());
        Mother daughter = timer.on(new Daughter());

        execute(() -> mother.implInMotherOverrideInSon("test"), "implInMotherOverrideInSon-test", 10);
        execute(() -> son.implInMotherOverrideInSon("test"), "Son.implInMotherOverrideInSon-test", 11);
        execute(() -> daughter.implInMotherOverrideInSon("test"), "implInMotherOverrideInSon-test", 12);

        String stats = timer.getStats();
        logger.info("actual:" + stats);
        JSONAssert.assertEquals("[{\"com.github.lkq.timeron.samples.ClassHierarchyTest$Son.implInMotherOverrideInSon(String)\":{\"total\":5,\"count\":11,\"avg\":1234}}]", stats,
                new CustomComparator(JSONCompareMode.STRICT,
                        new Customization("[0].com.github.lkq.timeron.samples.ClassHierarchyTest$Son.implInMotherOverrideInSon(String).total", (o1, o2) -> ((int) o2) > 0),
                        new Customization("[0].com.github.lkq.timeron.samples.ClassHierarchyTest$Son.implInMotherOverrideInSon(String).avg", (o1, o2) -> ((int) o2) > 0)
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
