package com.github.lkq.timeron.hierarchy;

import com.github.lkq.timeron.annotation.Timer;

public interface Barkable {
    @Timer(name = "wow")
    void wow();
}
