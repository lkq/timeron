package com.github.lkq.timeron.family;

import com.github.lkq.timeron.annotation.Timer;

public interface Uncle extends Grandpa {

    @Timer(name = "grandpaAnnotatedUncleReTag")
    @Override
    String grandpaAnnotated(String arg);

    @Override
    String grandpaNotAnnotated(String arg);
}
