package com.github.lkq.timeron.hierarchy.lv2;

import com.github.lkq.timeron.hierarchy.lv1.Grandma;

public abstract class Mother implements Grandma {

    public String implInMother(String arg) {
        return "implInMother-" + arg;
    }

    @Override
    public String tagInGrandmaTagInMother(String arg) {
        return "tagInGrandmaTagInMother-" + arg;
    }

    public String tagInMotherTagInSon(String arg) {
        return "tagInMotherTagInSon-" + arg;
    }

    public String fromMotherImplInSon(String arg) {
        return "fromMotherImplInSon-" + arg;
    }

    public abstract String tagInMotherImplInSon(String arg);

    public String fromMotherTagInGreatGrandson(String arg) {
        return "fromMotherTagInGreatGrandson-" + arg;
    }
}
