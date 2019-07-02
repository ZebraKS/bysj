package com.bysj.web.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bysj.common.po.Order;
import com.bysj.common.po.OrderItem;
import com.bysj.common.po.OrderShipping;
import com.bysj.common.service.HttpClientService;
import com.bysj.common.vo.SysResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	HttpClientService httpClient;
	ObjectMapper objectMapper = new ObjectMapper();

	@Autowired
	CartService cartService;
	@Override
	public String saveOrder(Order order) {
		String url = "http://www.bscart.com/order/create";
		Map<String, String> params = new HashMap<>();
		String resultOrder = null;
		try {
			String orderJSON = objectMapper.writeValueAsString(order);
			params.put("orderJSON", orderJSON);
			String resultJSON = httpClient.doPost(url, params);
			SysResult sysResult = objectMapper.readValue(resultJSON, SysResult.class);
			if (sysResult.getStatus() == 200) {
				resultOrder = (String) sysResult.getData();
				//订单提交成功删除购物车
				List<OrderItem> orderItems = order.getOrderItems();
				for (OrderItem orderItem : orderItems) {
					cartService.deleteCart(order.getUserId(), Long.valueOf(orderItem.getItemId()));
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("回传或发送失败");
			throw new RuntimeException();
		}
		return resultOrder;
	}

	@Override
	public Order findOrderById(String id) {
		String url = "http://www.bscart.com/order/query/"+id;
		String orderJSON = httpClient.doGet(url);
		Order order = null;
		try {
			order = objectMapper.readValue(orderJSON, Order.class);
		} catch (Exception e) {
			System.out.println("获取order对象异常"+e.getMessage());
		}
		return order;
	}

	@Override
	public List<Order> findOrderByUserId(Long userId) {
		String url = "http://www.bscart.com/order/querybyuserid/"+userId;
		Map<String, String> params = new HashMap<>();
		List<Order> orderList = null;
		try {
			params.put("userId", userId.toString());
			String resultJSON = httpClient.doPost(url, params);
			SysResult sysResult = objectMapper.readValue(resultJSON, SysResult.class);
			orderList = (List<Order>) sysResult.getData();

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("根据userid查询订单信息失败！");
		}

		return orderList;
	}

	@Override
	public OrderShipping findOrderShippingByOrderId(String orderId) {
		String url = "http://www.bscart.com/order/findOrderShippingByOrderId/"+orderId;
		String orderShippingJSON = httpClient.doGet(url);
		OrderShipping orderShipping = null;
		try {
			orderShipping = objectMapper.readValue(orderShippingJSON, OrderShipping.class);
		} catch (Exception e) {
			System.out.println("获取order对象异常"+e.getMessage());
		}
		return orderShipping;
	}

	@Override
	public List<OrderItem> findOrderItemsByOrderId(String orderId) {
		String url = "http://www.bscart.com/order/findOrderItemsByOrderId/"+orderId;
		String orderItemListJSON = httpClient.doGet(url);
		List<OrderItem> orderItemList = null;
		SysResult sysResult = null;
		try {
			sysResult = objectMapper.readValue(orderItemListJSON, SysResult.class);
			if(sysResult.getStatus() == 200) {
				orderItemList = (List<OrderItem>) sysResult.getData();
				return orderItemList;
			}
		} catch (Exception e) {
			System.out.println("获取order对象异常"+e.getMessage());
		}
		return orderItemList;
	}

	@Override
	public boolean receivingGoods(String orderId) {
		String url = "http://www.bscart.com/order/receivingGoods/"+orderId;
		String orderShippingJSON = httpClient.doGet(url);
		SysResult sysResult = null;
		try {
			sysResult = objectMapper.readValue(orderShippingJSON, SysResult.class);
			if(sysResult.getStatus() == 200) {
				return true;
			}
		} catch (Exception e) {
			System.out.println("获取对象异常"+e.getMessage());
		}
		return false;
	}

	@Override
	public boolean deleteOrder(String orderId) {
		String url = "http://www.bscart.com/order/deleteOrder/"+orderId;
		String orderShippingJSON = httpClient.doGet(url);
		SysResult sysResult = null;
		try {
			sysResult = objectMapper.readValue(orderShippingJSON, SysResult.class);
			if(sysResult.getStatus() == 200) {
				return true;
			}
		} catch (Exception e) {
			System.out.println("获取对象异常"+e.getMessage());
		}
		return false;
	}

}
