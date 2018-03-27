package com.github.lkq.timeron.family;

import com.github.lkq.timeron.annotation.Timer;

public class Mother {

    @Timer(name = "motherAnnotated")
    String motherAnnotated(String arg) {
        return arg;
    }

    String motherNotAnnotated(String arg) {
        return arg;
    }
}
