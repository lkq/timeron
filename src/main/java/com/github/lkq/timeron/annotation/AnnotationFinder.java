package com.github.lkq.timeron.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class AnnotationFinder {

    public <T extends Annotation> List<T> findAnnotations(Method method, Class<T> annotationClz) {
        if (method == null || annotationClz == null) {
            return Collections.emptyList();
        }
        T annotation = method.getDeclaredAnnotation(annotationClz);
        if (annotation != null) {
            return Arrays.asList(annotation);
        } else {
            return searchParent(method, annotationClz);
        }
    }

    private <T extends Annotation> List<T> searchParent(Method method, Class<T> annotationClz) {

        // TODO: pending implementation
        return Collections.emptyList();
    }
}
