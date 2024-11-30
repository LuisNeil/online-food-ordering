package com.ltjeda.web.app.onlinefoodordering.service.impl;

import com.ltjeda.web.app.onlinefoodordering.model.IngredientCategory;
import com.ltjeda.web.app.onlinefoodordering.model.IngredientsItem;
import com.ltjeda.web.app.onlinefoodordering.model.Restaurant;
import com.ltjeda.web.app.onlinefoodordering.repository.IngredientCategoryRepository;
import com.ltjeda.web.app.onlinefoodordering.repository.IngredientsItemRepository;
import com.ltjeda.web.app.onlinefoodordering.service.IIngredientsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IngredientService implements IIngredientsService {

    private final IngredientCategoryRepository ingredientCategoryRepository;
    private final IngredientsItemRepository ingredientsItemRepository;
    private final RestaurantService restaurantService;

    @Override
    public IngredientCategory createIngredientsCategory(String name, Long restaurantId) throws Exception {
        Restaurant restaurant = restaurantService.findRestaurantById(restaurantId);
        IngredientCategory ingredientCategory = IngredientCategory.builder()
                .restaurant(restaurant)
                .name(name)
                .build();
        return ingredientCategoryRepository.save(ingredientCategory);
    }

    @Override
    public IngredientCategory findIngredientsCategoryById(Long id) throws Exception {
        return ingredientCategoryRepository.findById(id).orElseThrow(Exception::new);
    }

    @Override
    public List<IngredientCategory> findIngredientsCategoryByRestaurantId(Long restaurantId) throws Exception {
        return ingredientCategoryRepository.findByRestaurantId(restaurantId);
    }

    @Override
    public IngredientsItem createIngredientsItem(Long restaurantId,
                                                 String ingredientName,
                                                 Long categoryId) throws Exception {
        Restaurant restaurant = restaurantService.findRestaurantById(restaurantId);
        IngredientCategory category = findIngredientsCategoryById(categoryId);
        IngredientsItem item = IngredientsItem.builder()
                .name(ingredientName)
                .restaurant(restaurant)
                .category(category)
                .build();
        IngredientsItem savedItem = ingredientsItemRepository.save(item);
        category.getIngredients().add(savedItem);
        return savedItem;
    }

    @Override
    public List<IngredientsItem> findRestaurantsIngredients(Long restaurantId) throws Exception {
        return ingredientsItemRepository.findByRestaurantId(restaurantId);
    }

    @Override
    public IngredientsItem updateStock(Long id) throws Exception {
        IngredientsItem ingredientsItem = ingredientsItemRepository.findById(id).orElseThrow(Exception::new);
        ingredientsItem.setInStock(!ingredientsItem.isInStock());
        return ingredientsItemRepository.save(ingredientsItem);
    }
}
