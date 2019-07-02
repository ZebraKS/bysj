package com.bysj.web.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.bysj.common.po.Cart;
import com.bysj.common.service.HttpClientService;
import com.bysj.common.vo.SysResult;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class CartServiceImpl implements CartService {
	@Autowired
	private HttpClientService httpClient;
	private ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public List<Cart> findCartById(Long userId) {
		// TODO Auto-generated method stub
		String url = "http://www.bscart.com/cart/query/" + userId;
		String resultJSON = httpClient.doGet(url);
		List<Cart> list = new ArrayList<>();
		try {
			SysResult sysResult = objectMapper.readValue(resultJSON, SysResult.class);
			if (sysResult.getStatus() == 200) {
				list = (List<Cart>) sysResult.getData();
			} else {
				System.out.println("后台查询数据失败");
				throw new RuntimeException();
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("后台回传数据，购物车系统解析错误");
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public void updateCartNum(Long userId, Long itemId, Integer num) {
		String url = "http://www.bscart.com/cart/update/num/" + userId + "/" + itemId + "/" + num;
		httpClient.doGet(url);
	}

	@Override
	public void saveCart(Cart cart) {
		// TODO Auto-generated method stub
		String url = "http://www.bscart.com/cart/save";
		// 为了简化参数传递将cart转化成cartJSON
		String cartJSON = null;
		try {
			cartJSON = objectMapper.writeValueAsString(cart);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			System.out.println("cart转化成JSON异常");
			e.printStackTrace();
		}
		Map<String, String> params = new HashedMap();
		params.put("cartJSON", cartJSON);
		try {
			httpClient.doPost(url, params);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("doPost异常");
			e.printStackTrace();
		}
	}

	@Override
	public void deleteCart(Long userId, Long itemId) {
		// TODO Auto-generated method stub
		String url = "http://www.bscart.com/cart/delete/" + userId + "/" + itemId;
		try {
			httpClient.doGet(url);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
