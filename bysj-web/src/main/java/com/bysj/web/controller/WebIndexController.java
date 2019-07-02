package com.bysj.web.controller;

import com.bysj.common.po.WebIndex;
import com.bysj.common.vo.WebIndexBean;
import com.bysj.web.service.WebIndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class WebIndexController {

    @Autowired
    WebIndexService webIndexService;
    @RequestMapping("/index")
    public String index(Model model){
        List<WebIndexBean> beanList = webIndexService.findIndexAll();
        model.addAttribute("beanList",beanList);
        return "index";
    }
}
