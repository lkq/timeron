package com.github.lkq.timeron.config;

import com.github.lkq.timeron.TimerException;
import com.github.lkq.timeron.measure.InvocationTimer;
import com.github.lkq.timeron.measure.TimerConfig;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class Interceptor implements MethodInterceptor {

    private TimerConfig timerConfig;

    private boolean interceptInProgress;

    private Method method;

    public Interceptor(TimerConfig timerConfig) {
        this.timerConfig = timerConfig;
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        this.method = method;
        this.interceptInProgress = true;
        return null;
    }

    public void finishInterception() {
        if (!interceptInProgress) {
            throw new TimerException("interception not started");
        }
        timerConfig.addTimer(method, new InvocationTimer());
        interceptInProgress = false;
    }

    public TimerConfig getTimerConfig() {
        return timerConfig;
    }
}
