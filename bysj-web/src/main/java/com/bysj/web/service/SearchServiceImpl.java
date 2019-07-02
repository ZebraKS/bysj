package com.bysj.web.service;

import com.bysj.common.po.Cart;
import com.bysj.common.po.Item;
import com.bysj.common.service.HttpClientService;
import com.bysj.common.vo.SysResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SearchServiceImpl implements SearchService{

    @Autowired
    private HttpClientService httpClient;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public List<Item> findItemByKey(String key) {

        String url = "http://localhost:9999/search";
        Map<String, String> params = new HashMap<>();
        params.put("key", key);
        List<Item> list = new ArrayList<>();
        String resultJSON = null;
        try {
            resultJSON = httpClient.doPost(url, params);
            SysResult sysResult = objectMapper.readValue(resultJSON, SysResult.class);
            if (sysResult.getStatus() == 200) {
                list = (List<Item>) sysResult.getData();
            } else {
                System.out.println("后台查询数据失败");
                throw new RuntimeException();
            }
        } catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        return list;
    }
}
