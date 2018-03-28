package com.github.lkq.timeron.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AnnotationFinder {

    public <T extends Annotation> List<T> findAnnotations(Method method, Class<T> annotationClz) {
        if (method == null || annotationClz == null) {
            return Collections.emptyList();
        }
        List<T> annotations = new ArrayList<>();
        T annotation = method.getDeclaredAnnotation(annotationClz);
        if (annotation != null) {
            annotations.add(annotation);
        }
        annotations.addAll(searchAnnotationInClassHierarchy(method.getDeclaringClass().getSuperclass(), method, annotationClz));
        annotations.addAll(searchAnnotationInInterfaceHierarchy(method.getDeclaringClass().getInterfaces(), method, annotationClz));
        return annotations;
    }

    private <T extends Annotation> List<T> searchAnnotationInInterfaceHierarchy(Class<?>[] interfaces, Method method, Class<T> annotationClz) {

        List<T> annotations = new ArrayList<>();
        for (Class<?> iface : interfaces) {
            try {
                Method ifaceMethod = iface.getDeclaredMethod(method.getName(), method.getParameterTypes());
                T annotation = ifaceMethod.getDeclaredAnnotation(annotationClz);
                if (annotation != null) {
                    annotations.add(annotation);
                }
            } catch (NoSuchMethodException ignored) {
            }
            annotations.addAll(searchAnnotationInInterfaceHierarchy(iface.getInterfaces(), method, annotationClz));
        }

        return annotations;
    }

    private <T extends Annotation> List<T> searchAnnotationInClassHierarchy(Class<?> clz, Method method, Class<T> annotationClz) {

        List<T> annotations = new ArrayList<>();
        if (clz != null && clz != Object.class) {
            try {
                Method superMethod = clz.getDeclaredMethod(method.getName(), method.getParameterTypes());
                T annotation = superMethod.getDeclaredAnnotation(annotationClz);
                if (annotation != null) {
                    annotations.add(annotation);
                }
                annotations.addAll(searchAnnotationInClassHierarchy(clz.getSuperclass(), method, annotationClz));
                return annotations;
            } catch (NoSuchMethodException ignored) {

            }
        }
        return annotations;
    }

    public boolean annotatedMethodPresentInClassHierarchy(Class<?> clz, Class<? extends Annotation> annotationClz) {
        for (Method method : clz.getDeclaredMethods()) {
            if (method.getAnnotation(Timer.class) != null) {
                return true;
            }
        }
        Class<?> superclass = clz.getSuperclass();
        if (superclass != null) {
            return annotatedMethodPresentInClassHierarchy(superclass, annotationClz);
        }
        return false;
    }
}
