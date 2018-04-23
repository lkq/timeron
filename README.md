# Timer On

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
    UserSerivce service = timer.intercept(UserSerivce.class)
    timer.measure(service.getUser(""));

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

