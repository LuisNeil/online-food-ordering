package com.ltjeda.web.app.onlinefoodordering.service.impl;

import com.ltjeda.web.app.onlinefoodordering.model.Cart;
import com.ltjeda.web.app.onlinefoodordering.model.CartItem;
import com.ltjeda.web.app.onlinefoodordering.model.Food;
import com.ltjeda.web.app.onlinefoodordering.model.User;
import com.ltjeda.web.app.onlinefoodordering.repository.CartItemRepository;
import com.ltjeda.web.app.onlinefoodordering.repository.CartRepository;
import com.ltjeda.web.app.onlinefoodordering.repository.FoodRepository;
import com.ltjeda.web.app.onlinefoodordering.request.AddCartItemRequest;
import com.ltjeda.web.app.onlinefoodordering.service.ICartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CartService implements ICartService {

    private final CartRepository cartRepository;
    private final UserService userService;
    private final CartItemRepository cartItemRepository;
    private final FoodService foodService;


    @Override
    public CartItem addCartItemToCart(AddCartItemRequest request, String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Food food = foodService.findFoodById(request.getMenuItemId());
        Cart cart = cartRepository.findByCostumerId(user.getId());
        for(CartItem item : cart.getItems()) {
            if(item.getFood().equals(food)) {
                int newQuantity = item.getQuantity() + request.getQuantity();
                return updateCartItemQuantity(item.getId(), newQuantity);
            }
        }

        CartItem newItem = CartItem.builder()
                .food(food)
                .cart(cart)
                .quantity(request.getQuantity())
                .ingredients(request.getIngredients())
                .totalPrice(food.getPrice().multiply(new BigDecimal(request.getQuantity())))
                .build();
        CartItem savedItem = cartItemRepository.save(newItem);
        cart.getItems().add(savedItem);
        cartRepository.save(cart);

        return savedItem;
    }

    @Override
    public CartItem updateCartItemQuantity(Long cartItemId, Integer quantity) throws Exception {
        CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(RuntimeException::new);
        cartItem.setQuantity(quantity);
        cartItem.setTotalPrice(cartItem.getTotalPrice().multiply(new BigDecimal(quantity)));
        return cartItemRepository.save(cartItem);
    }

    @Override
    public Cart removeItemFromCart(Long cartItemId, String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Cart cart = cartRepository.findByCostumerId(user.getId());
        CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(RuntimeException::new);
        cartItem.setQuantity(cartItem.getQuantity() - 1);
        cart.getItems().remove(cartItem);
        return cartRepository.save(cart);
    }

    @Override
    public BigDecimal calculateCartTotals(Cart cart) throws Exception {
        return cart.getItems().stream()
                .map(item -> item.getFood()
                        .getPrice()
                        .multiply(new BigDecimal(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public Cart findCartById(Long id) throws Exception {
        return cartRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    @Override
    public Cart findCartByUserId(Long userId) throws Exception {
        Cart cart  = cartRepository.findByCostumerId(userId);
        cart.setTotal(calculateCartTotals(cart));
        return cart;
    }

    @Override
    public Cart clearCart(Long userId) throws Exception {
        Cart cart = findCartByUserId(userId);
        cart.getItems().clear();
        return cartRepository.save(cart);
    }
}
