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
 * measure(Mother.declaredInMother)
 * -- son.declaredInMother()          - measured as Son
 * -- grandson.declaredInMother()     - measured as Grandson
 * -- daughter.declaredInMother()     - measured as Daughter
 * <p>
 * measure(Son.declaredInMother)
 * -- son.declaredInMother()      - measured as Son
 * -- grandson.declaredInMother() - measured as Grandson
 * -- daughter.declaredInMother() - not measured
 */
public class AbstractClassHierarchyTest {

    private Logger logger = Logger.getLogger(AbstractClassHierarchyTest.class.getSimpleName());

    @Test
    void measurementOnParentAbstractMethodCanBeInheritedByAllChild() throws JSONException {
        Timer timer = new Timer();
        Mother mother = timer.interceptor(Mother.class);
        timer.measure(mother.declaredInMother(""));

        Son son = timer.on(new Son());
        Grandson grandson = timer.on(new Grandson());
        Daughter daughter = timer.on(new Daughter());

        execute(() -> son.declaredInMother("test"), "Son.declaredInMother-test", 10);
        execute(() -> grandson.declaredInMother("test"), "Son.declaredInMother-test", 11);
        execute(() -> daughter.declaredInMother("test"), "Daughter.declaredInMother-test", 12);

        String stats = timer.getStats();
        logger.info("actual:" + stats);
        JSONAssert.assertEquals("[{\"com.github.lkq.timeron.samples.AbstractClassHierarchyTest$Son.declaredInMother(String)\":{\"total\":1,\"count\":10,\"avg\":1234}}," +
                        "{\"com.github.lkq.timeron.samples.AbstractClassHierarchyTest$Daughter.declaredInMother(String)\":{\"total\":1,\"count\":12,\"avg\":1234}}," +
                        "{\"com.github.lkq.timeron.samples.AbstractClassHierarchyTest$Grandson.declaredInMother(String)\":{\"total\":1,\"count\":11,\"avg\":1234}}]", stats,
                new CustomComparator(JSONCompareMode.STRICT,
                        new Customization("[0].com.github.lkq.timeron.samples.AbstractClassHierarchyTest$Son.declaredInMother(String).total", (o1, o2) -> ((int) o2) > 0),
                        new Customization("[0].com.github.lkq.timeron.samples.AbstractClassHierarchyTest$Son.declaredInMother(String).avg", (o1, o2) -> ((int) o2) > 0),
                        new Customization("[1].com.github.lkq.timeron.samples.AbstractClassHierarchyTest$Daughter.declaredInMother(String).total", (o1, o2) -> ((int) o2) > 0),
                        new Customization("[1].com.github.lkq.timeron.samples.AbstractClassHierarchyTest$Daughter.declaredInMother(String).avg", (o1, o2) -> ((int) o2) > 0),
                        new Customization("[2].com.github.lkq.timeron.samples.AbstractClassHierarchyTest$Grandson.declaredInMother(String).total", (o1, o2) -> ((int) o2) > 0),
                        new Customization("[2].com.github.lkq.timeron.samples.AbstractClassHierarchyTest$Grandson.declaredInMother(String).avg", (o1, o2) -> ((int) o2) > 0)
                ));
    }

    @Test
    void measurementOnChildImplementedAbstractMethodWillNotAffectOtherChildImplementation() throws JSONException {
        Timer timer = new Timer();
        Son sonInterceptor = timer.interceptor(Son.class);
        timer.measure(sonInterceptor.declaredInMother(""));

        Son son = timer.on(new Son());
        Grandson grandson = timer.on(new Grandson());
        Daughter daughter = timer.on(new Daughter());

        execute(() -> son.declaredInMother("test"), "Son.declaredInMother-test", 10);
        execute(() -> grandson.declaredInMother("test"), "Son.declaredInMother-test", 11);
        execute(() -> daughter.declaredInMother("test"), "Daughter.declaredInMother-test", 12);

        String stats = timer.getStats();
        logger.info("actual:" + stats);
        JSONAssert.assertEquals("[{\"com.github.lkq.timeron.samples.AbstractClassHierarchyTest$Son.declaredInMother(String)\":{\"total\":1,\"count\":10,\"avg\":1234}}," +
                        "{\"com.github.lkq.timeron.samples.AbstractClassHierarchyTest$Grandson.declaredInMother(String)\":{\"total\":1,\"count\":11,\"avg\":1234}}]", stats,
                new CustomComparator(JSONCompareMode.STRICT,
                        new Customization("[0].com.github.lkq.timeron.samples.AbstractClassHierarchyTest$Son.declaredInMother(String).total", (o1, o2) -> ((int) o2) > 0),
                        new Customization("[0].com.github.lkq.timeron.samples.AbstractClassHierarchyTest$Son.declaredInMother(String).avg", (o1, o2) -> ((int) o2) > 0),
                        new Customization("[1].com.github.lkq.timeron.samples.AbstractClassHierarchyTest$Grandson.declaredInMother(String).total", (o1, o2) -> ((int) o2) > 0),
                        new Customization("[1].com.github.lkq.timeron.samples.AbstractClassHierarchyTest$Grandson.declaredInMother(String).avg", (o1, o2) -> ((int) o2) > 0)
                ));
    }

    private void execute(Supplier<String> supplier, String expectedRetVal, int repeats) {
        for (int i = 0; i < repeats - 1; i++) {
            supplier.get();
        }

        String retVal = supplier.get();
        assertThat(retVal, is(expectedRetVal));
    }

    public static abstract class Mother {

        abstract public String declaredInMother(String arg);
    }

    public static class Son extends Mother {
        @Override
        public String declaredInMother(String arg) {
            return "Son.declaredInMother-" + arg;
        }
    }

    public static class Daughter extends Mother {

        @Override
        public String declaredInMother(String arg) {
            return "Daughter.declaredInMother-" + arg;
        }
    }

    public static class Grandson extends Son {

    }
}
