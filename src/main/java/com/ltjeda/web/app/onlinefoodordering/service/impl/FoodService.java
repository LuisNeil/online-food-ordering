package com.ltjeda.web.app.onlinefoodordering.service.impl;

import com.ltjeda.web.app.onlinefoodordering.model.Category;
import com.ltjeda.web.app.onlinefoodordering.model.Food;
import com.ltjeda.web.app.onlinefoodordering.model.Restaurant;
import com.ltjeda.web.app.onlinefoodordering.repository.FoodRepository;
import com.ltjeda.web.app.onlinefoodordering.request.CreateFoodRequest;
import com.ltjeda.web.app.onlinefoodordering.service.IFoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FoodService implements IFoodService {

    private final FoodRepository foodRepository;

    @Override
    public Food createFood(CreateFoodRequest request, Category category, Restaurant restaurant) {
        Food food = Food.builder()
                .foodCategory(category)
                .restaurant(restaurant)
                .description(request.getDescription())
                .images(request.getImages())
                .name(request.getName())
                .price(request.getPrice())
                .ingredients(request.getIngredients())
                .isSeasonal(request.isSeasonal())
                .isVegetarian(request.isVegetarian())
                .build();
        Food savedFood = foodRepository.save(food);
        restaurant.getFoods().add(savedFood);
        return savedFood;
    }

    @Override
    public void deleteFood(Long foodId) throws Exception {
        Food food = findFoodById(foodId);
        food.setRestaurant(null);
        foodRepository.delete(food);
    }

    @Override
    public List<Food> getRestaurantFoods(Long restaurantId,
                                         boolean isVegetarian,
                                         boolean isNonVeg,
                                         boolean isSeasonal,
                                         String foodCategory) {
        List<Food> foods = foodRepository.findByRestaurantId(restaurantId);
        if (isVegetarian) {
            foods = filterByVegetarian(foods, isVegetarian);
        }
        if (isNonVeg) {
            foods = filterByNonVeg(foods, isNonVeg);
        }
        if (isSeasonal) {
            foods = filterBySeasonal(foods, isSeasonal);
        }
        if (foodCategory != null && !foodCategory.isEmpty()) {
            foods = filterByCategory(foods, foodCategory);
        }
        return foods;
    }

    @Override
    public List<Food> searchFood(String keyword) {
        return foodRepository.searchByNameAndFoodCategory(keyword);

    }

    @Override
    public Food findFoodById(Long foodId) {
        return foodRepository.findById(foodId).orElseThrow(
                () -> new RuntimeException("Could not find food with id: " + foodId)
        );
    }

    @Override
    public Food updateAvailabilityStatus(Long foodId) {
        Food food = findFoodById(foodId);
        food.setAvailable(!food.isAvailable());
        return foodRepository.save(food);
    }

    private List<Food> filterByVegetarian(List<Food> foods, boolean isVegetarian) {
        return foods.stream().filter(food -> food.isVegetarian() == isVegetarian).toList();
    }

    private List<Food> filterBySeasonal(List<Food> foods, boolean isSeasonal) {
        return foods.stream().filter(food -> food.isSeasonal() == isSeasonal).toList();
    }

    private List<Food> filterByNonVeg(List<Food> foods, boolean isNonVeg) {
        return foods.stream().filter(food -> !food.isVegetarian()).toList();
    }

    private List<Food> filterByCategory(List<Food> foods, String foodCategory) {
        return foods.stream().filter(food -> {
            if (food.getFoodCategory() != null) {
                return food.getFoodCategory().getName().equals(foodCategory);
            }
            return false;
        }).toList();
    }
}
