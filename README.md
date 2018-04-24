# Timer On

[![Build Status](https://travis-ci.org/lkq/timeron.svg?branch=master)](https://travis-ci.org/lkq/timeron)

A simple framework for measuring performance of method call using AOP

## Usage

available in maven [central](https://repo1.maven.org/maven2/com/github/lkq/timeron/)

add dependency

    <dependency>
        <groupId>com.github.lkq</groupId>
        <artifactId>timeron</artifactId>
        <version>0.3.1</version>
    </dependency>

setup

    public interface UserService {
        User getUser(String name);
    }
    public class UserService {
        public User getUser(String name) {
         //...
        }
    }

    // setup
    Timer timer = new Timer();
    UserSerivce interceptor = timer.interceptor(UserSerivce.class)
    timer.measure(() -> interceptor.getUser(""));

    UserService userService = timer.on(new UserServiceImpl())

    // business logic
    userService.getUser("kingson");

results

    String result = timer.getStats()

    // value of "result"
    [{"com.github.lkq.timeron.UserServiceImpl.getUser(String)": {
        "total": 947396,
        "count": 100,
        "avg": 9473
    }}]

more sample usage could be found in [here](src/test/java/com/github/lkq/timeron/samples/)


## TODO List
- ~~support void methods~~
- add benchmark tests
- detach the measurement handling logic from the calling thread
- redesign result formatting
- support generic method
- allow adding callback to listen on measurement event
