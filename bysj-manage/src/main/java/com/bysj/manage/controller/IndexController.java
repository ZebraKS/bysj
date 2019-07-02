package com.bysj.manage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
    @RequestMapping("/index")
    public String index() {
        return "index";
    }

    /**
     * Index页面通用加载
     */
    @RequestMapping("/page/{moduleName}")
    public String moduleName(@PathVariable String moduleName) {
        return moduleName;
    }
}
