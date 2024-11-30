package com.ltjeda.web.app.onlinefoodordering.controller;

import com.ltjeda.web.app.onlinefoodordering.model.Order;
import com.ltjeda.web.app.onlinefoodordering.model.User;
import com.ltjeda.web.app.onlinefoodordering.request.CreateOrderRequest;
import com.ltjeda.web.app.onlinefoodordering.service.IOrderService;
import com.ltjeda.web.app.onlinefoodordering.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {

    private final IOrderService orderService;
    private final IUserService userService;

    @PostMapping("/create-order")
    public ResponseEntity<Order> createOrder(@RequestBody CreateOrderRequest request,
                                             @RequestHeader("Authorization") String token) throws Exception {
        User user = userService.findUserByJwtToken(token);
        Order order = orderService.createOrder(request, user);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @GetMapping("/user-orders")
    public ResponseEntity<List<Order>> getOrderHistory(@RequestHeader("Authorization") String token) throws Exception {
        User user = userService.findUserByJwtToken(token);
        List<Order> orders = orderService.getAllUsersOrders(user.getId());
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }


}
