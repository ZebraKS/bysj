package com.bysj.manage.service;

import com.bysj.manage.vo.EasyUITree;

import java.util.List;

public interface ItemCatService {

    List<EasyUITree> findItemCatById(Long parentId);

    List<EasyUITree> findCacheItemCatById(Long parentId);
}
