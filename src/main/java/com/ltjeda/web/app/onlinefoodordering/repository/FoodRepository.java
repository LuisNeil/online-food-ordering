package com.ltjeda.web.app.onlinefoodordering.repository;

import com.ltjeda.web.app.onlinefoodordering.model.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FoodRepository extends JpaRepository<Food, Long> {
    List<Food> findByRestaurantId(Long restaurantId);

    @Query("SELECT f FROM Food f WHERE " +
            "f.name LIKE %:keyword% OR " +
            "f.foodCategory.name LIKE %:keyword% AND " +
            "f.restaurant != NULL")
    List<Food> searchByNameAndFoodCategory(String keyword);
}
