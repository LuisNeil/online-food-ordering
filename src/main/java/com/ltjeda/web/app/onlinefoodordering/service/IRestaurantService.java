package com.ltjeda.web.app.onlinefoodordering.service;

import com.ltjeda.web.app.onlinefoodordering.dto.RestaurantDto;
import com.ltjeda.web.app.onlinefoodordering.model.Restaurant;
import com.ltjeda.web.app.onlinefoodordering.model.User;
import com.ltjeda.web.app.onlinefoodordering.request.CreateRestaurantRequest;


import java.util.List;

public interface IRestaurantService {
    Restaurant createRestaurant(CreateRestaurantRequest restaurantRequest, User user);
    Restaurant updateRestaurant(Long restaurantId, CreateRestaurantRequest restaurantRequest) throws Exception;
    void deleteRestaurant(Long restaurantId) throws Exception;
    List<Restaurant> getAllRestaurants();
    List <Restaurant> searchRestaurant(String keyword);
    Restaurant findRestaurantById(Long restaurantId) throws Exception;
    Restaurant getRestaurantByUserId(Long userId) throws Exception;
    RestaurantDto addToFavorites(Long restaurantId, User user) throws Exception;
    Restaurant updateRestaurantStatus(Long restaurantId) throws Exception;

}
