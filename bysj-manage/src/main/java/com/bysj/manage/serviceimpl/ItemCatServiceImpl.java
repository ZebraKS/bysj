package com.bysj.manage.serviceimpl;

import com.alibaba.druid.util.StringUtils;
import com.bysj.common.po.ItemCat;
import com.bysj.manage.mapper.ItemCatMapper;
import com.bysj.manage.service.ItemCatService;
import com.bysj.manage.vo.EasyUITree;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.JedisCluster;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ItemCatServiceImpl implements ItemCatService {

    @Autowired
    private ItemCatMapper itemCatMapper;

    @Autowired
    JedisCluster cluster;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public List<EasyUITree> findItemCatById(Long parentId) {
        ItemCat itemCat = new ItemCat();
        itemCat.setParentId(parentId);
        List<ItemCat> itemCatList = itemCatMapper.select(itemCat);
        List<EasyUITree> list = new ArrayList<>();
        for (ItemCat c : itemCatList) {
            EasyUITree easyUITree = new EasyUITree();
            easyUITree.setId(c.getId());
            easyUITree.setText(c.getName());
            if (c.getIsParent()) {
                easyUITree.setState("closed");
            } else {
                easyUITree.setState("open");
            }
            list.add(easyUITree);
        }
        return list;
    }

    @Override
    public List<EasyUITree> findCacheItemCatById(Long parentId) {
        List<EasyUITree> list = new ArrayList<EasyUITree>();
        String key = "Item_Cat_" + parentId;
        String valueJeson = cluster.get(key);
        if(StringUtils.isEmpty(valueJeson)) {
            list = findItemCatById(parentId);
            System.out.println("从数据库读取");
            try {
                cluster.set(key, objectMapper.writeValueAsString(list));
            } catch (JsonProcessingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }else {
            try {
                list = objectMapper.readValue(valueJeson, list.getClass());
                System.out.println("从缓存读取");
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return list;
    }
}
