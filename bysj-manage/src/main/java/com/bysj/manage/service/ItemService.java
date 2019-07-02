package com.bysj.manage.service;

import com.bysj.common.po.Item;
import com.bysj.common.po.ItemDesc;
import com.bysj.common.vo.EasyUIResult;
import com.bysj.common.vo.SysResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.ibatis.annotations.Select;

import java.io.IOException;

public interface ItemService {

    EasyUIResult findItemByPage(Integer page, Integer rows) throws IOException;

    String findItemCatNameById(Long itemId);

    void saveItem(Item item, String desc);

    void updateItem(Item item, String desc);

    void updateStatus(String[] ids,int status);

    ItemDesc findItemDescById(Long itemId);

    void deleteItem(Long[] ids);

    void deleteDesc(Long[] ids);

    Item findItemById(Long itemId);
}
