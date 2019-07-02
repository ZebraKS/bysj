package com.bysj.manage.mapper;

import com.bysj.common.mapper.SysMapper;
import com.bysj.common.po.Order;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;


public interface OrderMapper extends SysMapper<Order> {

	List<Order> orderList(@Param("start") Integer start, @Param("rows") Integer rows);

	List<Order> orderList1(@Param("start") Integer start, @Param("rows") Integer rows,@Param("order") Order order);
}