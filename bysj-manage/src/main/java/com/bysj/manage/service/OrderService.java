package com.bysj.manage.service;


import com.bysj.common.po.Order;
import com.bysj.common.po.OrderItem;
import com.bysj.common.po.OrderShipping;
import com.bysj.manage.vo.PageBean;

import java.util.List;

public interface OrderService {

    PageBean orderList(Integer currentPage, Integer row, Order order) throws Exception;

    void deleteOrder(String orderId);

    void deleteOrders(String[] orderIds);

    OrderShipping findOrderShippingByOrderId(String orderId);

    List<OrderItem> findOrderItemsByOrderId(String orderId);

    void sendItem(String orderId);
}
