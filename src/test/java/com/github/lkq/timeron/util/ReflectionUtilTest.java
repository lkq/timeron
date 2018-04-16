package com.github.lkq.timeron.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

class ReflectionUtilTest {

    @Test
    void returnTrueForSameMethod() throws NoSuchMethodException {
        Method implInSon = Son.class.getDeclaredMethod("implInSon", String.class);
        Method other = Son.class.getDeclaredMethod("implInSon", String.class);
        Assertions.assertTrue(ReflectionUtil.methodSignatureEquals(implInSon, other));
    }

    @Test
    void returnTrueForOverrideMethod() throws NoSuchMethodException {
        Method mother = Mother.class.getDeclaredMethod("implInMotherOverrideInSon", String.class);
        Method son = Son.class.getDeclaredMethod("implInMotherOverrideInSon", String.class);
        Assertions.assertTrue(ReflectionUtil.methodSignatureEquals(mother, son));
    }

    @Test
    void returnTrueForImplementedAbstractMethod() throws NoSuchMethodException {
        Method mother = Mother.class.getDeclaredMethod("declaredInMotherImplInSon", String.class);
        Method son = Son.class.getDeclaredMethod("declaredInMotherImplInSon", String.class);
        Assertions.assertTrue(ReflectionUtil.methodSignatureEquals(mother, son));
    }

    @Test
    void returnTrueForImplementedInterfaceMethod() throws NoSuchMethodException {
        Method mother = Grandma.class.getDeclaredMethod("declaredInGrandMaImplInSon", String.class);
        Method son = Son.class.getDeclaredMethod("declaredInGrandMaImplInSon", String.class);
        Assertions.assertTrue(ReflectionUtil.methodSignatureEquals(mother, son));
    }

    @Test
    void returnFalseForMethodInSameClassWithDifferentParamType() throws NoSuchMethodException {
        Method implInSon = Son.class.getDeclaredMethod("implInSon", String.class);
        Method other = Son.class.getDeclaredMethod("implInSon", Integer.class);
        Assertions.assertFalse(ReflectionUtil.methodSignatureEquals(implInSon, other));
    }

    @Test
    void returnFalseForMethodInTwoDifferentClassWithSameSignature() throws NoSuchMethodException {
        Method implInSon = Son.class.getDeclaredMethod("implInSon", String.class);
        Method dog = Dog.class.getDeclaredMethod("implInSon", String.class);
        Assertions.assertFalse(ReflectionUtil.methodSignatureEquals(implInSon, dog));
    }

    interface Grandma {
        String declaredInGrandMaImplInSon(String arg);
    }

    public static abstract class Mother implements Grandma{
        public abstract String declaredInMotherImplInSon(String arg);
        public String implInMotherOverrideInSon(String arg) {
            return "implInMotherOverrideInSon-" + arg;
        }
    }

    public static class Son extends Mother {
        public String implInSon(String arg) {
            return "implInSon-" + arg;
        }
        public String implInSon(Integer arg) {
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
        public String declaredInGrandMaImplInSon(String arg) {
            return "Son.declaredInGrandMaImplInSon-" + arg;
        }
    }
    public static class Dog {
        public String implInSon(String arg) {
            return "implInSon-" + arg;
        }
    }
}