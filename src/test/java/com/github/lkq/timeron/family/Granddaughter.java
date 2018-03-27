package com.github.lkq.timeron.family;

import com.github.lkq.timeron.annotation.Timer;

public class Granddaughter extends Son {
    public Granddaughter(String name) {
        super(name);
    }

    @Timer(name = "granddaughterAnnotated")
    public String granddaughterAnnotated(String arg) {
        return arg;
    }
}
