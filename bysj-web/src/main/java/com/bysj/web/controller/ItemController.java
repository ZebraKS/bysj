package com.bysj.web.controller;

import com.bysj.common.po.Item;
import com.bysj.common.po.ItemDesc;
import com.bysj.web.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/items")
public class ItemController {

	@Autowired
	private ItemService itemServie;
	@RequestMapping(value="/{itemId}", produces = "text/html;charset=utf-8", method = {RequestMethod.GET, RequestMethod.POST})
	public String findItemById(@PathVariable Long itemId , Model model) {
		Item item = itemServie.findItemById(itemId);
		model.addAttribute("item",item);
		
		//商品详情展示	itemDesc.itemDesc
		ItemDesc itemDesc = itemServie.findItemDescById(itemId);
		model.addAttribute("itemDesc",itemDesc);
		return "item";
	}

	@RequestMapping(value="/kkk/{itemId}")
	public String kkk(@PathVariable Long itemId , Model model) {
		System.out.println(itemId);
		Item item = itemServie.findItemById(itemId);
		model.addAttribute("item",item);

		//商品详情展示	itemDesc.itemDesc
		ItemDesc itemDesc = itemServie.findItemDescById(itemId);
		model.addAttribute("itemDesc",itemDesc);
		return "item";
	}

}
