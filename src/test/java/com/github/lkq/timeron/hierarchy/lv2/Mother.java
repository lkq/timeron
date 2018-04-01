package com.github.lkq.timeron.hierarchy.lv2;

import com.github.lkq.timeron.annotation.Timer;
import com.github.lkq.timeron.hierarchy.lv1.Grandma;

public abstract class Mother implements Grandma {

    @Timer(name = "tagInMother")
    public String tagInMother(String arg) {
        return arg;
    }

    @Timer(name = "tagInGrandmaTagInMother")
    @Override
    public String tagInGrandmaTagInMother(String arg) {
        return "tagInGrandmaTagInMother-" + arg;
    }

    @Timer(name = "tagInMotherTagInSon")
    public String tagInMotherTagInSon(String arg) {
        return "tagInMotherTagInSon-" + arg;
    }

    @Override
    public String fromMotherTagInSon(String arg) {
        return "fromMotherTagInSon-" + arg;
    }

    public String fromMotherTagInGreatGrandson(String arg) {
        return "fromMotherTagInGreatGrandson-" + arg;
    }
}
