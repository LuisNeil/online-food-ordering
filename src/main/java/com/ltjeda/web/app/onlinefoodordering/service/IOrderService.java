package com.ltjeda.web.app.onlinefoodordering.service;

import com.ltjeda.web.app.onlinefoodordering.model.Order;
import com.ltjeda.web.app.onlinefoodordering.model.User;
import com.ltjeda.web.app.onlinefoodordering.request.CreateOrderRequest;

import java.util.List;

public interface IOrderService {
    Order createOrder(CreateOrderRequest order, User user) throws Exception;
    Order updateOrder(Long orderId, String orderStatus) throws Exception;
    void cancelOrder(Long orderId) throws Exception;
    List<Order> getAllUsersOrders(Long userId) throws Exception;
    List<Order> getAllRestaurantOrders(Long restaurantId, String orderStatus) throws Exception;
    Order findOrderById(Long orderId) throws Exception;
}
