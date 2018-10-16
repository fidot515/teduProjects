package com.jt.web.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.service.HttpClientService;
import com.jt.web.pojo.Item;
import com.jt.web.pojo.ItemDesc;

@Service
public class ItemServiceImpl implements ItemService {
	
	@Autowired
	private HttpClientService httpClient;
	private static final ObjectMapper objectMapper = new ObjectMapper();
	
	
	//前台获取后台的商品信息
	@Override
	public Item findItemById(Long itemId) {
		String url = "http://manage.jt.com/web/item/findItemById";
		Map<String, String> params = new HashMap<String, String>();
		params.put("itemId", itemId+"");
		Item item = null;
		
		//返回的Item的JSON
		String resultJSON = httpClient.doPost(url, params);
		try {
			item = objectMapper.readValue(resultJSON, Item.class);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		return item;
	}


	@Override
	public ItemDesc findItemDescById(Long itemId) {
		String url = "http://manage.jt.com/web/item/findItemDescById";
		Map<String, String> params = new HashMap<String, String>();
		params.put("itemId", itemId+"");
		ItemDesc itemDesc = null;
		
		//返回的Item的JSON
		String resultJSON = httpClient.doPost(url, params);
		try {
			itemDesc = objectMapper.readValue(resultJSON, ItemDesc.class);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		return itemDesc;
	}
	
}
