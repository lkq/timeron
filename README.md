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

    Timer timer = new Timer();
    UserSerivce serviceInterceptor = timer.interceptor(UserSerivce.class)
    timer.measure(serviceInterceptor.getUser(""));

    UserService timedService = timer.on(new UserServiceImpl())

    // method execution time will be measured
    timedService.getUser("kingson");

results

    String result = timer.getStats()

    // value of "result"
    [{"com.github.lkq.timeron.UserServiceImpl.getUser(String)": {
        "total": 947396,
        "count": 100,
        "avg": 9473
    }}]

more sample usage could be found in [here](src/test/java/com/github/lkq/timeron/samples/)