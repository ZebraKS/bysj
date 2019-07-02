package com.bysj.cart.mapper;

import com.bysj.common.mapper.SysMapper;
import com.bysj.common.po.Order;

import java.util.Date;


public interface OrderMapper extends SysMapper<Order> {

	void updateStatus(Date agoDate);

}