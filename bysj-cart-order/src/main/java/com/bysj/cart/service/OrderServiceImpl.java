package com.bysj.cart.service;

import java.util.Date;
import java.util.List;

import com.bysj.cart.mapper.OrderItemMapper;
import com.bysj.cart.mapper.OrderMapper;
import com.bysj.cart.mapper.OrderShippingMapper;
import com.bysj.common.po.Order;
import com.bysj.common.po.OrderItem;
import com.bysj.common.po.OrderShipping;
import com.bysj.common.util.Constants;
import com.bysj.common.util.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.JedisCluster;


@Service
@Transactional
public class OrderServiceImpl implements OrderService{

	@Autowired
	OrderItemMapper orderItemMapper;
	@Autowired
	OrderMapper orderMapper;
	@Autowired
	OrderShippingMapper orderShippingMapper;
	@Autowired
	private JedisCluster jedisCluster;
	@Override
	public String saveOrder(Order order) {
		// TODO Auto-generated method stub
		Date date = new Date();
		
		String orderId = "" + order.getUserId() + System.currentTimeMillis();
		order.setOrderId(orderId);
		order.setStatus(1);
		order.setCreated(date);
		order.setUpdated(date);
		orderMapper.insert(order);
		System.out.println("orderMapper save success");
		
		OrderShipping orderShipping = order.getOrderShipping();
		orderShipping.setOrderId(orderId);
		orderShipping.setCreated(date);
		orderShipping.setUpdated(date);
		orderShippingMapper.insert(orderShipping);
		
		List<OrderItem> orderItems = order.getOrderItems();
		System.out.println("orderShippingMapper save success");
		
		for (OrderItem orderItem : orderItems) {
			orderItem.setOrderId(orderId);
			orderItem.setCreated(date);
			orderItem.setUpdated(date);
			orderItemMapper.insert(orderItem);
			System.out.println("orderItemMapper save success");
		}

		//生成支付token
		String token = TokenUtils.getPayToken();
		jedisCluster.set(orderId,token,"NX","EX", Constants.PAY_TOKEN_MEMBER_TIME);
		return orderId;
	}
	
	@Override
	public Order findOrderById(String orderId) {
		Order order = orderMapper.selectByPrimaryKey(orderId);
		OrderShipping orderShipping = 
				orderShippingMapper.selectByPrimaryKey(orderId);
		OrderItem orderItem = new OrderItem();
		orderItem.setOrderId(orderId);
		List<OrderItem> orderItems = 
				orderItemMapper.select(orderItem);
		order.setOrderShipping(orderShipping);
		order.setOrderItems(orderItems);
		return order;
	}

	@Override
	public List<Order> findOrderByUserId(Long userId) {
		Order order = new Order();
		order.setUserId(userId);
		return orderMapper.select(order);
	}

	@Override
	public OrderShipping findOrderShippingByOrderId(String orderId) {
		return orderShippingMapper.selectByPrimaryKey(orderId);
	}

	@Override
	public List<OrderItem> findOrderItemsByOrderId(String orderId) {
		OrderItem orderItem = new OrderItem();
		List<OrderItem> orderItems = null;
		try {
			orderItem.setOrderId(orderId);
			orderItems = orderItemMapper.select(orderItem);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return orderItems;
	}

	@Override
	public void receivingGoods(String orderId) {
		Order order = new Order();
		order.setOrderId(orderId);
		order.setStatus(5);
		order.setUpdated(new Date());
		orderMapper.updateByPrimaryKeySelective(order);
	}

	@Override
	public void deleteOrder(String orderId) {
		orderMapper.deleteByPrimaryKey(orderId);

		OrderItem orderItem = new OrderItem();
		orderItem.setOrderId(orderId);

		orderItemMapper.delete(orderItem);

		orderShippingMapper.deleteByPrimaryKey(orderId);
	}

}
