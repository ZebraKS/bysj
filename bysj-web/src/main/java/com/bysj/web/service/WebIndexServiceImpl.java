package com.bysj.web.service;

import com.bysj.common.service.HttpClientService;
import com.bysj.common.vo.SysResult;
import com.bysj.common.vo.WebIndexBean;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class WebIndexServiceImpl implements WebIndexService {
    @Autowired
    private HttpClientService httpClient;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public List<WebIndexBean> findIndexAll() {
        String url = "http://www.bsmanage.com/webindex/findIndexAll";
        String resultJSON = httpClient.doGet(url);
        List<WebIndexBean> list = new ArrayList<>();
        try {
            SysResult sysResult = objectMapper.readValue(resultJSON, SysResult.class);
            if (sysResult.getStatus() == 200) {
                list = (List<WebIndexBean>) sysResult.getData();
            } else {
                System.out.println("后台查询数据失败");
                throw new RuntimeException();
            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            System.out.println("数据解析失败！");
            e.printStackTrace();
        }
        return list;
    }
}
