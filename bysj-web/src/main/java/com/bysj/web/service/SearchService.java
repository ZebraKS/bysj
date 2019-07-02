package com.bysj.web.service;

import com.bysj.common.po.Item;

import java.util.List;

public interface SearchService {

    List<Item> findItemByKey(String key);
}
