package com.jt.web.service;

import java.util.List;

import com.jt.web.pojo.Cart;

public interface CartService {

	List<Cart> findCartByUserId(Long userId);

	void saveCart(Cart cart);

	void updateCartNum(Cart cart);

	void deleteCart(Long itemId, Long userId);

}
