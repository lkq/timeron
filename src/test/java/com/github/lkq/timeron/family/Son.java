package com.github.lkq.timeron.family;


import com.github.lkq.timeron.annotation.Timer;

import java.util.logging.Logger;

public class Son extends Mother implements Father {
    private static Logger logger = Logger.getLogger(Son.class.getName());

    public Son(String name) {
        logger.info("creating son " + name);
    }

    private void delay(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ignored) {
        }
    }

    @Timer(name = "sonAnnotated")
    public String sonAnnotated(String arg) {
        return arg;
    }

    @Timer(name = "sonAnnotatedToBeOverrideWithoutReTag")
    public String sonAnnotatedToBeOverrideWithoutReTag(String arg) {
        return arg;
    }

    @Timer(name = "sonAnnotatedToBeOverrideAndReTag")
    public String sonAnnotatedToBeOverrideAndReTag(String arg) {
        return arg;
    }

    @Timer(name = "sonAnnotatedNotToBeOverride")
    final public String sonAnnotatedNotToBeOverride(String arg) {
        return arg;
    }

    public String sonNotAnnotated(String arg) {
        return arg;
    }

    @Override
    public String fatherAnnotated(String arg) {
        return arg;
    }

    @Override
    public String fatherNotAnnotated(String arg) {
        return arg;
    }

    @Override
    public String grandpaAnnotated(String arg) {
        return arg;
    }

    @Override
    public String grandpaNotAnnotated(String arg) {
        return arg;
    }

    @Timer(name = "motherAnnotatedSonReTag")
    @Override
    public String motherAnnotated(String arg) {
        return arg;
    }

    @Override
    public String motherNotAnnotated(String arg) {
        return arg;
    }
}
