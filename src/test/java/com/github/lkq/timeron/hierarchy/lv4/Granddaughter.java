package com.github.lkq.timeron.hierarchy.lv4;

import com.github.lkq.timeron.annotation.Timer;
import com.github.lkq.timeron.hierarchy.lv3.Son;

public class Granddaughter extends Son {
    public Granddaughter(String name) {
        super(name);
    }

    @Timer(name = "granddaughterAnnotated")
    public String granddaughterAnnotated(String arg) {
        return arg;
    }
}
