package com.github.lkq.timeron.config;

import com.github.lkq.timeron.TimerException;
import com.github.lkq.timeron.measure.TimeRecorder;
import com.github.lkq.timeron.measure.TimeRecorders;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class Interceptor implements MethodInterceptor {

    private TimeRecorders timeRecorders;

    private boolean interceptInProgress;

    private Method method;

    public Interceptor(TimeRecorders timeRecorders) {
        this.timeRecorders = timeRecorders;
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        if (interceptInProgress) {
            throw new TimerException("unfinished interception detected on " + this.method);
        }
        this.method = method;
        this.interceptInProgress = true;
        return null;
    }

    public void finishInterception() {
        if (!interceptInProgress) {
            throw new TimerException("interception not started");
        }
        timeRecorders.addTimer(method, new TimeRecorder());
        interceptInProgress = false;
    }

    public TimeRecorders getTimeRecorders() {
        return timeRecorders;
    }
}
