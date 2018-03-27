# Timer On

Going to build a simple framework for measuring method call performance using AOP


### Designed Features


#### 1. with annotation

    public interface UserService {
        @Timer(name = "getUser")
        String getUser(String name);
    }

    public class UserServiceImpl {
        public String getUser(String name) {
            return "user:" + name;
        }
    }

    Timer timer = new Timer();

    UserService timedService = timer.on(new UserServiceImpl())

    // method execution time will be measured
    timedService.getUser("kingson");

#### 2. annotation free

    Timer timer = new Timer();
    UserSerivce service = timer.stub(UserSerivceImpl.class)
    timer.intercept(service.getUser(any()), "getUser");

    UserService timedService = timer.on(new UserServiceImpl())

    // method execution time will be measured
    timedService.getUser("kingson");


#### 3. stats

send stats to persistent storage when the timer name match with "pattern", e.g influx DB

    timer.addCallback("<pattern>", new StatsDSender());

expose stats with api

    // get method execution time stats in csv format
    String csvStats = timer.get("csv");
    // get method execution time stats in csv format
    String jsonStats = timer.get("json");