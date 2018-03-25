package com.github.lkq.timeron.family;

import com.github.lkq.timeron.annotation.Timer;

public interface Mother {

    @Timer(name = "motherAnnotated")
    String motherAnnotated(String arg);

    String motherNotAnnotated(String arg);
}
