package com.bysj.web.service;

import com.bysj.common.po.Item;
import com.bysj.common.po.ItemDesc;
import org.springframework.stereotype.Service;



public interface ItemService {

	public Item findItemById(Long itemId);

	public ItemDesc findItemDescById(Long itemId);
	
}
