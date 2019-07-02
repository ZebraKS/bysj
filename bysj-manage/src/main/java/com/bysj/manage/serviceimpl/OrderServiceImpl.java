package com.bysj.manage.serviceimpl;

import com.alibaba.druid.util.StringUtils;
import com.bysj.common.po.Order;
import com.bysj.common.po.OrderItem;
import com.bysj.common.po.OrderShipping;
import com.bysj.manage.mapper.OrderItemMapper;
import com.bysj.manage.mapper.OrderMapper;
import com.bysj.manage.mapper.OrderShippingMapper;
import com.bysj.manage.service.OrderService;
import com.bysj.manage.vo.ObjectUtils;
import com.bysj.manage.vo.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.JedisCluster;

import java.util.Date;
import java.util.List;

import static com.bysj.manage.vo.ObjectUtils.fieldNullToNull;


@Service
@Transactional
public class OrderServiceImpl implements OrderService {

	@Autowired
	OrderItemMapper orderItemMapper;
	@Autowired
	OrderMapper orderMapper;
	@Autowired
	OrderShippingMapper orderShippingMapper;
	@Autowired
	private JedisCluster jedisCluster;

	@Override
	public PageBean orderList(Integer currentPage, Integer row, Order order) throws Exception {

		if(ObjectUtils.isAllFieldNull(order)){
			if(currentPage <= 0){
				currentPage =1;
			}
			PageBean pb = new PageBean();
			int total = orderMapper.selectCount(null);
			int start = (currentPage-1)*row;
			int totalPage = total%row==0?total/row:total/row+1;
			if(currentPage > totalPage){
				currentPage = totalPage;
			}
			pb.setList(orderMapper.orderList((currentPage-1)*row,row));
			pb.setCurrentPage(currentPage);
			pb.setRows(row);
			pb.setTotalCount(total);
			pb.setTotalPage(totalPage);
			return pb;
		}else {
			if(currentPage <= 0){
				currentPage =1;
			}
			PageBean pb = new PageBean();
            /*if(StringUtils.isEmpty(order.getOrderId())){
                order.setOrderId(null);
            }*/
			fieldNullToNull(order);
			int total = orderMapper.selectCount(order);
			int totalPage = total%row==0?total/row:total/row+1;
			if(currentPage > totalPage){
				currentPage = totalPage;
			}
			pb.setList(orderMapper.orderList1((currentPage-1)*row,row,order));
			pb.setCurrentPage(currentPage);
			pb.setRows(row);
			pb.setTotalCount(total);
			pb.setTotalPage(totalPage);
			return pb;
		}

	}
	@Override
	public void deleteOrder(String orderId) {
		orderMapper.deleteByPrimaryKey(orderId);

		OrderItem orderItem = new OrderItem();
		orderItem.setOrderId(orderId);

		orderItemMapper.delete(orderItem);

		orderShippingMapper.deleteByPrimaryKey(orderId);
	}

	@Override
	public void deleteOrders(String[] orderIds) {
		for (String s:orderIds) {
			deleteOrder(s);
		}
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
	public void sendItem(String orderId) {
		Order order = new Order();
		order.setOrderId(orderId);
		order.setStatus(4);
		order.setUpdated(new Date());
		orderMapper.updateByPrimaryKeySelective(order);
	}

}
