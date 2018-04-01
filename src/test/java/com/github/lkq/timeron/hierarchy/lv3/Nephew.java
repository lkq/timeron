package com.github.lkq.timeron.hierarchy.lv3;

import com.github.lkq.timeron.hierarchy.lv2.Uncle;

public class Nephew implements Uncle {
    @Override
    public String tagInGrandpa(String arg) {
        return arg;
    }

    @Override
    public String fromGrandpaTagInSon(String arg) {
        return "fromGrandpaTagInSon-" + arg;
    }

}
