package com.bysj.web.service;


import com.bysj.common.po.Order;
import com.bysj.common.po.OrderItem;
import com.bysj.common.po.OrderShipping;

import java.util.List;

public interface OrderService {

	String saveOrder(Order order);

	Order findOrderById(String id);

	List<Order> findOrderByUserId(Long userId);

    OrderShipping findOrderShippingByOrderId(String orderId);

    List<OrderItem> findOrderItemsByOrderId(String orderId);

    boolean receivingGoods(String orderId);

    boolean deleteOrder(String orderId);
}
