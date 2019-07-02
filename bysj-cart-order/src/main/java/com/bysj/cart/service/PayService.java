package com.bysj.cart.service;

import com.alipay.api.AlipayApiException;
import com.bysj.common.po.Order;

import java.util.Map;

public interface PayService {

    String payInfo(Order order) throws AlipayApiException;

    //异步回调修改订单状态
    String payNotify(Map<String, String> params);
}
