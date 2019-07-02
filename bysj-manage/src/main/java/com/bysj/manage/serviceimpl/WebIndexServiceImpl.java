package com.bysj.manage.serviceimpl;

import com.bysj.common.po.Item;
import com.bysj.common.po.WebIndex;
import com.bysj.common.vo.WebIndexBean;
import com.bysj.manage.mapper.WebIndexMapper;
import com.bysj.manage.service.ItemService;
import com.bysj.manage.service.WebIndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WebIndexServiceImpl implements WebIndexService{

    @Autowired
    WebIndexMapper webIndexMapper;

    @Autowired
    ItemService itemService;

    @Override
    public List<WebIndex> findAll() {
        return webIndexMapper.findAll();
    }

    @Override
    public void updateById(WebIndex webIndex) {
        webIndexMapper.updateByPrimaryKey(webIndex);
    }

    @Override
    public List<WebIndexBean> findWebIndexBean() {
        List<WebIndexBean> beanList = new ArrayList<>();
        List<WebIndex> webIndexList = webIndexMapper.findAll();
        for (WebIndex webIndex:webIndexList) {
            WebIndexBean bean = new WebIndexBean();
            Item item = itemService.findItemById(webIndex.getItemId());
            bean.setWebIndex(webIndex);
            bean.setItem(item);
            beanList.add(bean);
        }
        return beanList;
    }
}
