package com.github.lkq.timeron.proxy;

import com.github.lkq.timeron.TimerProxy;

public class CGLIBProxy<T> implements TimerProxy<T> {
    private T target;

    public CGLIBProxy(T target) {
        this.target = target;
    }

    @Override
    public T getProxy() {
        // TODO: pending implementation
        return target;
    }
}
