package com.jt.web.service;

import com.jt.web.pojo.Item;
import com.jt.web.pojo.ItemDesc;

public interface ItemService {

	Item findItemById(Long itemId);
	
	//根据商品的Id获取商品的描述信息
	ItemDesc findItemDescById(Long itemId);

}
