package com.bysj.web.service;

import com.bysj.common.po.Cart;

import java.util.List;


public interface CartService {

	List<Cart> findCartById(Long userId);

	void updateCartNum(Long userId, Long itemId, Integer num);

	void saveCart(Cart cart);

	void deleteCart(Long userId, Long itemId);

}
