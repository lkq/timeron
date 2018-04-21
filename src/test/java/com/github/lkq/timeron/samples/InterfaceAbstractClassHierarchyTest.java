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
 * measure(Grandma.declaredInGrandmaImplInMother)
 * -- son.declaredInGrandmaImplInMother()           - measured as Son
 * -- daughter.declaredInGrandmaImplInMother()      - measured as Daughter
 * -- aunt.declaredInGrandmaImplInMother()          - measured as Daughter
 * <p>
 * measure(Grandma.declaredInGrandma)
 * -- son.declaredInGrandma()       - measured as Son
 * <p>
 * measure(Mother.declaredInGrandma)
 * -- son.declaredInGrandma()       - measured as Son
 * -- daughter.declaredInGrandma()  - measured as Daughter
 * -- aunt.declaredInGrandma()      - not measured
 * <p>
 * measure(Aunt.declaredInGrandma)
 * -- son.declaredInGrandma()       - not measured
 * -- daughter.declaredInGrandma()  - not measured
 * -- aunt.declaredInGrandma()      - measured as Aunt
 */
public class InterfaceAbstractClassHierarchyTest {

    private Logger logger = Logger.getLogger(InterfaceAbstractClassHierarchyTest.class.getSimpleName());

    private void execute(Supplier<String> supplier, String expectedRetVal, int repeats) {
        for (int i = 0; i < repeats - 1; i++) {
            supplier.get();
        }

        String retVal = supplier.get();
        assertThat(retVal, is(expectedRetVal));
    }

    @Test
    void measurementOnInterfaceMethodCanBeInheritedByAllAbstractClassAndItsChild() throws JSONException {
        Timer timer = new Timer();
        Grandma grandma = timer.interceptor(Grandma.class);
        timer.measure(grandma.declaredInGrandmaImplInMother(""));

        Son son = timer.on(new Son());
        Daughter daughter = timer.on(new Daughter());
        Aunt aunt = timer.on(new Aunt());

        execute(() -> son.declaredInGrandmaImplInMother("test"), "Son.declaredInGrandmaImplInMother-test", 10);
        execute(() -> daughter.declaredInGrandmaImplInMother("test"), "Daughter.declaredInGrandmaImplInMother-test", 11);
        execute(() -> aunt.declaredInGrandmaImplInMother("test"), "Aunt.declaredInGrandmaImplInMother-test", 12);
        String stats = timer.getStats();
        logger.info("actual:" + stats);
        JSONAssert.assertEquals("[{\"com.github.lkq.timeron.samples.InterfaceAbstractClassHierarchyTest$Aunt.declaredInGrandmaImplInMother(String)\":{\"total\":12345,\"count\":12,\"avg\":1234}}," +
                        "{\"com.github.lkq.timeron.samples.InterfaceAbstractClassHierarchyTest$Daughter.declaredInGrandmaImplInMother(String)\":{\"total\":12345,\"count\":11,\"avg\":1234}}," +
                        "{\"com.github.lkq.timeron.samples.InterfaceAbstractClassHierarchyTest$Son.declaredInGrandmaImplInMother(String)\":{\"total\":12345,\"count\":10,\"avg\":1234}}]", stats,
                new CustomComparator(JSONCompareMode.STRICT,
                        new Customization("[0].com.github.lkq.timeron.samples.InterfaceAbstractClassHierarchyTest$Aunt.declaredInGrandmaImplInMother(String).total", (o1, o2) -> ((int) o2) > 0),
                        new Customization("[0].com.github.lkq.timeron.samples.InterfaceAbstractClassHierarchyTest$Aunt.declaredInGrandmaImplInMother(String).avg", (o1, o2) -> ((int) o2) > 0),
                        new Customization("[1].com.github.lkq.timeron.samples.InterfaceAbstractClassHierarchyTest$Daughter.declaredInGrandmaImplInMother(String).total", (o1, o2) -> ((int) o2) > 0),
                        new Customization("[1].com.github.lkq.timeron.samples.InterfaceAbstractClassHierarchyTest$Daughter.declaredInGrandmaImplInMother(String).avg", (o1, o2) -> ((int) o2) > 0),
                        new Customization("[2].com.github.lkq.timeron.samples.InterfaceAbstractClassHierarchyTest$Son.declaredInGrandmaImplInMother(String).total", (o1, o2) -> ((int) o2) > 0),
                        new Customization("[2].com.github.lkq.timeron.samples.InterfaceAbstractClassHierarchyTest$Son.declaredInGrandmaImplInMother(String).avg", (o1, o2) -> ((int) o2) > 0)
                ));
    }

    @Test
    void measurementOnInterfaceMethodCanBeInheritedByGrandChild() throws JSONException {
        Timer timer = new Timer();
        Grandma grandma = timer.interceptor(Grandma.class);
        timer.measure(grandma.declaredInGrandma(""));

        Son son = timer.on(new Son());
        Daughter daughter = timer.on(new Daughter());

        execute(() -> son.declaredInGrandma("test"), "Son.declaredInGrandma-test", 10);
        execute(() -> daughter.declaredInGrandma("test"), "Daughter.declaredInGrandma-test", 11);

        String stats = timer.getStats();
        logger.info("actual:" + stats);
        JSONAssert.assertEquals("[{\"com.github.lkq.timeron.samples.InterfaceAbstractClassHierarchyTest$Son.declaredInGrandma(String)\":{\"total\":12345,\"count\":10,\"avg\":1234}}," +
                        "{\"com.github.lkq.timeron.samples.InterfaceAbstractClassHierarchyTest$Daughter.declaredInGrandma(String)\":{\"total\":12345,\"count\":11,\"avg\":1234}}]", stats,
                new CustomComparator(JSONCompareMode.STRICT,
                        new Customization("[0].com.github.lkq.timeron.samples.InterfaceAbstractClassHierarchyTest$Son.declaredInGrandma(String).total", (o1, o2) -> ((int) o2) > 0),
                        new Customization("[0].com.github.lkq.timeron.samples.InterfaceAbstractClassHierarchyTest$Son.declaredInGrandma(String).avg", (o1, o2) -> ((int) o2) > 0),
                        new Customization("[1].com.github.lkq.timeron.samples.InterfaceAbstractClassHierarchyTest$Daughter.declaredInGrandma(String).total", (o1, o2) -> ((int) o2) > 0),
                        new Customization("[1].com.github.lkq.timeron.samples.InterfaceAbstractClassHierarchyTest$Daughter.declaredInGrandma(String).avg", (o1, o2) -> ((int) o2) > 0)
                ));
    }

