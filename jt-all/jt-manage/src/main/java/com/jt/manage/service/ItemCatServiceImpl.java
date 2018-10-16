package com.jt.manage.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.service.RedisService;
import com.jt.manage.mapper.ItemCatMapper;
import com.jt.manage.pojo.ItemCat;
import com.jt.manage.vo.EasyUITree;
import com.sun.tools.jdeps.resources.jdeps;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;

@Service
public class ItemCatServiceImpl implements ItemCatService {
	
	@Autowired
	private ItemCatMapper itemCatMapper;
	
	@Autowired
	private JedisCluster jedisCluster;
	//private RedisService redisService;
	//private Jedis jedis;
	
	private static final ObjectMapper objectMapper = new ObjectMapper();

	/**
	 * 实现思路:
	 * 	1.利用通用Mapper查询商品分类信息
	 *  2.将ItemCatList转化为EasyUITreeList
	 */
	@Override
	public List<EasyUITree> findItemCatList(Long parentId) {
		ItemCat itemCat = new ItemCat();
		itemCat.setParentId(parentId);
		//select * from tb_item_cat where is_parent = 0
		List<ItemCat> itemCatList = itemCatMapper.select(itemCat);
		List<EasyUITree> treeList = new ArrayList<>();
		for (ItemCat itemCatTemp : itemCatList) {
			EasyUITree easyUITree = new EasyUITree();
			easyUITree.setId(itemCatTemp.getId());
			easyUITree.setText(itemCatTemp.getName());
			
			//如果是父级 应该设置为closed,否则open即可
			String state = itemCatTemp.getIsParent() ? "closed" : "open";
			easyUITree.setState(state);
			treeList.add(easyUITree);
		}
		return treeList;
	}

	
	
	/**
	 * 1.查询缓存
	 * 2.判断是否有数据
	 * 3.如果没有数据查询数据库,之后将返回的结果转化为JSON串,在保存到redis
	 * 4.如果查询的结果有数据,需要将json串转化为Java对象之后返回.
	 */
	@Override
	public List<EasyUITree> findItemCatCache(Long parentId) {
		String key = "ITEM_CAT_" +parentId;
		String result = jedisCluster.get(key);	//获取缓存的数据
		List<EasyUITree> treeList = null;
		try {
			if(StringUtils.isEmpty(result)){
				//证明缓存中没有数据
				treeList = findItemCatList(parentId);
				//将数据存入缓存
				String easyUIJSON = 
						objectMapper.writeValueAsString(treeList);
				jedisCluster.set(key, easyUIJSON);
				System.out.println("查询数据库");
			}else{
				EasyUITree[] trees = objectMapper.readValue(result,EasyUITree[].class);
				treeList = Arrays.asList(trees);
				System.out.println("查询缓存");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return treeList;
	}
}
