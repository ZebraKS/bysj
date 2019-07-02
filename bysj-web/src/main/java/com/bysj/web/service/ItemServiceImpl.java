package com.bysj.web.service;

import java.io.IOException;

import com.bysj.common.po.Item;
import com.bysj.common.po.ItemDesc;
import com.bysj.common.service.HttpClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ItemServiceImpl implements ItemService{

	@Autowired
	private HttpClientService httpClient;
	
	//从后台服务器要数据 用http
	@Override
	public Item findItemById(Long itemId) {
		// restFul 风格发送url
		String url = "http://www.bsmanage.com/web/item/findItemById/"+itemId;
		String result = httpClient.doGet(url);
		ObjectMapper mapper = new ObjectMapper();
		Item item = null;
		try {
			item = mapper.readValue(result, Item.class);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return item;
	}

	@Override
	public ItemDesc findItemDescById(Long itemId) {
		// TODO Auto-generated method stub
		// restFul 风格发送url
		String url = "http://www.bsmanage.com/web/item/fingItemDescById/"+itemId;
		String result = httpClient.doGet(url);
		ObjectMapper mapper = new ObjectMapper();
		ItemDesc itemDesc = null;
		try {
			itemDesc = mapper.readValue(result, ItemDesc.class);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return itemDesc;
	}

}
