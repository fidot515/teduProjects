package com.jt.manage.service;

import java.util.List;

import com.jt.manage.vo.EasyUITree;

public interface ItemCatService {

	List<EasyUITree> findItemCatList(Long parentId);
	
	//通过parentid查询缓存操作
	List<EasyUITree> findItemCatCache(Long parentId);

}
