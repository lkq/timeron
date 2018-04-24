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
 * measure(Grandma.declaredInGrandma)
 * measure(Grandpa.declaredInGrandpa)
 * -- son.declaredInGrandma()         - measured as Son
 * -- nephew.declaredInGrandma()      - measured as Nephew
 * -- son.declaredInGrandpa()         - measured as Son
 * <p>
 * measure(Mother.declaredInGrandma)
 * measure(Mother.declaredInGrandpa)
 * -- son.declaredInGrandma()       - measured as Son
 * -- son.declaredInGrandpa()       - measured as Son
 * -- nephew.declaredInGrandma()    - not measured
 * <p>
 * measure(Father.declaredInFather)
 * measure(Mother.declaredInMother)
 * -- son.declaredInFather()        - measured as Son
 * -- son.declaredInMother()        - measured as Son
 * <p>
 * measure(Son.declaredInGrandma)
 * -- son.declaredInGrandma()        - measured as Son
 * -- nephew.declaredInGrandma()     - not measured
 */
public class InterfaceInterfaceHierarchyTest {

    private Logger logger = Logger.getLogger(InterfaceInterfaceHierarchyTest.class.getSimpleName());
    
    private void execute(Supplier<String> supplier, String expectedRetVal, int repeats) {
        for (int i = 0; i < repeats - 1; i++) {
            supplier.get();
        }

        String retVal = supplier.get();
        assertThat(retVal, is(expectedRetVal));
    }

    @Test
    void measurementOnMultipleGrandParentInterfaceWillBeInheritedByAllChildren() throws JSONException {
        Timer timer = new Timer();
        Grandma grandma = timer.interceptor(Grandma.class);
        timer.measure(() -> grandma.declaredInGrandma(""));
        Grandpa grandpa = timer.interceptor(Grandpa.class);
        timer.measure(() -> grandpa.declaredInGrandpa(""));

        Son son = timer.on(new Son());
        Nephew nephew = timer.on(new Nephew());

        execute(() -> son.declaredInGrandma("test"), "Son.declaredInGrandma-test", 10);
        execute(() -> nephew.declaredInGrandma("test"), "Nephew.declaredInGrandma-test", 11);
        execute(() -> son.declaredInGrandpa("test"), "Son.declaredInGrandpa-test", 12);
        String stats = timer.getStats();
        logger.info("actual:" + stats);
        JSONAssert.assertEquals("[{\"com.github.lkq.timeron.samples.InterfaceInterfaceHierarchyTest$Son.declaredInGrandpa(String)\":{\"total\":12345,\"count\":12,\"avg\":1234}}," +
                        "{\"com.github.lkq.timeron.samples.InterfaceInterfaceHierarchyTest$Nephew.declaredInGrandma(String)\":{\"total\":12345,\"count\":11,\"avg\":1234}}," +
                        "{\"com.github.lkq.timeron.samples.InterfaceInterfaceHierarchyTest$Son.declaredInGrandma(String)\":{\"total\":12345,\"count\":10,\"avg\":1234}}]", stats,
                new CustomComparator(JSONCompareMode.STRICT,
                        new Customization("[0].com.github.lkq.timeron.samples.InterfaceInterfaceHierarchyTest$Son.declaredInGrandpa(String).total", (o1, o2) -> ((int) o2) > 0),
                        new Customization("[0].com.github.lkq.timeron.samples.InterfaceInterfaceHierarchyTest$Son.declaredInGrandpa(String).avg", (o1, o2) -> ((int) o2) > 0),
                        new Customization("[1].com.github.lkq.timeron.samples.InterfaceInterfaceHierarchyTest$Nephew.declaredInGrandma(String).total", (o1, o2) -> ((int) o2) > 0),
                        new Customization("[1].com.github.lkq.timeron.samples.InterfaceInterfaceHierarchyTest$Nephew.declaredInGrandma(String).avg", (o1, o2) -> ((int) o2) > 0),
                        new Customization("[2].com.github.lkq.timeron.samples.InterfaceInterfaceHierarchyTest$Son.declaredInGrandma(String).total", (o1, o2) -> ((int) o2) > 0),
                        new Customization("[2].com.github.lkq.timeron.samples.InterfaceInterfaceHierarchyTest$Son.declaredInGrandma(String).avg", (o1, o2) -> ((int) o2) > 0)
                ));
    }

    @Test
    void measurementOnGrandParentMethodUsingParentInterfaceWillBeInheritedByItsChildren() throws JSONException {
        Timer timer = new Timer();
        Mother mother = timer.interceptor(Mother.class);
        timer.measure(() -> mother.declaredInGrandma(""));
        timer.measure(() -> mother.declaredInGrandpa(""));

        Son son = timer.on(new Son());
        Nephew nephew = timer.on(new Nephew());

        execute(() -> son.declaredInGrandma("test"), "Son.declaredInGrandma-test", 10);
        execute(() -> nephew.declaredInGrandma("test"), "Nephew.declaredInGrandma-test", 11);
        execute(() -> son.declaredInGrandpa("test"), "Son.declaredInGrandpa-test", 12);

        String stats = timer.getStats();
        logger.info("actual:" + stats);
        JSONAssert.assertEquals("[{\"com.github.lkq.timeron.samples.InterfaceInterfaceHierarchyTest$Son.declaredInGrandpa(String)\":{\"total\":12345,\"count\":12,\"avg\":1234}}," +
                        "{\"com.github.lkq.timeron.samples.InterfaceInterfaceHierarchyTest$Son.declaredInGrandma(String)\":{\"total\":12345,\"count\":10,\"avg\":1234}}]", stats,
                new CustomComparator(JSONCompareMode.STRICT,
                        new Customization("[1].com.github.lkq.timeron.samples.InterfaceInterfaceHierarchyTest$Son.declaredInGrandma(String).total", (o1, o2) -> ((int) o2) > 0),
                        new Customization("[1].com.github.lkq.timeron.samples.InterfaceInterfaceHierarchyTest$Son.declaredInGrandma(String).avg", (o1, o2) -> ((int) o2) > 0),
                        new Customization("[0].com.github.lkq.timeron.samples.InterfaceInterfaceHierarchyTest$Son.declaredInGrandpa(String).total", (o1, o2) -> ((int) o2) > 0),
                        new Customization("[0].com.github.lkq.timeron.samples.InterfaceInterfaceHierarchyTest$Son.declaredInGrandpa(String).avg", (o1, o2) -> ((int) o2) > 0)
                ));
    }

