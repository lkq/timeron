package com.github.lkq.timeron.hierarchy.lv4;

import com.github.lkq.timeron.hierarchy.lv3.Son;

public class Granddaughter extends Son {
    public Granddaughter(String name) {
        super(name);
    }

    public String granddaughterAnnotated(String arg) {
        return arg;
    }
}
