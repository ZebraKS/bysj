package com.bysj.web.service;

import com.bysj.common.service.HttpClientService;
import com.bysj.common.vo.SysResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class PayServiceImpl implements PayService {

    @Autowired
    HttpClientService httpClient;
    ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String payOrder(String orderId) {
        String url = "http://www.bscart.com/pay/payorder/"+orderId;
        String resultJSON = httpClient.doGet(url);
        SysResult result = null;
        try {
            result = objectMapper.readValue(resultJSON, SysResult.class);
        } catch (Exception e) {
            System.out.println("获取异常"+e.getMessage());
        }
        return (String) result.getData();
    }

    @Override
    public String payCallBack(Map<String, String> params) {
        //把支付宝异步回调参数传到后台
        String url = "http://www.bscart.com/pay/paycallback";
        String result = null;
        try {
            String resultJSON = httpClient.doPost(url,params);
            SysResult sysResult = objectMapper.readValue(resultJSON , SysResult.class);
            if(sysResult.getStatus() == 200) {
                result = (String) sysResult.getData();
                return result;
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new RuntimeException();
        }
        return "false";
    }
}
