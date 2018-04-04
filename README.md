# Timer On

Going to build a simple framework for measuring method call performance


### Designed Features

#### Intercept concrete class method

intercepted concrete method will be measured performance with

    Timer timer = new Timer();
    UserSerivceImpl service = timer.intercept(UserSerivceImpl.class)
    timer.measure(service.getUser(any()));

    UserService timedService = timer.on(new UserServiceImpl())

    // method execution time will be measured
    timedService.getUser("kingson");

sample result

    [{"com.github.lkq.timeron.UserServiceImpl.getUser(String)": {
        "total": 947396,
        "count": 100,
        "avg": 9473
    }}]

#### Intercept interface method

all classes implements the intercepted interface method will inherit the measurement under its own name

    Timer timer = new Timer();
    UserSerivce service = timer.intercept(UserSerivce.class)
    timer.measure(service.getUser(any()));

    UserService timedService = timer.on(new UserServiceImpl())

    // method execution time will be measured
    timedService.getUser("kingson");

sample result

    [{"com.github.lkq.timeron.UserServiceImpl.getUser(String)": {
        "total": 947396,
        "count": 100,
        "avg": 9473
    }}]

#### Intercept abstract method

all classes implements the intercepted abstract method will inherit the measurement under its own name

    Timer timer = new Timer();
    AbstractUserSerivce service = timer.intercept(AbstractUserSerivce.class)
    timer.measure(service.getUser(any()));

    UserService timedService = timer.on(new UserServiceImpl())

    // method execution time will be measured
    timedService.getUser("kingson");

sample result

    [{"com.github.lkq.timeron.UserServiceImpl.getUser(String)": {
        "total": 947396,
        "count": 100,
        "avg": 9473
    }}]

#### Intercept super class method

all classes extended the intercepted super class concrete method without override will be measured under the super class's name

    Timer timer = new Timer();
    UserSerivceImpl service = timer.intercept(UserSerivceImpl.class)
    timer.measure(service.getUser(any()));

    UserService timedService = timer.on(new VIPUserServiceImpl())

    // method execution time will be measured
    timedService.getUser("kingson");

sample result

    [{"com.github.lkq.timeron.UserServiceImpl.getUser(String)": {
        "total": 947396,
        "count": 100,
        "avg": 9473
    }}]

#### Intercept super class method being override

all classes extended the intercepted super class concrete method and overriding it will be measured under its own name

    Timer timer = new Timer();
    UserSerivceImpl service = timer.intercept(UserSerivceImpl.class)
    timer.measure(service.getUser(any()));

    UserService timedService = timer.on(new VIPUserServiceImpl())

    // method execution time will be measured
    timedService.getUser("kingson");

sample result

    [{"com.github.lkq.timeron.VIPUserServiceImpl.getUser(String)": {
        "total": 947396,
        "count": 100,
        "avg": 9473
    }}]

#### 3. stats

send stats to persistent storage when the timer name match with "pattern", e.g influx DB

    timer.addCallback("<pattern>", new StatsDSender());

expose stats with api

    // get method execution time stats in csv format
    String csvStats = timer.getCsv("timerName");
    // get method execution time stats in json format
    String jsonStats = timer.getJson("timerName");