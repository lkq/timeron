package com.github.lkq.timeron.family;

import com.github.lkq.timeron.annotation.Timer;

public interface Grandpa {

    @Timer(name = "grandpaAnnotated")
    String grandpaAnnotated(String arg);

    String grandpaNotAnnotated(String arg);
}
