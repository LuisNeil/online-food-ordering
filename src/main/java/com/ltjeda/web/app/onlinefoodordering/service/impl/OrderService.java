package com.ltjeda.web.app.onlinefoodordering.service.impl;

import com.ltjeda.web.app.onlinefoodordering.model.*;
import com.ltjeda.web.app.onlinefoodordering.repository.*;
import com.ltjeda.web.app.onlinefoodordering.request.CreateOrderRequest;
import com.ltjeda.web.app.onlinefoodordering.service.IOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final AddressRepository addressRepository;
    private final UserRepository userRepository;
    private final RestaurantService restaurantService;
    private final CartService cartService;


    @Override
    public Order createOrder(CreateOrderRequest order, User user) throws Exception {
        Address shippingAddress = order.getAddress();
        Address savedAddress = addressRepository.save(shippingAddress);
        if(!user.getAddresses().contains(savedAddress)) {
            user.getAddresses().add(savedAddress);
        }
        userRepository.save(user);
        Restaurant restaurant = restaurantService.findRestaurantById(order.getRestaurantId());
        Order createdOrder = Order.builder()
                .customer(user)
                .createdAt(new Date())
                .restaurant(restaurant)
                .deliveryAddress(savedAddress)
                .orderStatus("PENDING")
                .build();

        Cart cart = cartService.findCartByUserId(user.getId());
        List<OrderItem> orderItems = new ArrayList<>();
        for(CartItem cartItem : cart.getItems()){
            OrderItem orderItem = OrderItem.builder()
                    .food(cartItem.getFood())
                    .ingredients(cartItem.getIngredients())
                    .quantity(cartItem.getQuantity())
                    .totalPrice(cartItem.getFood().getPrice().multiply(new BigDecimal(cartItem.getQuantity())))
                    .build();
            OrderItem savedOrderItem = orderItemRepository.save(orderItem);
            orderItems.add(savedOrderItem);
        }

        createdOrder.setItems(orderItems);
        createdOrder.setTotalPrice(cartService.calculateCartTotals(cart));

        Order savedOrder = orderRepository.save(createdOrder);
        restaurant.getOrders().add(savedOrder);
        return createdOrder;
    }

    @Override
    public Order updateOrder(Long orderId, String orderStatus) throws Exception {
        Order order = findOrderById(orderId);
        if(orderStatus.equals("OUT_FOR_DELIVERY") || orderStatus.equals("DELIVERED")
        || orderStatus.equals("COMPLETED") || orderStatus.equals("PENDING")){
            order.setOrderStatus(orderStatus);
            return orderRepository.save(order);
        }
        throw new Exception("Please select a valid order status");
    }

    @Override
    public void cancelOrder(Long orderId) throws Exception {
        orderRepository.findById(orderId).ifPresentOrElse(orderRepository::delete, ()-> {
            throw new RuntimeException("Order not found");
        });
    }

    @Override
    public List<Order> getAllUsersOrders(Long userId) throws Exception {
        return orderRepository.findByCustomerId(userId);
    }

    @Override
    public List<Order> getAllRestaurantOrders(Long restaurantId, String orderStatus) throws Exception {
        List<Order> restaurantOrders = orderRepository.findByRestaurantId(restaurantId);
        if(orderStatus != null){
            restaurantOrders = restaurantOrders.stream().filter(order -> order.getOrderStatus().equals(orderStatus))
                    .toList();
        }
        return restaurantOrders;
    }

    @Override
    public Order findOrderById(Long orderId) throws Exception {
        return orderRepository.findById(orderId).orElseThrow(() -> new Exception("Order not found"));
    }
}
