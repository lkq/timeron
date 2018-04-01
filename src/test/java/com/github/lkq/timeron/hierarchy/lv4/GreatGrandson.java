package com.github.lkq.timeron.hierarchy.lv4;

import com.github.lkq.timeron.annotation.Timer;
import com.github.lkq.timeron.hierarchy.lv3.Son;

public class GreatGrandson extends Son {
    public GreatGrandson(String name) {
        super(name);
    }


    @Timer(name = "tagInGrandpa")
    @Override
    public String tagInGrandpa(String arg) {
        return super.tagInGrandpa(arg);
    }
}
