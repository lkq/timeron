package com.github.lkq.timeron;


import java.util.logging.Logger;

public class OrderServiceImpl implements OrderService, UserService {
    private static Logger logger = Logger.getLogger(OrderServiceImpl.class.getName());

    public OrderServiceImpl(String serviceName) {
        logger.info("creating " + serviceName);
    }

    public String getUser(String userName) {
        delay(1);
        return userName;
    }

    public String createOrder() {
        delay(3);
        return "order";
    }

    public boolean saveOrder(String userName, String item, int quantity) {
        delay(2);
        return true;
    }

    private void delay(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ignored) {
        }
    }
}
