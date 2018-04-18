package com.github.lkq.timeron.intercept;

import com.github.lkq.timeron.TimerException;

import java.lang.reflect.Method;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Interceptor {
    private static Logger logger = Logger.getLogger(Interceptor.class.getSimpleName());

    private Map<Class, List<Method>> interceptedMethods = new HashMap<>();
    private MethodExtractor methodExtractor;

    private Method interceptingMethod;

    public Interceptor(MethodExtractor methodExtractor) {
        this.methodExtractor = methodExtractor;
    }

    public void startIntercept(Method method) {
        if (this.interceptingMethod != null) {
            throw new TimerException("unfinished interception detected on " + this.interceptingMethod);
        }
        logger.log(Level.INFO, "start intercepting method: " + method.toString());
        this.interceptingMethod = method;
    }

    public void completeIntercept() {
        if (this.interceptingMethod == null) {
            throw new TimerException("no interception in progress");
        }
        Class<?> clz = interceptingMethod.getDeclaringClass();
        List<Method> methods = this.interceptedMethods.computeIfAbsent(clz, k -> new ArrayList<>());
        methods.add(interceptingMethod);
        logger.log(Level.INFO, "finished intercepting method: " + this.interceptingMethod.toString());
        this.interceptingMethod = null;
    }

    public List<MeasuredMethod> getMeasuredMethods(Class<?> clz) throws NoSuchMethodException {
        return methodExtractor.extract(clz, this.interceptedMethods);
    }

}
