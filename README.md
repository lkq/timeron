# Timer On

[![Build Status](https://travis-ci.org/lkq/timeron.svg?branch=master)](https://travis-ci.org/lkq/timeron)

A simple framework for measuring method call performance

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
1. add benchmark tests
2. detach the measurement handling logic from the calling thread
3. redesign result formatting
4. support generic method
5. allow adding callback to listen on measurement event
