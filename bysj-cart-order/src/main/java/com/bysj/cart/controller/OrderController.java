package com.bysj.cart.controller;

import com.bysj.cart.service.OrderService;
import com.bysj.common.po.Order;
import com.bysj.common.po.OrderShipping;
import com.bysj.common.vo.SysResult;
import com.fasterxml.jackson.databind.DeserializationFeature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.druid.util.StringUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping("/order")
public class OrderController {

	@Autowired
	private OrderService orderService;
	
	private ObjectMapper objectMapper = new ObjectMapper();
	//订单提交
	@RequestMapping("/create")
	@ResponseBody
	public SysResult saveOrder(String orderJSON) {
		try {
			objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			Order order = objectMapper.readValue(orderJSON,Order.class);
			String orderId = orderService.saveOrder(order);
			if(!StringUtils.isEmpty(orderId)) {
				System.out.println(orderId);
				return SysResult.oK(orderId);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return SysResult.build(201, "保存订单失败");
	}
	
	//根据orderId查询订单信息
	@RequestMapping("/query/{orderId}")
	@ResponseBody
	public Order findOrderById(@PathVariable String orderId){

		return orderService.findOrderById(orderId);
	}

	//根据user_id查询 订单信息
	@RequestMapping("/querybyuserid/{userId}")
	@ResponseBody
	public SysResult findOrderByUserId(@PathVariable(value = "userId") Long userId){
		return SysResult.oK(orderService.findOrderByUserId(userId));
	}

	//根据orderId查询物流信息
	@RequestMapping("/findOrderShippingByOrderId/{orderId}")
	@ResponseBody
	public OrderShipping findOrderShippingByOrderId(@PathVariable String orderId){

		return orderService.findOrderShippingByOrderId(orderId);
	}

	//根据orderId查询订单商品
	@RequestMapping("/findOrderItemsByOrderId/{orderId}")
	@ResponseBody
	public SysResult findOrderItemsByOrderId(@PathVariable String orderId) {
		try {
			return SysResult.oK(orderService.findOrderItemsByOrderId(orderId));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201, "查询失败！");
	}

	/**
	 * 已支付的订单发货 ， 状态2到4
	 * @param orderId
	 * @return
	 */
	@RequestMapping("/receivingGoods")
	@ResponseBody
	public SysResult receivingGoods(String orderId) {
		try {
			orderService.receivingGoods(orderId);
			return SysResult.oK();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201,"收货失败！");
	}

	/**
	 *
	 * @param orderId
	 * @return
	 */
	@RequestMapping("/deleteOrder/{orderId}")
	@ResponseBody
	public SysResult deleteOrder(@PathVariable(value="orderId") String orderId) {
		try {
			orderService.deleteOrder(orderId);
			return SysResult.oK();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201,"删除失败！");
	}
}
