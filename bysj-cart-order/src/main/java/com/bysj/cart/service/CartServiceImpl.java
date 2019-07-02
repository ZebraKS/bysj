package com.bysj.cart.service;

import java.util.Date;
import java.util.List;

import com.bysj.cart.mapper.CartMapper;
import com.bysj.common.po.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CartServiceImpl implements CartService {
	@Autowired
	private CartMapper cartMapper;
	private ObjectMapper objectMapper = new ObjectMapper();
	
	
	@Override
	public List<Cart> findCartById(Long userId) {
		// TODO Auto-generated method stub
		Cart cart = new Cart();
		cart.setUserId(userId);
		List<Cart> list = cartMapper.select(cart);
		return list;
	}


	@Override
	public void updateCartNum(Long userId, Long itemId, Integer num) {
		Cart cart = new Cart();	//封装用户数据
		cart.setUserId(userId);
		cart.setItemId(itemId);
		cart.setNum(num);
		cart.setUpdated(new Date());
		cartMapper.updateCartNum(cart);
	}


	@Override
	public void saveCart(Cart cart) {
		// TODO Auto-generated method stub
		Cart cartDB = cartMapper.findCartByUI(cart);
		if(cartDB == null) {
			cart.setCreated(new Date());
			cart.setUpdated(cart.getCreated());
			cartMapper.insert(cart);
		}else {
			int num = cartDB.getNum() + cart.getNum();
			System.out.println("num"+num);
			cartDB.setNum(num);
			cartDB.setCreated(new Date());
			cartDB.setUpdated(cart.getCreated());
			cartMapper.updateByPrimaryKeySelective(cartDB);
		}
	}


	@Override
	public void deleteCart(Long userId, Long itemId) {
		// TODO Auto-generated method stub
		Cart cart = new Cart();
		cart.setUserId(userId);
		cart.setItemId(itemId);
		cartMapper.delete(cart);
	}


}
