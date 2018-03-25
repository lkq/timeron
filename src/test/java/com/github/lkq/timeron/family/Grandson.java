package com.github.lkq.timeron.family;

import com.github.lkq.timeron.annotation.Timer;

public class Grandson extends Son {
    public Grandson(String name) {
        super(name);
    }

    @Override
    public String sonAnnotatedToBeOverrideWithoutReTag(String arg) {
        return super.sonAnnotatedToBeOverrideWithoutReTag(arg);
    }

    @Timer(name = "sonAnnotatedToBeOverrideAndReTag")
    @Override
    public String sonAnnotatedToBeOverrideAndReTag(String arg) {
        return super.sonAnnotatedToBeOverrideAndReTag(arg);
    }

    @Timer(name = "grandpaAnnotated")
    @Override
    public String grandpaAnnotated(String arg) {
        return super.grandpaAnnotated(arg);
    }
}
