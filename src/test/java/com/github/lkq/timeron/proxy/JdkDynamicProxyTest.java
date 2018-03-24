package com.github.lkq.timeron.proxy;

import com.github.lkq.timeron.OrderService;
import com.github.lkq.timeron.OrderServiceImpl;
import com.github.lkq.timeron.Timer;
import com.github.lkq.timeron.UserService;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class JdkDynamicProxyTest {

    @Test
    void canProxyMethodCalls() {
        UserService proxy = new Timer().on(new OrderServiceImpl("real service"));
        String user = proxy.getUser("user");
        assertThat(user, is("user"));

        OrderService orderService = (OrderService) proxy;
        String order = orderService.createOrder();
        assertThat(order, is("order"));
    }
}