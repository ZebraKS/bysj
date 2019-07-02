package com.bysj.cart.controller;

import com.alipay.api.AlipayApiException;
import com.bysj.cart.service.OrderService;
import com.bysj.cart.service.PayService;
import com.bysj.common.po.Order;
import com.bysj.common.vo.SysResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping("/pay")
public class PayController {

    @Autowired
    PayService payService;

    @Autowired
    OrderService orderService;


    //支付宝异步回调接口
    @RequestMapping("/paycallback")
    @ResponseBody
    public SysResult payNotify(@RequestParam Map<String, String> params){
        String str = null;
        try {
            str = payService.payNotify(params);
            return SysResult.oK(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SysResult.build(200,"false");
    }

    @RequestMapping(value = "/payorder/{orderId}")
    @ResponseBody
    public SysResult pay(@PathVariable String orderId) {
        try {
            Order order = orderService.findOrderById(orderId);
            return SysResult.oK(payService.payInfo(order));
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return SysResult.build(201,"支付请求失败！");
    }
}
