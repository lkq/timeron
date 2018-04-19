package com.github.lkq.timeron.intercept;

import com.github.lkq.timeron.TimerException;

import java.lang.reflect.Method;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Interceptor {
    private static Logger logger = Logger.getLogger(Interceptor.class.getSimpleName());

    private Map<Class, List<MeasuredMethod>> interceptedMethods = new HashMap<>();
    private MethodExtractor methodExtractor;

    private MeasuredMethod interceptedMethodInProgress;

    public Interceptor(MethodExtractor methodExtractor) {
        this.methodExtractor = methodExtractor;
    }

    public void startIntercept(Class<?> clz, Method method) throws NoSuchMethodException {
        if (this.interceptedMethodInProgress != null) {
            throw new TimerException("unfinished interception detected on " + this.interceptedMethodInProgress.signature());
        }
        if (clz == null || clz.equals(Object.class)) {
            clz = method.getDeclaringClass();
            if (clz.equals(Object.class)) {
                return;
            }
        }
        logger.log(Level.INFO, "start intercepting method: " + clz.getName() + "." + method.toString());
        this.interceptedMethodInProgress = new MeasuredMethod(clz, method);
    }

    public void completeIntercept() {
        if (this.interceptedMethodInProgress == null) {
            throw new TimerException("no interception in progress");
        }
        Class<?> clz = interceptedMethodInProgress.clz();
        List<MeasuredMethod> methods = this.interceptedMethods.computeIfAbsent(clz, k -> new ArrayList<>());
        methods.add(interceptedMethodInProgress);
        logger.log(Level.INFO, "finished intercepting method: " + this.interceptedMethodInProgress.signature());
        this.interceptedMethodInProgress = null;
    }

    public List<MeasuredMethod> getMeasuredMethods(Class<?> clz) throws NoSuchMethodException {
        return methodExtractor.extract(clz, this.interceptedMethods);
    }

}
