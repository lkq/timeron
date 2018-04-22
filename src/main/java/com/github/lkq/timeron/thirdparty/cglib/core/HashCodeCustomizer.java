package com.github.lkq.timeron.thirdparty.cglib.core;

import com.github.lkq.timeron.thirdparty.asm.Type;

public interface HashCodeCustomizer extends KeyFactoryCustomizer {
    /**
     * Customizes calculation of hashcode
     * @param e code emitter
     * @param type parameter type
     */
    boolean customize(CodeEmitter e, Type type);
}
