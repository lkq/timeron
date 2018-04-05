package com.github.lkq.timeron.hierarchy.lv2;

import com.github.lkq.timeron.hierarchy.lv1.Grandpa;

public interface Uncle extends Grandpa {

    @Override
    String tagInGrandpa(String arg);

}
