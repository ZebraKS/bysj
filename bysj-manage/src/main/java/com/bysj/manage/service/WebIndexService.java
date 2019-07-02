package com.bysj.manage.service;

import com.bysj.common.po.WebIndex;
import com.bysj.common.vo.WebIndexBean;

import java.util.List;

public interface WebIndexService {
    List<WebIndex> findAll();

    void updateById(WebIndex webIndex);

    List<WebIndexBean> findWebIndexBean();
}
