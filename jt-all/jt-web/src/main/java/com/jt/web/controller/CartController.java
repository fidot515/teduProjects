package com.jt.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.common.vo.SysResult;
import com.jt.web.pojo.Cart;
import com.jt.web.pojo.User;
import com.jt.web.service.CartService;
import com.jt.web.thread.UserThreadLocal;

@Controller
@RequestMapping("/cart")
public class CartController {
	
	@Autowired
	private CartService cartService;
	
	//http://www.jt.com/cart/show.html
	@RequestMapping("/show")
	public String findCartByUserId(Model model,HttpServletRequest request){
		//User user = (User) request.getSession().getAttribute("JT_USER");
		//Long userId = user.getId();	//暂时写死
		
		Long userId = UserThreadLocal.get().getId();
		//获取购物车列表信息
		List<Cart> cartList = cartService.findCartByUserId(userId);
		model.addAttribute("cartList", cartList);
		return "cart"; //返回购物车页面
	}
	
	@RequestMapping("/add/{itemId}")
	public String saveCart(@PathVariable Long itemId,Cart cart){
		
		Long userId = UserThreadLocal.get().getId();
		cart.setUserId(userId);
		cart.setItemId(itemId);
		cartService.saveCart(cart);
		//跳转到购物车列表页面
		return "redirect:/cart/show.html";
	}
	
	///cart/update/num/562379/16.html
	//www.jt.com/cart/update/num/562379/16.html
	@RequestMapping("/update/num/{itemId}/{num}")
	@ResponseBody
	public SysResult updateCartNum(@PathVariable Long itemId,@PathVariable Integer num){
		try {
			//1.获取用户Id
			Long userId = UserThreadLocal.get().getId();
			Cart cart = new Cart();
			cart.setUserId(userId);
			cart.setItemId(itemId);
			cart.setNum(num);
			cartService.updateCartNum(cart);
			return SysResult.oK();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return SysResult.build(201, "购物车数量修改失败");
	}
	
	//购物车删除
	@RequestMapping("/delete/{itemId}")
	public String deleteCarts(@PathVariable Long itemId){
		Long userId = UserThreadLocal.get().getId();
		cartService.deleteCart(itemId,userId);
		//重定向到购物车列表页面
		return "redirect:/cart/show.html";
	}
	
	
	
	
	
	
}
