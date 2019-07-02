package com.bysj.manage.controller;

import com.bysj.common.po.WebIndex;
import com.bysj.common.vo.SysResult;
import com.bysj.common.vo.WebIndexBean;
import com.bysj.manage.service.WebIndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/webindex")
public class WebIndexController {

    @Autowired
    WebIndexService webIndexService;

    /**
     * 显示所有 首页
     * @return
     */
    @RequestMapping("/findAll")
    public String findAll(Model model) {
        List<WebIndex> webIndexList = webIndexService.findAll();
        model.addAttribute("webIndexList",webIndexList);
        return "webindex-list";
    }

    /**
     * 显示修改页面
     * @param webIndex
     * @param model
     * @return
     */
    @RequestMapping("/showUpdateById")
    public String showUpdateById(WebIndex webIndex , Model model) {
        model.addAttribute("webIndex",webIndex);
        return "webindex-update";
    }

    /**
     * 保存新信息
     * @param webIndex
     * @return
     */
    @RequestMapping("/updateById")
    @ResponseBody
    public String updateById(WebIndex webIndex) {
        try {
            webIndexService.updateById(webIndex);
            return "Update OK!";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Update Fail!";
    }

    /**
     * 查询首页信息
     * @param webIndex
     * @return
     */
    @RequestMapping("/findIndexAll")
    @ResponseBody
    public SysResult findIndexAll(WebIndex webIndex) {
        try {
            List<WebIndexBean> webIndexList = webIndexService.findWebIndexBean();
            return SysResult.oK(webIndexList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SysResult.build(201,"获取信息失败！");
    }
}
