package com.ltjeda.web.app.onlinefoodordering.service;

import com.ltjeda.web.app.onlinefoodordering.model.Category;

import java.util.List;

public interface ICategoryService {

    Category createCategory(String name, Long userId) throws Exception;
    List<Category> findCategoryByRestaurantId(Long userId) throws Exception;
    Category findCategoryById(Long id) throws Exception;
}
