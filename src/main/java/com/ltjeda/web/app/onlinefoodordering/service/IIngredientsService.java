package com.ltjeda.web.app.onlinefoodordering.service;

import com.ltjeda.web.app.onlinefoodordering.model.IngredientCategory;
import com.ltjeda.web.app.onlinefoodordering.model.IngredientsItem;

import java.util.List;

public interface IIngredientsService {
    IngredientCategory createIngredientsCategory(String name, Long restaurantId) throws Exception;
    IngredientCategory findIngredientsCategoryById(Long id) throws Exception;
    List<IngredientCategory> findIngredientsCategoryByRestaurantId(Long restaurantId) throws Exception;
    IngredientsItem createIngredientsItem(Long restaurantId, String ingredientName, Long categoryId) throws Exception;
    List<IngredientsItem> findRestaurantsIngredients(Long restaurantId) throws Exception;
    IngredientsItem updateStock(Long id) throws Exception;
}
