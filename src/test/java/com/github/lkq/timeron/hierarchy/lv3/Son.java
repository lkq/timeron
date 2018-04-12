package com.github.lkq.timeron.hierarchy.lv3;


import com.github.lkq.timeron.hierarchy.lv2.Father;
import com.github.lkq.timeron.hierarchy.lv2.Mother;

import java.util.logging.Logger;

/**
 * tagInGrandpa - class -> interface -> interface(tag)
 * tagInFather - class -> interface(tag) -> interface
 * fromGrandpaTagInSon - class(tag) -> interface -> interface()
 * fromFatherTagInSon - class(tag) -> interface() -> interface
 *
 * implInMother - class -> class(tag) -> interface
 * tagInGrandma - class -> class -> interface(tag)
 * tagInGrandmaTagInSon - class(tag) -> class -> interface(tag)
 * tagInGrandmaTagInMother - class -> class(tag) -> interface(tag)
 * tagInMotherTagInSon - class(tag) -> class(tag) -> interface
 * fromGrandmaTagInSon - class(tag) -> class -> interface()
 * fromMotherImplInSon - class(tag) -> class() -> interface
 *
 * implInMother - class -> class -> class(tag)
 * tagInMotherTagInSon - class -> class(tag) -> class(tag)
 * fromMotherTagInGreatGrandson - class(tag) -> class -> class()
 */
public class Son extends Mother implements Father {
    private static Logger logger = Logger.getLogger(Son.class.getName());

    public Son(String name) {
        logger.info("creating son " + name);
    }

    private void delay(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ignored) {
        }
    }

    public String implInSon(String arg) {
        return "implInSon-" + arg;
    }

    public String fromSonTagInGrandson(String arg) {
        return "fromSonTagInGrandson-" + arg;
    }

    @Override
    public String tagInGrandma(String arg) {
        return "tagInGrandma-" + arg;
    }

    @Override
    public String tagInGrandmaTagInSon(String arg) {
        return "tagInGrandmaTagInSon-" + arg;
    }

    @Override
    public String tagInFather(String arg) {
        return "tagInFather-" + arg;
    }

    @Override
    public String fromFatherTagInSon(String arg) {
        return "tagInfromFatherTagInSonGrandma-" + arg;
    }

    @Override
    public String tagInGrandpa(String arg) {
        return "tagInGrandpa-" + arg;
    }

    @Override
    public String fromGrandpaTagInSon(String arg) {
        return "fromGrandpaTagInSon-" + arg;
    }

    @Override
    public String fromMotherImplInSon(String arg) {
        return "fromMotherImplInSon-" + arg;
    }

    @Override
    public String tagInMotherImplInSon(String arg) {
        return "tagInMotherImplInSon-" + arg;
    }
}
