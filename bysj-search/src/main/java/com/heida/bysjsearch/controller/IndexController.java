package com.heida.bysjsearch.controller;

import javax.servlet.http.HttpServletRequest;

import com.heida.bysjsearch.pojo.Item;
import com.heida.bysjsearch.service.SearchService;
import com.heida.bysjsearch.vo.SysResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
public class IndexController {

	@Autowired
	SearchService searchService;

	@ResponseBody
	@RequestMapping(value = "/search")
	public SysResult findItemByKey(String key){
		try{
			List<Item> itemList = searchService.findItemByKey(key);
			return SysResult.oK(itemList);
		}catch (Exception e){
			e.printStackTrace();
		}
		return SysResult.build(201,"查询失败！");
	}

}
