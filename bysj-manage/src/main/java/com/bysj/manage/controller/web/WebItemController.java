package com.bysj.manage.controller.web;

import com.bysj.common.po.Item;
import com.bysj.common.po.ItemDesc;
import com.bysj.manage.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/web/item")
public class WebItemController {

	@Autowired
	ItemService itemSerice;
	//"http://manage.jt.com/web/item/findItemById/"+itemId;
	@RequestMapping("/findItemById/{itemId}")
	@ResponseBody
	public Item findItemById(@PathVariable Long itemId) {
		Item item = itemSerice.findItemById(itemId);
		return item;
	}
	
	@RequestMapping("/fingItemDescById/{itemId}")
	@ResponseBody
	public ItemDesc fingItemDescById(@PathVariable Long itemId) {
		return itemSerice.findItemDescById(itemId);
	}
}
