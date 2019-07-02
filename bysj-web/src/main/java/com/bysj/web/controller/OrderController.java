package com.bysj.web.controller;

import java.util.List;

import com.bysj.common.po.Cart;
import com.bysj.common.po.Order;
import com.bysj.common.po.OrderItem;
import com.bysj.common.po.OrderShipping;
import com.bysj.common.vo.SysResult;
import com.bysj.web.service.CartService;
import com.bysj.web.service.OrderService;
import com.bysj.web.thread.UserThreadLocal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.druid.util.StringUtils;

@Controller
@RequestMapping("/order")
public class OrderController {

	@Autowired
	private CartService cartService;
	@Autowired
	private OrderService orderService;
	
	@RequestMapping("/create")
	public String orderIndex(Model model) {
		Long userId = UserThreadLocal.getUser().getId();
		List<Cart> list = cartService.findCartById(userId);
		model.addAttribute("carts", list);
		return "order-cart";
	}
	
	//订单提交
	@RequestMapping("/submit")
	@ResponseBody
	public SysResult submit(Order order) {
		try {
			Long userId = UserThreadLocal.getUser().getId();
			order.setUserId(userId);
			String orderId = orderService.saveOrder(order);
			if(!StringUtils.isEmpty(orderId)) {
				return SysResult.oK(orderId);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return SysResult.build(201, "提交订单失败");
	}
	
	//提交后页面
	@RequestMapping("/success")
	public String findOrderById(String id,Model model){
		//根据id查询订单数据
		Order order = orderService.findOrderById(id);
		model.addAttribute("order", order);
		return "success";
	}

	//跳转我的订单
	@RequestMapping("/myOrder")
	public String myOrder(Model model){
		List<Order> orderList = orderService.findOrderByUserId(UserThreadLocal.getUser().getId());
		model.addAttribute("orderList",orderList);
		return "myorder";
	}


	/**
	 * 根据orderId查询 物流信息，商品信息
	 * @param orderId
	 * @param model
	 * @return
	 */
	@RequestMapping("/findOrderShippingByOrderId/{orderId}")
	public String findOrderShippingByOrderId(@PathVariable(value = "orderId") String orderId , Model model){
		try {
			OrderShipping orderShipping = orderService.findOrderShippingByOrderId(orderId);
			List<OrderItem> orderItems = orderService.findOrderItemsByOrderId(orderId);
			model.addAttribute("orderShipping",orderShipping);
			model.addAttribute("orderItems",orderItems);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "my-order-shipping";
	}

	/**
	 * 已支付的订单发货 ， 状态2到4
	 * @param orderId
	 * @return
	 */
	@RequestMapping(value = "/receivingGoods/{orderId}")
	@ResponseBody
	public SysResult receivingGoods(@PathVariable("orderId") String orderId) {
		try {
			if(orderService.receivingGoods(orderId)){
				return SysResult.oK();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201,"收货失败！");
	}

	/**
	 * 根据id删除订单
	 * @param orderId
	 * @return
	 */
	@RequestMapping(value = "/deleteOrder/{orderId}")
	@ResponseBody
	public SysResult deleteOrder(@PathVariable("orderId") String orderId) {
		try {
			if(orderService.deleteOrder(orderId)){
				return SysResult.oK();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201,"删除失败！");
	}
}
