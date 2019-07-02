package com.bysj.web.controller;

import com.bysj.common.po.Item;
import com.bysj.web.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/search")
public class SearchController {

    @Autowired
    SearchService searchService;
    // q:查询的关键字
    @RequestMapping("/search")
    public String search(String q,Model model)
            throws Exception
    {
        //处理乱码
        byte[] data=q.getBytes("ISO-8859-1");
        String key=new String(data, "UTF-8");

        List<Item> itemList= null;
        try {
            itemList = searchService.findItemByKey(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        model.addAttribute("itemList", itemList);
        return "search";
    }

}
