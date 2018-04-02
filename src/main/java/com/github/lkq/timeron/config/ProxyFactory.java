package com.github.lkq.timeron.config;

import com.github.lkq.timeron.measure.TimerConfig;
import com.github.lkq.timeron.proxy.CGLIBProxy;

public class ProxyFactory {
    public <T> T create(T target, TimerConfig timerConfig) {
        return new CGLIBProxy<>(target, timerConfig).getProxy();
    }
}
