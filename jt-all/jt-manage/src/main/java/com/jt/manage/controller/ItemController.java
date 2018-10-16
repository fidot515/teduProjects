package com.jt.manage.controller;

import java.nio.charset.Charset;

import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.common.vo.EasyUIResult;
import com.jt.common.vo.SysResult;
import com.jt.manage.pojo.Item;
import com.jt.manage.pojo.ItemDesc;
import com.jt.manage.service.ItemService;

@Controller
@RequestMapping("/item")
public class ItemController {
	
	//private static final Logger logger = Logger.getLogger(ItemController.class);
	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ItemController.class);
	@Autowired
	private ItemService itemService;
	
	//实现商品分页查询  http://localhost:8091/item/query?page=1&rows=50
	@RequestMapping("/query")
	@ResponseBody
	public EasyUIResult findItemByPage(int page,int rows){
		
		return itemService.findItemByPage(page,rows);
	}
	
	
	//根据商品分类id查询商品分类名称
	@RequestMapping(value="/cat/queryItemName",produces="text/html;charset=utf-8")
	@ResponseBody
	public String findItemCatNameById(Long itemId){
		
		return itemService.findItemCatNameById(itemId);
	}
	
	//问题1:使用@ResponseBody时处理实体对象时一般不乱码.如果处理String类型可能就乱码
	/**
	 * 原因:因为使用@ResponseBody内部有多重的解析的机制.
	 * 	当解析为string类型时默认以ISO-8859-1的格式进行解析.
	 * 
	 * 	public static final Charset DEFAULT_CHARSET = Charset.forName("ISO-8859-1");
	 *  当解析对象时,默认以UTF-8格式进行解析.
	 *  public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");
	 */
	
	
	//实现商品新增
	@RequestMapping("/save")
	@ResponseBody
	public SysResult saveItem(Item item,String desc){
		try {
			itemService.saveItem(item,desc);
			return SysResult.oK();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201, "商品新增失败");
	}
	
	
	//实现商品的更新操作
	@RequestMapping("/update")
	@ResponseBody
	public SysResult updateItem(Item item,String desc){
		try {
			itemService.updateItem(item,desc);
			//System.out.println("sadfasdfasdfasdafsdfdsa");//执行性能太低
			logger.info("~~~~~~~~~~~~~~~~~~~商品更新成功}");
			logger.debug("~~~~~~~~~~~~~~~~~~~商品更新成功}");
			return SysResult.oK();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return SysResult.build(201,"修改商品数据失败");
	}
	
	//1,2,3,4,5,6
	@RequestMapping("/delete")
	@ResponseBody
	public SysResult deleteItems(Long[] ids){
		try {
			itemService.deleteItems(ids);
			return SysResult.oK();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201,"商品删除失败");
	}
	
	
	//下架
	@RequestMapping("/instock")
	@ResponseBody
	public SysResult instock(Long[] ids){
		try {
			int status = 2;   //商品下架
			itemService.updateStatus(ids,status);
			return SysResult.oK();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201, "商品下架失败");
	}
	
	//商品上架
	@RequestMapping("/reshelf")
	@ResponseBody
	public SysResult reshelf(Long[] ids){
		try {
			int status = 1;   //商品下架
			itemService.updateStatus(ids,status);
			return SysResult.oK();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201, "商品下架失败");
	}
	
	//实现商品描述信息的查询
	@RequestMapping("/query/item/desc/{itemId}")
	@ResponseBody
	public SysResult findItemDescById(@PathVariable Long itemId){
		try {
			ItemDesc itemDesc = itemService.findItemDescById(itemId);
			return SysResult.oK(itemDesc);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201, "商品详情查询失败");
	}
	
	
	
	
	
	
	
	
	
	
}
