package com.github.lkq.timeron.hierarchy.lv2;

import com.github.lkq.timeron.annotation.Timer;
import com.github.lkq.timeron.hierarchy.lv1.Grandpa;

public interface Uncle extends Grandpa {

    @Timer(name = "grandpaAnnotatedUncleReTag")
    @Override
    String tagInGrandpa(String arg);

}
