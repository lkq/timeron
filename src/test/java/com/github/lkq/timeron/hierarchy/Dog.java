package com.github.lkq.timeron.hierarchy;

public class Dog extends Pet implements Barkable {
    public String petDogNotAnnotated(String arg) {
        return arg;
    }

    @Override
    public void wow() {

    }
}
