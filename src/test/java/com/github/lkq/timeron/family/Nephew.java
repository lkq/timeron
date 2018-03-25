package com.github.lkq.timeron.family;

public class Nephew implements Uncle {
    @Override
    public String grandpaAnnotated(String arg) {
        return arg;
    }

    @Override
    public String grandpaNotAnnotated(String arg) {
        return arg;
    }
}
