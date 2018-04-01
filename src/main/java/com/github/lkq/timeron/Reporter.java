package com.github.lkq.timeron;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Reporter {
    private static Logger logger = Logger.getLogger(Reporter.class.getName());

    public void missingMethodInvocation() {
        logger.log(Level.SEVERE, "measure() requires an argument which has to be 'a method call on a object returned by intercept()'");
    }
}
