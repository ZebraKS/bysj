package com.heida.bysjsearch.service;

import com.heida.bysjsearch.pojo.Item;

import java.util.List;

public interface SearchService {

    List<Item> findItemByKey(String key);
}
