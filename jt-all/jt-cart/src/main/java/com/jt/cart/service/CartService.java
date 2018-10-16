package com.jt.cart.service;

import java.util.List;

import com.jt.cart.pojo.Cart;

public interface CartService {

	List<Cart> findCartByUserId(Long userId);

	void saveCart(Cart cart);

	void updateCartNum(Cart cart);

	void deleteCart(Long userId, Long itemId);

}
