package com.bysj.manage.controller;

import com.bysj.manage.service.ItemCatService;
import com.bysj.manage.vo.EasyUITree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/item")
public class ItemCatController {

    @Autowired
    private ItemCatService itemCatService;

    /**
     * 商品分类标题查询
     * @param parentId
     * @return
     */
    @RequestMapping("/cat/list")
    @ResponseBody
    public List<EasyUITree> findCacheItemCatById(@RequestParam(value="id",defaultValue="0")Long parentId){
        List<EasyUITree> findCacheItemCatById = itemCatService.findCacheItemCatById(parentId);
        return findCacheItemCatById;
    }
}
