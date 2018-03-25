package com.github.lkq.timeron.family;

import com.github.lkq.timeron.annotation.Timer;

public interface Father extends Grandpa {

    @Timer(name = "fatherAnnotated")
    String fatherAnnotated(String arg);

    String fatherNotAnnotated(String arg);
}
