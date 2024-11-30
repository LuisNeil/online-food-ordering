package com.ltjeda.web.app.onlinefoodordering.service;

import com.ltjeda.web.app.onlinefoodordering.model.Category;
import com.ltjeda.web.app.onlinefoodordering.model.Food;
import com.ltjeda.web.app.onlinefoodordering.model.Restaurant;
import com.ltjeda.web.app.onlinefoodordering.request.CreateFoodRequest;

import java.util.List;

public interface IFoodService {

    Food createFood(CreateFoodRequest request, Category category, Restaurant restaurant);
    void deleteFood(Long foodId) throws Exception;
    List<Food> getRestaurantFoods(Long restaurantId, boolean isVegetarian, boolean isNonVeg, boolean isSeasonal, String foodCategory);
    List<Food> searchFood(String keyword);
    Food findFoodById(Long foodId);
    Food updateAvailabilityStatus(Long foodId);

}
