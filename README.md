# Timer On
Going to build a simple AOP framework for measuring method call performance


## Usage

    public interface UserService {
        @Timer(name = "getUser")
        String getUser(String name);
    }

    public class UserServiceImpl {
        public String getUser(String name) {
            return "user:" + name;
        }
    }

    UserService timedService = new Timer().on(new UserServiceImpl())
    timedService.getUser("kingson");