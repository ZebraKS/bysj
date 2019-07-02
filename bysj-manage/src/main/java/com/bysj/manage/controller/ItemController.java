package com.bysj.manage.controller;

import com.bysj.common.po.Item;
import com.bysj.common.po.ItemDesc;
import com.bysj.common.vo.EasyUIResult;
import com.bysj.common.vo.SysResult;
import com.bysj.manage.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

@Controller
@RequestMapping("/item")
public class ItemController {

    @Autowired
    private ItemService itemService;

    /**
     * 查询商品
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("/query")
    @ResponseBody
    public EasyUIResult findItemByPage(Integer page, Integer rows) {
        try {
            return itemService.findItemByPage(page,rows);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 查询叶子类目
     * @param itemId
     * @return
     */
    @RequestMapping(value = "/cat/queryItemName" , produces="text/html;charset=utf-8")
    @ResponseBody
    public String findItemCatNameById(Long itemId){
        return itemService.findItemCatNameById(itemId);
    }

    /**
     * 新增商品，对商品表  商品描述表添加
     * @param item
     * @param desc
     * @return
     */
    @RequestMapping("/save")
    @ResponseBody
    public SysResult saveItem(Item item, String desc){
        try {
            itemService.saveItem(item,desc);
            return SysResult.oK();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SysResult.build(201, "商品新增失败");
    }

    /**
     * 实现商品的修改
     */
    @RequestMapping("/update")
    @ResponseBody
    public SysResult updateItem(Item item,String desc) {
        try {
            itemService.updateItem(item,desc);
            return SysResult.oK();
        } catch (Exception e) {
            // TODO: handle exception
        }
        return SysResult.build(201, "商品更新失败");
    }

    /**
     * 商品上架
     * @param ids
     * @return
     */
    @RequestMapping("/reshelf")
    @ResponseBody
    public SysResult reshelf(String[] ids){
        try {
            int status = 1; //1正常
            itemService.updateStatus(ids,status);
            return SysResult.oK();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SysResult.build(201, "商品上架失败");
    }

    /**
     * 商品下架
     * @param ids
     * @return
     */
    @RequestMapping("/instock")
    @ResponseBody
    public SysResult instock(String[] ids){
        try {
            int status = 2; //2下架
            itemService.updateStatus(ids,status);
            return SysResult.oK();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SysResult.build(201, "商品下架失败");
    }

    /**
     * 查询商品详情
     * @param itemId
     * @return
     */
    @RequestMapping("/query/item/desc/{itemId}")
    @ResponseBody
    public SysResult findItemDescById(@PathVariable Long itemId){
        try {
            ItemDesc itemDesc = itemService.findItemDescById(itemId);
            return SysResult.oK(itemDesc);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SysResult.build(201,"商品详情查询失败");
    }


    /**
     * 删除商品表，描述表
     * @param ids
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    public SysResult deleteItem(Long[] ids) {
        try {
            itemService.deleteDesc(ids);
            itemService.deleteItem(ids);
            return SysResult.oK();
        } catch (Exception e) {
            // TODO: handle exception
        }
        return SysResult.build(201, "商品删除失败");
    }
}
