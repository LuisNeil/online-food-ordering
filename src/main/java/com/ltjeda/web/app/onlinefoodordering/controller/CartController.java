package com.ltjeda.web.app.onlinefoodordering.controller;

import com.ltjeda.web.app.onlinefoodordering.model.Cart;
import com.ltjeda.web.app.onlinefoodordering.model.CartItem;
import com.ltjeda.web.app.onlinefoodordering.model.User;
import com.ltjeda.web.app.onlinefoodordering.request.AddCartItemRequest;
import com.ltjeda.web.app.onlinefoodordering.request.UpdateCartItemRequest;
import com.ltjeda.web.app.onlinefoodordering.service.ICartService;
import com.ltjeda.web.app.onlinefoodordering.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cart")
public class CartController {

    private final ICartService cartService;
    private final IUserService userService;

    @PostMapping("/add-item")
    public ResponseEntity<CartItem> addItemToCart(@RequestBody AddCartItemRequest request,
                                                  @RequestHeader("Authorization") String token) throws Exception {
        return new ResponseEntity<>(cartService.addCartItemToCart(request, token), HttpStatus.OK);
    }

    @PutMapping("/update-cart")
    public ResponseEntity<CartItem> updateCartItem(@RequestBody UpdateCartItemRequest request,
                                                   @RequestHeader("Authorization") String token) throws Exception {
        return new ResponseEntity<>(cartService.updateCartItemQuantity(request.getCartItemId(), request.getQuantity()), HttpStatus.OK);
    }

    @PatchMapping("/remove-item/{itemId}")
    public ResponseEntity<Cart> removeItemFromCart(@PathVariable Long itemId, @RequestHeader("Authorization") String token) throws Exception {
        return new ResponseEntity<>(cartService.removeItemFromCart(itemId, token), HttpStatus.OK);
    }

    @DeleteMapping("/clear-cart")
    public ResponseEntity<Cart> clearCart(@RequestHeader("Authorization") String token) throws Exception {
        User user = userService.findUserByJwtToken(token);
        return new ResponseEntity<>(cartService.clearCart(user.getId()), HttpStatus.NO_CONTENT);
    }

    @GetMapping("/total")
    public ResponseEntity<BigDecimal> calculateCartTotal(@RequestHeader("Authorization") String token) throws Exception {
        User user = userService.findUserByJwtToken(token);
        Cart cart = cartService.findCartByUserId(user.getId());
        return new ResponseEntity<>(cartService.calculateCartTotals(cart), HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<Cart> findUserCart(@RequestHeader("Authorization") String token) throws Exception {
        return new ResponseEntity<>(cartService.findCartByUserId(userService.findUserByJwtToken(token).getId()), HttpStatus.OK);
    }


}
