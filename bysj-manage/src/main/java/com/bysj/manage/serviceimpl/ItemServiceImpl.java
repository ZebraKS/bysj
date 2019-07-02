package com.bysj.manage.serviceimpl;

import com.alibaba.druid.util.StringUtils;
import com.bysj.common.po.Item;
import com.bysj.common.po.ItemDesc;
import com.bysj.common.vo.EasyUIResult;
import com.bysj.manage.mapper.ItemDescMapper;
import com.bysj.manage.mapper.ItemMapper;
import com.bysj.manage.service.ItemService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.JedisCluster;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class ItemServiceImpl implements ItemService {
    @Autowired
    private ItemMapper itemMapper;

    @Autowired
    private ItemDescMapper itemDescMapper;

    @Autowired
    private JedisCluster jedisCluster;
    private ObjectMapper mapper = new ObjectMapper();
    /**
     * 查询商品
     * @param page
     * @param rows
     * @return
     */
    @Override
    public EasyUIResult findItemByPage(Integer page, Integer rows) throws IOException {
        String itemJson = jedisCluster.get("item"+page.toString());
        if(StringUtils.isEmpty(itemJson)){
            int total = itemMapper.selectCount(null);
            int start = (page-1)*rows;
            List<Item> list = itemMapper.findItemByPage(start, rows);
            itemJson = mapper.writeValueAsString(list);
            jedisCluster.set("item"+page.toString(),itemJson);
            jedisCluster.lpush("itemNum","item"+page.toString());
            //查数据库
            return new EasyUIResult(total,list);
        }else {
            List<Item> list = mapper.readValue(itemJson, new TypeReference<List<Item>>() { });
            int total = itemMapper.selectCount(null);
            //查缓存
            return new EasyUIResult(total,list);
        }
    }

    /**
     * 查询叶子类目
     * @param itemId
     * @return
     */
    @Override
    public String findItemCatNameById(Long itemId) {
        return itemMapper.findItemCatNameById(itemId);
    }

    @Override
    public void saveItem(Item item, String desc) {

        //商品信息入库
        item.setStatus(1);
        item.setCreated(new Date());
        item.setUpdated(item.getCreated());
        itemMapper.insert(item);

        //商品描述信息入库
        ItemDesc itemDesc = new ItemDesc();
        itemDesc.setItemId(item.getId());
        itemDesc.setItemDesc(desc);
        itemDesc.setCreated(item.getCreated());
        itemDesc.setUpdated(item.getCreated());
        itemDescMapper.insert(itemDesc);
        deleteItemRedis();
    }

    @Override
    public void updateItem(Item item, String desc) {
        //更新商品信息
        item.setUpdated(new Date());
        itemMapper.updateByPrimaryKeySelective(item);

        //更新商品描述信息
        ItemDesc itemDesc = new ItemDesc();
        itemDesc.setItemId(item.getId());
        itemDesc.setItemDesc(desc);
        itemDesc.setUpdated(item.getUpdated());
        itemDescMapper.updateByPrimaryKey(itemDesc);
        deleteItemRedis();
    }

    /**
     * 上架，下架，更新status
     * @param ids
     * @param status
     */
    @Override
    public void updateStatus(String[] ids, int status) {
        itemMapper.updateStatus(ids,status);
        deleteItemRedis();
    }

    private void deleteItemRedis() {
        List<String> list = jedisCluster.lrange("itemNum", 0, -1);
        for (int i = 0; i < list.size(); i++) {
            jedisCluster.lpop("itemNum");
        }
        for (String s:list) {
            jedisCluster.del(s);
        }
    }

    @Override
    public ItemDesc findItemDescById(Long itemId) {
        return itemDescMapper.selectByPrimaryKey(itemId);
    }

    @Override
    public void deleteItem(Long[] ids) {
        itemMapper.deleteByIDS(ids);
        deleteItemRedis();
    }

    @Override
    public void deleteDesc(Long[] ids) {
        itemDescMapper.deleteByIDS(ids);
    }

    @Override
    public Item findItemById(Long itemId) {
        return itemMapper.selectByPrimaryKey(itemId);
    }


}
