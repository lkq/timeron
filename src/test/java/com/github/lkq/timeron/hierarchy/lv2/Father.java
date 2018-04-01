package com.github.lkq.timeron.hierarchy.lv2;

import com.github.lkq.timeron.annotation.Timer;
import com.github.lkq.timeron.hierarchy.lv1.Grandpa;

public interface Father extends Grandpa {

    @Timer(name = "tagInFather")
    String tagInFather(String arg);

    String fromFatherTagInSon(String arg);
}