    @Test
    void measurementOnParentInterfaceWillBeInheritedByItsChildren() throws JSONException {
        Timer timer = new Timer();
        Father father = timer.interceptor(Father.class);
        Mother mother = timer.interceptor(Mother.class);
        timer.measure(() -> father.declaredInFather(""));
        timer.measure(() -> mother.declaredInMother(""));

        Son son = timer.on(new Son());

        execute(() -> son.declaredInFather("test"), "Son.declaredInFather-test", 10);
        execute(() -> son.declaredInMother("test"), "Son.declaredInMother-test", 11);

        String stats = timer.getStats();
        logger.info("actual:" + stats);
        JSONAssert.assertEquals("[{\"com.github.lkq.timeron.samples.InterfaceInterfaceHierarchyTest$Son.declaredInMother(String)\":{\"total\":12345,\"count\":11,\"avg\":1234}}," +
                        "{\"com.github.lkq.timeron.samples.InterfaceInterfaceHierarchyTest$Son.declaredInFather(String)\":{\"total\":12345,\"count\":10,\"avg\":1234}}]", stats,
                new CustomComparator(JSONCompareMode.STRICT,
                        new Customization("[1].com.github.lkq.timeron.samples.InterfaceInterfaceHierarchyTest$Son.declaredInFather(String).total", (o1, o2) -> ((int) o2) > 0),
                        new Customization("[1].com.github.lkq.timeron.samples.InterfaceInterfaceHierarchyTest$Son.declaredInFather(String).avg", (o1, o2) -> ((int) o2) > 0),
                        new Customization("[0].com.github.lkq.timeron.samples.InterfaceInterfaceHierarchyTest$Son.declaredInMother(String).total", (o1, o2) -> ((int) o2) > 0),
                        new Customization("[0].com.github.lkq.timeron.samples.InterfaceInterfaceHierarchyTest$Son.declaredInMother(String).avg", (o1, o2) -> ((int) o2) > 0)
                ));
    }

    @Test
    void measurementOnParentMethodUsingChildClassWillNotAffectOtherChildren() throws JSONException {
        Timer timer = new Timer();
        Son sonInterceptor = timer.interceptor(Son.class);
        timer.measure(() -> sonInterceptor.declaredInGrandma(""));

        Son son = timer.on(new Son());
        Nephew nephew = timer.on(new Nephew());

        execute(() -> son.declaredInGrandma("test"), "Son.declaredInGrandma-test", 10);
        execute(() -> nephew.declaredInGrandma("test"), "Nephew.declaredInGrandma-test", 11);

        String stats = timer.getStats();
        logger.info("actual:" + stats);
        JSONAssert.assertEquals("[{\"com.github.lkq.timeron.samples.InterfaceInterfaceHierarchyTest$Son.declaredInGrandma(String)\":{\"total\":12345,\"count\":10,\"avg\":1234}}]", stats,
                new CustomComparator(JSONCompareMode.STRICT,
                        new Customization("[0].com.github.lkq.timeron.samples.InterfaceInterfaceHierarchyTest$Son.declaredInGrandma(String).total", (o1, o2) -> ((int) o2) > 0),
                        new Customization("[0].com.github.lkq.timeron.samples.InterfaceInterfaceHierarchyTest$Son.declaredInGrandma(String).avg", (o1, o2) -> ((int) o2) > 0)
                ));
    }

    public interface Grandma {
        String declaredInGrandma(String arg);
    }

    public interface Grandpa {
        String declaredInGrandpa(String arg);
    }

    public interface Mother extends Grandma, Grandpa {
        String declaredInMother(String arg);
    }

    public interface Father {
        String declaredInFather(String arg);
    }

    public interface Aunt extends Grandma {

    }

    public static class Son implements Mother, Father {
        @Override
        public String declaredInGrandma(String arg) {
            return "Son.declaredInGrandma-" + arg;
        }

        @Override
        public String declaredInGrandpa(String arg) {
            return "Son.declaredInGrandpa-" + arg;
        }

        @Override
        public String declaredInMother(String arg) {
            return "Son.declaredInMother-" + arg;
        }

        @Override
        public String declaredInFather(String arg) {
            return "Son.declaredInFather-" + arg;
        }
    }

    public static class Nephew implements Aunt {
        @Override
        public String declaredInGrandma(String arg) {
            return "Nephew.declaredInGrandma-" + arg;
        }
    }
}
