package com.bysj.web.controller;

import com.bysj.web.service.PayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

@Controller
@RequestMapping("/pay")
public class PayController {

    @Autowired
    PayService payService;


    @RequestMapping("/payorder/{orderId}")
    public void payOrder(@PathVariable String orderId, HttpServletResponse response){
        try {
            response.setContentType("text/html;charset=utf-8");
            PrintWriter writer = response.getWriter();
            writer.println(payService.payOrder(orderId));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/paycallback")
    @ResponseBody
    public String payCallBack(@RequestParam Map<String, String> params){
        try {
            return payService.payCallBack(params);
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return "false";
    }
}
