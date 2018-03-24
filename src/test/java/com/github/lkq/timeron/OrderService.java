package com.github.lkq.timeron;

import com.github.lkq.timeron.annotation.Timer;

public interface OrderService {

    String createOrder();

    @Timer(name = "Save Order")
    boolean saveOrder(String userName, String item, int quantity);
}
