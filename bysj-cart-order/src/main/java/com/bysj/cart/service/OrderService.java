package com.bysj.cart.service;


import com.bysj.common.po.Order;
import com.bysj.common.po.OrderItem;
import com.bysj.common.po.OrderShipping;

import java.util.List;

public interface OrderService {

	String saveOrder(Order order);

	Order findOrderById(String orderId);

	List<Order> findOrderByUserId(Long userId);

    OrderShipping findOrderShippingByOrderId(String orderId);

    List<OrderItem> findOrderItemsByOrderId(String orderId);

    void receivingGoods(String orderId);

    void deleteOrder(String orderId);
}
