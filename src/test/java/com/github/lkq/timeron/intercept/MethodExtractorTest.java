package com.github.lkq.timeron.intercept;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.mockito.MockitoAnnotations.initMocks;

class MethodExtractorTest {

    private MethodExtractor methodExtractor;
    private Map<Class, List<Method>> interceptedMethods = new HashMap<>();

    @BeforeEach
    void setUp() throws NoSuchMethodException {
        initMocks(this);
        methodExtractor = new MethodExtractor();
    }

    private void runTest(Class<Son> clz, Method[] expectedMethods) {
        Method[] methods = methodExtractor.interceptedMethods(clz, interceptedMethods).toArray(new Method[0]);
        assertArrayEquals(methods, expectedMethods);
    }

    @Test
    void canExtractDeclaredMethod() throws NoSuchMethodException {
        Method implInSon = Son.class.getDeclaredMethod("implInSon", String.class);

        interceptedMethods.put(Son.class, Arrays.asList(implInSon));
        runTest(Son.class, new Method[]{implInSon});
    }

    @Disabled("pending implementation")
    @Test
    void canExtractInheritedMethod() throws NoSuchMethodException {

        Method implInMother = Mother.class.getDeclaredMethod("implInMother", String.class);

        interceptedMethods.put(Mother.class, Arrays.asList(implInMother));
        runTest(Son.class, new Method[]{implInMother});
    }

    @Test
    void canExtractOverrideMethod() throws NoSuchMethodException {
        Method motherMethod = Mother.class.getDeclaredMethod("implInMotherOverrideInSon", String.class);
        Method sonMethod = Son.class.getDeclaredMethod("implInMotherOverrideInSon", String.class);

        interceptedMethods.put(Mother.class, Arrays.asList(motherMethod));
        interceptedMethods.put(Son.class, Arrays.asList(sonMethod));
        runTest(Son.class, new Method[]{sonMethod});
    }

    @Disabled("pending implementation")
    @Test
    void canExtractOverrideMethodFromParentClass() throws NoSuchMethodException {
        Method motherMethod = Mother.class.getDeclaredMethod("implInMotherOverrideInSon", String.class);

        interceptedMethods.put(Mother.class, Arrays.asList(motherMethod));
        runTest(Son.class, new Method[]{motherMethod});
    }

    @Test
    void canExtractImplementedAbstractMethod() throws NoSuchMethodException {
        Method sonMethod = Son.class.getDeclaredMethod("declaredInMotherImplInSon", String.class);
        Method motherMethod = Mother.class.getDeclaredMethod("declaredInMotherImplInSon", String.class);

        interceptedMethods.put(Son.class, Arrays.asList(sonMethod));
        interceptedMethods.put(Mother.class, Arrays.asList(motherMethod));
        runTest(Son.class, new Method[]{sonMethod});
    }

    @Disabled("pending implementation")
    @Test
    void canExtractImplementedAbstractMethodFromParentClass() throws NoSuchMethodException {
        Method motherMethod = Mother.class.getDeclaredMethod("declaredInMotherImplInSon", String.class);

        interceptedMethods.put(Mother.class, Arrays.asList(motherMethod));
        runTest(Son.class, new Method[]{motherMethod});
    }

    @Test
    void canExtractImplementedInterfaceMethod() throws NoSuchMethodException {
        Method grandmaMethod = Son.class.getDeclaredMethod("declaredInGrandmaImplInSon", String.class);

        interceptedMethods.put(Son.class, Arrays.asList(grandmaMethod));
        runTest(Son.class, new Method[]{grandmaMethod});
    }

    @Disabled("pending implementation")
    @Test
    void canExtractImplementedInterfaceMethodFromParentInterface() throws NoSuchMethodException {
        Method grandmaMethod = Grandma.class.getDeclaredMethod("declaredInGrandmaImplInSon", String.class);

        interceptedMethods.put(Grandma.class, Arrays.asList(grandmaMethod));
        runTest(Son.class, new Method[]{grandmaMethod});
    }

    @Test
    void canExtractOverrideInterfaceMethod() throws NoSuchMethodException {
        Method grandmaMethod = Son.class.getDeclaredMethod("declaredInGrandMaImplInMotherOverrideInSon", String.class);

        interceptedMethods.put(Son.class, Arrays.asList(grandmaMethod));
        runTest(Son.class, new Method[]{grandmaMethod});
    }

    @Disabled("pending implementation")
    @Test
    void canExtractOverrideInterfaceMethodFromParentInterface() throws NoSuchMethodException {
        Method grandmaMethod = Grandma.class.getDeclaredMethod("declaredInGrandMaImplInMotherOverrideInSon", String.class);

        interceptedMethods.put(Grandma.class, Arrays.asList(grandmaMethod));
        runTest(Son.class, new Method[]{grandmaMethod});
    }

    public interface Grandma {
        String declaredInGrandMaImplInMotherOverrideInSon(String arg);
        String declaredInGrandmaImplInSon(String arg);
    }

    public static abstract class Mother implements Grandma {
        public abstract String declaredInMotherImplInSon(String arg);
        public String implInMother(String arg) {
            return "implInMother-" + arg;
        }

        public String implInMotherOverrideInSon(String arg) {
            return "implInMotherOverrideInSon-" + arg;
        }

        @Override
        public String declaredInGrandMaImplInMotherOverrideInSon(String arg) {
            return "Mother.declaredInGrandMaImplInMotherOverrideInSon-" + arg;
        }
    }

    public static class Son extends Mother {

        public String implInSon(String arg) {
            return "implInSon-" + arg;
        }

        @Override
        public String declaredInMotherImplInSon(String arg) {
            return "Son.declaredInMotherImplInSon-" + arg;
        }

        @Override
        public String implInMotherOverrideInSon(String arg) {
            return "Son.implInMotherOverrideInSon-" + arg;
        }

        @Override
        public String declaredInGrandmaImplInSon(String arg) {
            return "Son.declaredInGrandmaImplInSon-" + arg;
        }

        @Override
        public String declaredInGrandMaImplInMotherOverrideInSon(String arg) {
            return "Son.declaredInGrandMaImplInMotherOverrideInSon-" + arg;
        }
    }
}