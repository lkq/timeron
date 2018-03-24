package com.github.lkq.timeron;

import com.github.lkq.timeron.annotation.Timer;

public interface UserService {

    @Timer(name = "Get User")
    String getUser(String userName);
}
