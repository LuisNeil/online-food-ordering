package com.ltjeda.web.app.onlinefoodordering.service;

import com.ltjeda.web.app.onlinefoodordering.model.Cart;
import com.ltjeda.web.app.onlinefoodordering.model.CartItem;
import com.ltjeda.web.app.onlinefoodordering.request.AddCartItemRequest;

import java.math.BigDecimal;

public interface ICartService {

    CartItem addCartItemToCart(AddCartItemRequest request, String jwt) throws Exception;
    CartItem updateCartItemQuantity(Long cartItemId, Integer quantity) throws Exception;
    Cart removeItemFromCart(Long cartItemId, String jwt) throws Exception;
    BigDecimal calculateCartTotals(Cart cart) throws Exception;
    Cart findCartById(Long id) throws Exception;
    Cart findCartByUserId(Long userId) throws Exception;
    Cart clearCart(Long userId) throws Exception;
}
