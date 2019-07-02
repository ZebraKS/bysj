package com.bysj.cart.controller;

import java.util.List;

import com.bysj.cart.service.CartService;
import com.bysj.common.po.Cart;
import com.bysj.common.vo.SysResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping("/cart")
public class CartController {

	@Autowired
	CartService cartService;

	private ObjectMapper objectMapper = new ObjectMapper();

	@RequestMapping("/query/{userId}")
	@ResponseBody
	public SysResult show(@PathVariable Long userId) {
		try {
			List<Cart> list = cartService.findCartById(userId);
			return SysResult.oK(list);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return SysResult.build(201, "查询cartById失败");
	}

	@RequestMapping("/update/num/{userId}/{itemId}/{num}")
	@ResponseBody
	public SysResult updateCartNum(@PathVariable Long userId, @PathVariable Long itemId, @PathVariable Integer num) {
		try {
			cartService.updateCartNum(userId, itemId, num);
			return SysResult.oK();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201, "商品数量修改失败");
	}

	// 购物车新增
	@RequestMapping("/save")
	@ResponseBody
	public SysResult saveCart(String cartJSON) {
		try {
			Cart cart = objectMapper.readValue(cartJSON, Cart.class);
			cartService.saveCart(cart);
			return SysResult.oK();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return SysResult.build(201, "新增购物车失败");
	}

	// 购物车删除
	@RequestMapping("/delete/{userId}/{itemId}")
	@ResponseBody
	public SysResult deleteCart(@PathVariable Long userId, @PathVariable Long itemId) {
		try {
			System.out.println("aaa");
			cartService.deleteCart(userId, itemId);
			return SysResult.oK();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return SysResult.build(201, "删除购物车失败");
	}
}
