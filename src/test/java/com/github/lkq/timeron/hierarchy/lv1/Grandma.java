package com.github.lkq.timeron.hierarchy.lv1;

import com.github.lkq.timeron.annotation.Timer;

public interface Grandma {
    @Timer(name = "tagInGrandma")
    String tagInGrandma(String arg);

    @Timer(name = "tagInGrandmaTagInMother")
    String tagInGrandmaTagInMother(String arg);

    @Timer(name = "tagInGrandmaTagInSon")
    String tagInGrandmaTagInSon(String arg);

    String fromMotherTagInSon(String arg);

}
