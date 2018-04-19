package com.github.lkq.timeron.intercept;

import org.junit.jupiter.api.BeforeEach;
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
    private Map<Class, List<MeasuredMethod>> interceptedMethods = new HashMap<>();

    @BeforeEach
    void setUp() throws NoSuchMethodException {
        initMocks(this);
        methodExtractor = new MethodExtractor();
    }

    private void runTest(Class<Son> clz, MeasuredMethod[] expectedMethods, Map<Class, List<MeasuredMethod>> interceptedMethods) throws NoSuchMethodException {
        List<MeasuredMethod> methods = methodExtractor.extract(clz, interceptedMethods);
        assertArrayEquals(methods.toArray(new MeasuredMethod[0]), expectedMethods);
    }

    @Test
    void canExtractDeclaredMethod() throws NoSuchMethodException {
        Method implInSon = Son.class.getDeclaredMethod("implInSon", String.class);

        interceptedMethods.put(Son.class, Arrays.asList(new MeasuredMethod(Son.class, implInSon)));
        runTest(Son.class, new MeasuredMethod[]{new MeasuredMethod(Son.class, implInSon)}, interceptedMethods);
    }

    @Test
    void canExtractInheritedMethod() throws NoSuchMethodException {

        Method implInMother = Mother.class.getDeclaredMethod("implInMother", String.class);

        interceptedMethods.put(Mother.class, Arrays.asList(new MeasuredMethod(Mother.class, implInMother)));
        runTest(Son.class, new MeasuredMethod[]{new MeasuredMethod(Son.class, implInMother)}, interceptedMethods);
    }

    @Test
    void canExtractOverrideMethod() throws NoSuchMethodException {
        Method motherMethod = Mother.class.getDeclaredMethod("implInMotherOverrideInSon", String.class);
        Method sonMethod = Son.class.getDeclaredMethod("implInMotherOverrideInSon", String.class);

        interceptedMethods.put(Mother.class, Arrays.asList(new MeasuredMethod(Mother.class, motherMethod)));
        interceptedMethods.put(Son.class, Arrays.asList(new MeasuredMethod(Son.class, sonMethod)));
        runTest(Son.class, new MeasuredMethod[]{
                new MeasuredMethod(Son.class, sonMethod),
                new MeasuredMethod(Son.class, motherMethod)
        }, interceptedMethods);
    }

    @Test
    void canExtractOverrideMethodFromParentClass() throws NoSuchMethodException {
        Method motherMethod = Mother.class.getDeclaredMethod("implInMotherOverrideInSon", String.class);

        interceptedMethods.put(Mother.class, Arrays.asList(new MeasuredMethod(Mother.class, motherMethod)));
        runTest(Son.class, new MeasuredMethod[]{new MeasuredMethod(Son.class, motherMethod)}, interceptedMethods);
    }

    @Test
    void canExtractImplementedAbstractMethod() throws NoSuchMethodException {
        Method sonMethod = Son.class.getDeclaredMethod("declaredInMotherImplInSon", String.class);
        Method motherMethod = Mother.class.getDeclaredMethod("declaredInMotherImplInSon", String.class);

        interceptedMethods.put(Son.class, Arrays.asList(new MeasuredMethod(Son.class, sonMethod)));
        interceptedMethods.put(Mother.class, Arrays.asList(new MeasuredMethod(Mother.class, motherMethod)));
        runTest(Son.class, new MeasuredMethod[]{
                new MeasuredMethod(Son.class, sonMethod),
                new MeasuredMethod(Son.class, motherMethod)
        }, interceptedMethods);
    }

    @Test
    void canExtractImplementedAbstractMethodFromParentClass() throws NoSuchMethodException {
        Method motherMethod = Mother.class.getDeclaredMethod("declaredInMotherImplInSon", String.class);

        interceptedMethods.put(Mother.class, Arrays.asList(new MeasuredMethod(Mother.class, motherMethod)));
        runTest(Son.class, new MeasuredMethod[]{new MeasuredMethod(Son.class, motherMethod)}, interceptedMethods);
    }

    @Test
    void canExtractImplementedInterfaceMethod() throws NoSuchMethodException {
        Method grandmaMethod = Son.class.getDeclaredMethod("declaredInGrandmaImplInSon", String.class);

        interceptedMethods.put(Son.class, Arrays.asList(new MeasuredMethod(Son.class, grandmaMethod)));
        runTest(Son.class, new MeasuredMethod[]{new MeasuredMethod(Son.class, grandmaMethod)}, interceptedMethods);
    }

    @Test
    void canExtractImplementedInterfaceMethodFromParentInterface() throws NoSuchMethodException {
        Method grandmaMethod = Grandma.class.getDeclaredMethod("declaredInGrandmaImplInSon", String.class);

        interceptedMethods.put(Grandma.class, Arrays.asList(new MeasuredMethod(Grandma.class, grandmaMethod)));
        runTest(Son.class, new MeasuredMethod[]{new MeasuredMethod(Son.class, grandmaMethod)}, interceptedMethods);
    }

    @Test
    void canExtractOverrideInterfaceMethod() throws NoSuchMethodException {
        Method grandmaMethod = Son.class.getDeclaredMethod("declaredInGrandMaImplInMotherOverrideInSon", String.class);

        interceptedMethods.put(Son.class, Arrays.asList(new MeasuredMethod(Son.class, grandmaMethod)));
        runTest(Son.class, new MeasuredMethod[]{new MeasuredMethod(Son.class, grandmaMethod)}, interceptedMethods);
    }

    @Test
    void canExtractOverrideInterfaceMethodFromParentInterface() throws NoSuchMethodException {
        Method grandmaMethod = Grandma.class.getDeclaredMethod("declaredInGrandMaImplInMotherOverrideInSon", String.class);

        interceptedMethods.put(Grandma.class, Arrays.asList(new MeasuredMethod(Grandma.class, grandmaMethod)));
        runTest(Son.class, new MeasuredMethod[]{new MeasuredMethod(Son.class, grandmaMethod)}, interceptedMethods);
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