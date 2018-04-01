package com.github.lkq.timeron.config;

import java.lang.reflect.Method;

public class InterceptContext {
    private boolean interceptInProgress;
    public void interceptBegin(Method method) {
        interceptInProgress = true;
    }

    public void interceptEnd(String name) {
        interceptInProgress = false;
    }
}
