package com.bysj.web.controller;

import java.util.List;

import com.bysj.common.po.Cart;
import com.bysj.common.vo.SysResult;
import com.bysj.web.service.CartService;
import com.bysj.web.thread.UserThreadLocal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/cart")
public class CartController {

	@Autowired
	CartService cartService;

	@RequestMapping("/show")
	public String show(Model model) {
		Long userId = UserThreadLocal.getUser().getId();
		List<Cart> cartList = cartService.findCartById(userId);
		model.addAttribute("cartList", cartList);
		return "cart";
	}

	// /service/cart/update/num/itemId/num"
	@RequestMapping("/update/num/{itemId}/{num}")
	@ResponseBody
	public SysResult updateCartNum(@PathVariable Long itemId, @PathVariable Integer num) {
		try {
			Long userId = UserThreadLocal.getUser().getId();
			cartService.updateCartNum(userId, itemId, num);
			return SysResult.oK();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201, "购物车商品修改失败");
	}

	@RequestMapping("/add/{itemId}")
	public String saveCart(@PathVariable Long itemId,Cart cart) {
		Long userId = UserThreadLocal.getUser().getId();
		cart.setUserId(userId);
		cart.setItemId(itemId);
		cartService.saveCart(cart);
		return "redirect:/cart/show.html";
	}
	
	//删除购物车
	@RequestMapping("/delete/{itemId}")
	public String deleteCart(@PathVariable Long itemId) {
		Long userId = UserThreadLocal.getUser().getId();
		cartService.deleteCart(userId,itemId);
		return "redirect:/cart/show.html";
	}
}
