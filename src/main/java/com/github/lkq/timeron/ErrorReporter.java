package com.github.lkq.timeron;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ErrorReporter {
    private static Logger logger = Logger.getLogger(ErrorReporter.class.getSimpleName());

    public static void missingMethodInvocation() {
        logger.log(Level.SEVERE, "measure() requires an argument which has to be 'a method call on a object returned by intercept()'");
    }

    public static void missingMeasurementConfig(Class clz) {
        logger.log(Level.SEVERE, "no measurement defined in class or its ancestors: " + clz.getName());
    }
}
