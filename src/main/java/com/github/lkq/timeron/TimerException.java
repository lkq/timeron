package com.github.lkq.timeron;

public class TimerException extends RuntimeException {
    public TimerException(String msg) {
        super(msg);
    }

    public TimerException(String msg, Exception cause) {
        super(msg, cause);
    }
}
