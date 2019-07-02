package com.bysj.cart.service;

import com.alibaba.druid.util.StringUtils;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.bysj.cart.mapper.OrderItemMapper;
import com.bysj.cart.mapper.OrderMapper;
import com.bysj.common.po.Order;
import com.bysj.common.po.OrderItem;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.JedisCluster;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
public class PayServiceImpl implements PayService{

    @Autowired
    OrderMapper orderMapper;
    @Autowired
    private JedisCluster jedisCluster;
    ObjectMapper objectMapper = new ObjectMapper();

    //返回支付页面html
    @Override
    public String payInfo(Order order) throws AlipayApiException {
        //获得初始化的AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);

        //设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl(AlipayConfig.return_url);
        alipayRequest.setNotifyUrl(AlipayConfig.notify_url);

        //商户订单号，商户网站订单系统中唯一订单号，必填
        String out_trade_no =order.getOrderId();
        //付款金额，必填
        String total_amount = order.getPayment()+"";
        //订单名称，必填
        String subject ="订单商品支付费用";
//		//商品描述，可空
//		String body = new String(request.getParameter("WIDbody").getBytes("ISO-8859-1"),"UTF-8");

        alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\","
                + "\"total_amount\":\""+ total_amount +"\","
                + "\"subject\":\""+ subject +"\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");

        //若想给BizContent增加其他可选请求参数，以增加自定义超时时间参数timeout_express来举例说明
        //alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\","
        //		+ "\"total_amount\":\""+ total_amount +"\","
        //		+ "\"subject\":\""+ subject +"\","
        //		+ "\"body\":\""+ body +"\","
        //		+ "\"timeout_express\":\"10m\","
        //		+ "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
        //请求参数可查阅【电脑网站支付的API文档-alipay.trade.page.pay-请求参数】章节

        //请求
        String result = alipayClient.pageExecute(alipayRequest).getBody();
        return result;

    }

    public String payNotify(Map<String, String> params){
        String orderId = params.get("out_trade_no");
        Order order = orderMapper.selectByPrimaryKey(params.get("out_trade_no"));
        if(StringUtils.isEmpty(jedisCluster.get(orderId))){
            // token失效 订单超时 或者已经支付
            return "false";
        }
        if(order.getPayment().equals(params.get("receipt_amount")) && "TRADE_SUCCESS".equals(params.get("trade_status"))){
            //支付金额 == 应付金额 ， 并且支付成功  修改订单状态 情况token
            order.setStatus(2);
            orderMapper.updateByPrimaryKeySelective(order);
            jedisCluster.del(orderId);
            return "success";
        }
        return "false";
    }
}
