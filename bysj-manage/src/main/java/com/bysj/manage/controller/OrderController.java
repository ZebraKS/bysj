package com.bysj.manage.controller;

import com.bysj.common.po.Order;
import com.bysj.common.po.OrderItem;
import com.bysj.common.po.OrderShipping;
import com.bysj.common.vo.SysResult;
import com.bysj.manage.service.OrderService;
import com.bysj.manage.vo.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * 订单管理分页查询
     * 条件查询
     * @param currentPage
     * @param row
     * @param model
     * @return
     */
    @RequestMapping("/orderList")
    public String orderList(@RequestParam(defaultValue = "0")Integer currentPage , Integer row , Model model , Order order) {
        PageBean pb = null;
        try {
            pb = orderService.orderList(currentPage,row,order);
        } catch (Exception e) {
            e.printStackTrace();
        }
        model.addAttribute("pb",pb);
        return "order-list";
    }

    /**
     * 删除单个订单
     * @param orderId
     * @param model
     * @return
     */
    @RequestMapping("/deleteOrder")
    public String deleteOrder(String orderId , Model model) {
        orderService.deleteOrder(orderId);
        PageBean<Order> pb = null;
        try {
            pb = orderService.orderList(1,10, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        model.addAttribute("pb",pb);
        return "order-list";
    }

    /**
     * 删除单个订单
     * @param orderIds
     * @param model
     * @return
     */
    @RequestMapping("/deleteOrders")
    public String deleteOrders(String[] orderIds , Model model) {
        orderService.deleteOrders(orderIds);
        PageBean pb = null;
        try {
            pb = orderService.orderList(1,10, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        model.addAttribute("pb",pb);
        return "order-list";
    }

    /**
     * 根据orderId查询 物流信息，商品信息
     * @param orderId
     * @param model
     * @return
     */
    @RequestMapping("/findOrderShippingByOrderId/{orderId}")
    public String findOrderShippingByOrderId(@PathVariable(value = "orderId") String orderId , Model model){
        try {
            OrderShipping orderShipping = orderService.findOrderShippingByOrderId(orderId);
            List<OrderItem> orderItems = orderService.findOrderItemsByOrderId(orderId);
            model.addAttribute("orderShipping",orderShipping);
            model.addAttribute("orderItems",orderItems);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "order-shipping";
    }

    /**
     * 已支付的订单发货 ， 状态2到4
     * @param orderId
     * @return
     */
    @RequestMapping("/sendItem")
    @ResponseBody
    public SysResult sendItem(String orderId) {
        try {
            orderService.sendItem(orderId);
            return SysResult.oK();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SysResult.build(201,"发货失败！");
    }
}
