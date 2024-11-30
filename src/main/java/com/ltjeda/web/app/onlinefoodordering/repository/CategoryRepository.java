package com.ltjeda.web.app.onlinefoodordering.repository;

import com.ltjeda.web.app.onlinefoodordering.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findByRestaurantId(Long restaurantId);
}
