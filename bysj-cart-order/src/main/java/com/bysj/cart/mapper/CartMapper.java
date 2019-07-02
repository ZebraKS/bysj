package com.bysj.cart.mapper;


import com.bysj.common.mapper.SysMapper;
import com.bysj.common.po.Cart;

public interface CartMapper extends SysMapper<Cart> {

	void updateCartNum(Cart cart);

	
	Cart findCartByUI(Cart cart);

}
