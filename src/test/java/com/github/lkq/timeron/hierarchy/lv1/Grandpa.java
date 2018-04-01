package com.github.lkq.timeron.hierarchy.lv1;

import com.github.lkq.timeron.annotation.Timer;

public interface Grandpa {

    @Timer(name = "tagInGrandpa")
    String tagInGrandpa(String arg);

    String fromGrandpaTagInSon(String arg);
}
