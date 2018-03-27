package com.github.lkq.timeron.family;

import com.github.lkq.timeron.annotation.Timer;

public interface Barkable {
    @Timer(name = "wow")
    void wow();
}