    @Test
    void measurementOnInterfaceMethodUsingAbstractClassCanBeInheritedByAbstractClassChildren() throws JSONException {
        Timer timer = new Timer();
        Mother mother = timer.interceptor(Mother.class);
        timer.measure(mother.declaredInGrandma(""));

        Son son = timer.on(new Son());
        Daughter daughter = timer.on(new Daughter());
        Aunt aunt = timer.on(new Aunt());

        execute(() -> son.declaredInGrandma("test"), "Son.declaredInGrandma-test", 10);
        execute(() -> daughter.declaredInGrandma("test"), "Daughter.declaredInGrandma-test", 11);
        execute(() -> aunt.declaredInGrandma("test"), "Aunt.declaredInGrandma-test", 12);

        String stats = timer.getStats();
        logger.info("actual:" + stats);
        JSONAssert.assertEquals("[{\"com.github.lkq.timeron.samples.InterfaceAbstractClassHierarchyTest$Son.declaredInGrandma(String)\":{\"total\":12345,\"count\":10,\"avg\":1234}}," +
                        "{\"com.github.lkq.timeron.samples.InterfaceAbstractClassHierarchyTest$Daughter.declaredInGrandma(String)\":{\"total\":12345,\"count\":11,\"avg\":1234}}]", stats,
                new CustomComparator(JSONCompareMode.STRICT,
                        new Customization("[0].com.github.lkq.timeron.samples.InterfaceAbstractClassHierarchyTest$Son.declaredInGrandma(String).total", (o1, o2) -> ((int) o2) > 0),
                        new Customization("[0].com.github.lkq.timeron.samples.InterfaceAbstractClassHierarchyTest$Son.declaredInGrandma(String).avg", (o1, o2) -> ((int) o2) > 0),
                        new Customization("[1].com.github.lkq.timeron.samples.InterfaceAbstractClassHierarchyTest$Daughter.declaredInGrandma(String).total", (o1, o2) -> ((int) o2) > 0),
                        new Customization("[1].com.github.lkq.timeron.samples.InterfaceAbstractClassHierarchyTest$Daughter.declaredInGrandma(String).avg", (o1, o2) -> ((int) o2) > 0)
                ));
    }

    @Test
    void measurementOnChildImplMethodWillNotAffectOtherImpl() throws JSONException {
        Timer timer = new Timer();
        Aunt interceptor = timer.interceptor(Aunt.class);
        timer.measure(interceptor.declaredInGrandma(""));

        Aunt aunt = timer.on(new Aunt());
        Son son = timer.on(new Son());
        Daughter daughter = timer.on(new Daughter());

        execute(() -> aunt.declaredInGrandma("test"), "Aunt.declaredInGrandma-test", 10);
        execute(() -> son.declaredInGrandma("test"), "Son.declaredInGrandma-test", 11);
        execute(() -> daughter.declaredInGrandma("test"), "Daughter.declaredInGrandma-test", 12);

        String stats = timer.getStats();
        logger.info("actual:" + stats);
        JSONAssert.assertEquals("[{\"com.github.lkq.timeron.samples.InterfaceAbstractClassHierarchyTest$Aunt.declaredInGrandma(String)\":{\"total\":12345,\"count\":10,\"avg\":1234}}]", stats,
                new CustomComparator(JSONCompareMode.STRICT,
                        new Customization("[0].com.github.lkq.timeron.samples.InterfaceAbstractClassHierarchyTest$Aunt.declaredInGrandma(String).total", (o1, o2) -> ((int) o2) > 0),
                        new Customization("[0].com.github.lkq.timeron.samples.InterfaceAbstractClassHierarchyTest$Aunt.declaredInGrandma(String).avg", (o1, o2) -> ((int) o2) > 0)
                ));
    }

    public interface Grandma {
        String declaredInGrandmaImplInMother(String arg);

        String declaredInGrandma(String arg);
    }

    public static abstract class Mother implements Grandma {

    }

    public static class Son extends Mother {
        @Override
        public String declaredInGrandmaImplInMother(String arg) {
            return "Son.declaredInGrandmaImplInMother-" + arg;
        }

        @Override
        public String declaredInGrandma(String arg) {
            return "Son.declaredInGrandma-" + arg;
        }
    }

    public static class Daughter extends Mother {
        @Override
        public String declaredInGrandmaImplInMother(String arg) {
            return "Daughter.declaredInGrandmaImplInMother-" + arg;
        }

        @Override
        public String declaredInGrandma(String arg) {
            return "Daughter.declaredInGrandma-" + arg;
        }
    }

    public static class Aunt implements Grandma {

        @Override
        public String declaredInGrandmaImplInMother(String arg) {
            return "Aunt.declaredInGrandmaImplInMother-" + arg;
        }

        @Override
        public String declaredInGrandma(String arg) {
            return "Aunt.declaredInGrandma-" + arg;
        }
    }
}
