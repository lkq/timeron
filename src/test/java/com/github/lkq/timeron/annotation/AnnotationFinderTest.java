package com.github.lkq.timeron.annotation;

import com.github.lkq.timeron.family.Grandson;
import com.github.lkq.timeron.family.Nephew;
import com.github.lkq.timeron.family.Son;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class AnnotationFinderTest {

    AnnotationFinder annotationFinder;

    @BeforeEach
    void setUp() {
        annotationFinder = new AnnotationFinder();
    }

    @Test
    void willReturnNullIfTargetOrAnnotationClassIsNull() {
        List<Annotation> annotation = annotationFinder.findAnnotations(null, null);
        assertThat(annotation.size(), is(0));
    }

    @Test
    void canFindAnnotationFromDeclaredMethod() throws NoSuchMethodException {
        Method method = Son.class.getDeclaredMethod("sonAnnotated", String.class);
        List<Timer> annotations = annotationFinder.findAnnotations(method, Timer.class);
        assertThat(annotations.size(), is(1));
        assertThat(annotations.get(0).name(), is("sonAnnotated"));
    }

    @Test
    void canFindAnnotationWhenAnnotatedInParentClassMethodAndNotOverrideInChild() throws NoSuchMethodException {
        Method method = Grandson.class.getMethod("sonAnnotatedNotToBeOverride", String.class);
        List<Timer> annotations = annotationFinder.findAnnotations(method, Timer.class);
        assertThat(annotations.size(), is(1));
        assertThat(annotations.get(0).name(), is("sonAnnotatedNotToBeOverride"));
    }

    @Test
    void canFindAnnotationWhenAnnotatedInParentClassMethodAndBeingOverrideInChildWithoutReTag() throws NoSuchMethodException {
        Method method = Grandson.class.getMethod("sonAnnotatedToBeOverrideWithoutReTag", String.class);
        List<Timer> annotations = annotationFinder.findAnnotations(method, Timer.class);
        assertThat(annotations.size(), is(1));
        assertThat(annotations.get(0).name(), is("sonAnnotatedToBeOverrideWithoutReTag"));
    }

    @Test
    void canFindAnnotationsWhenAnnotatedInParentClassMethodAndBeingOverrideInChildAndReTag() throws NoSuchMethodException {
        Method method = Grandson.class.getMethod("motherAnnotated", String.class);
        List<Timer> annotations = annotationFinder.findAnnotations(method, Timer.class);
        assertThat(annotations.size(), is(2));
        assertThat(annotations.get(0).name(), is("motherAnnotatedSonReTag"));
        assertThat(annotations.get(1).name(), is("motherAnnotated"));
    }

    @Test
    void canFindAnnotationFromParentInterfaceMethod() throws NoSuchMethodException {
        Method method = Son.class.getDeclaredMethod("fatherAnnotated", String.class);
        List<Timer> annotations = annotationFinder.findAnnotations(method, Timer.class);
        assertThat(annotations.size(), is(1));
        assertThat(annotations.get(0).name(), is("fatherAnnotated"));
    }

    @Test
    void canFindAnnotationFromGrandParentInterfaceMethod() throws NoSuchMethodException {
        Method method = Son.class.getDeclaredMethod("grandpaAnnotated", String.class);
        List<Timer> annotations = annotationFinder.findAnnotations(method, Timer.class);
        assertThat(annotations.size(), is(1));
        assertThat(annotations.get(0).name(), is("grandpaAnnotated"));
    }

    @Test
    void canFindAnnotationsWhenAnnotatedInGrandParentInterfaceMethodAndOverrideInChildInterfaceWithReTag() throws NoSuchMethodException {
        Method method = Nephew.class.getDeclaredMethod("grandpaAnnotated", String.class);
        List<Timer> annotations = annotationFinder.findAnnotations(method, Timer.class);
        assertThat(annotations.size(), is(2));
        assertThat(annotations.get(0).name(), is("grandpaAnnotatedUncleReTag"));
        assertThat(annotations.get(1).name(), is("grandpaAnnotated"));
    }

    @Test
    void canNotFindAnnotationFromAllParentsIfNeverTagged() throws NoSuchMethodException {
        Method method = Son.class.getDeclaredMethod("grandpaNotAnnotated", String.class);
        List<Timer> annotations = annotationFinder.findAnnotations(method, Timer.class);
        assertThat(annotations.size(), is(0));
    }
}