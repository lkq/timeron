package com.github.lkq.timeron.hierarchy.lv2;

import com.github.lkq.timeron.hierarchy.lv1.Grandpa;

public interface Father extends Grandpa {

    String tagInFather(String arg);

    String fromFatherTagInSon(String arg);
}
