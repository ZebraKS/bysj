package com.heida.bysjsearch.service;

import java.util.List;

import com.heida.bysjsearch.pojo.Item;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class SearchServiceImpl implements SearchService{

	@Autowired
	HttpSolrClient httpSolrClient;
	@Override
	public List<Item> findItemByKey(String key) {
		try {
			
			SolrQuery query=new SolrQuery(key);
			//mysql limit 0,100
			query.setStart(0);//从第一个数据开始查
			query.setRows(100);//查100条数据
			QueryResponse response=
					httpSolrClient.query(query);
			List<Item> itemList = response.getBeans(Item.class);
			return itemList;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


}
