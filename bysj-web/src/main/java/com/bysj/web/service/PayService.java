package com.bysj.web.service;

import java.util.Map;

public interface PayService {

    String payOrder(String orderId);

    String payCallBack(Map<String, String> params);
}
