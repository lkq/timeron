package com.github.lkq.timeron.config;

import com.github.lkq.timeron.hierarchy.lv3.Son;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class MethodExtractorTest {

    private MethodExtractor methodExtractor;

    @BeforeEach
    void setUp() {
        methodExtractor = new MethodExtractor();
    }

    @Disabled("pending implementation")
    @Test
    void canReturnInterceptedMethodForClass() throws NoSuchMethodException {

        Method[] methods = methodExtractor.extractInterceptedMethods(Son.class).toArray(new Method[0]);

        Method[] expectedMethods = new Method[]{
                Son.class.getDeclaredMethod("tagInSon", String.class),
        };

        assertArrayEquals(methods, expectedMethods);
    }

    @Test
    void canCacheExtractedMethodsFromParentClass() {

    }

    @Test
    void canCacheExtractedMethodsFromParentInterface() {

    }
}