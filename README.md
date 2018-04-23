# Timer On

[![Build Status](https://travis-ci.org/lkq/timeron.svg?branch=master)](https://travis-ci.org/lkq/timeron)

A simple framework for measuring performance in method call level using AOP

## Usage

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
    timer.measure(interceptor.getUser(""));

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
- support void methods
- add benchmark tests
- detach the measurement handling logic from the calling thread
- redesign result formatting
- support generic method
- allow adding callback to listen on measurement event
