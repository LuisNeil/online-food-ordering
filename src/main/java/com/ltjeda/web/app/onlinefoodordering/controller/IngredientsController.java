package com.ltjeda.web.app.onlinefoodordering.controller;

import com.ltjeda.web.app.onlinefoodordering.model.IngredientCategory;
import com.ltjeda.web.app.onlinefoodordering.model.IngredientsItem;
import com.ltjeda.web.app.onlinefoodordering.request.IngredientCategoryRequest;
import com.ltjeda.web.app.onlinefoodordering.request.IngredientsRequest;
import com.ltjeda.web.app.onlinefoodordering.service.IIngredientsService;
import com.ltjeda.web.app.onlinefoodordering.service.impl.IngredientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/ingredients")
public class IngredientsController {

    private final IIngredientsService ingredientService;

    @PostMapping("/create/ingredient-category")
    public ResponseEntity<IngredientCategory> createIngredientCategory(@RequestBody IngredientCategoryRequest request) throws Exception{
        IngredientCategory ingredientCategory = ingredientService.createIngredientsCategory(request.getName(), request.getRestaurantId());
        return new ResponseEntity<>(ingredientCategory, HttpStatus.CREATED);
    }

    @PostMapping("/create/ingredient-item")
    public ResponseEntity<IngredientsItem> createIngredientItem(@RequestBody IngredientsRequest request) throws Exception{
        IngredientsItem item = ingredientService.createIngredientsItem(request.getRestaurantId(), request.getIngredientName(), request.getCategoryId());
        return new ResponseEntity<>(item, HttpStatus.CREATED);
    }

    @PutMapping("/update-stoke/{id}")
    public ResponseEntity<IngredientsItem> updateStock(@PathVariable Long id) throws Exception{
        IngredientsItem item = ingredientService.updateStock(id);
        return new ResponseEntity<>(item, HttpStatus.OK);
    }

    @GetMapping("/restaurant/{id}/category")
    public ResponseEntity<List<IngredientCategory>> getIngredientsCategory(@PathVariable Long id) throws Exception{
        List<IngredientCategory> categories = ingredientService.findIngredientsCategoryByRestaurantId(id);
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @GetMapping("/restaurant/{id}")
    public ResponseEntity<List<IngredientsItem>> getRestaurantIngredients(@PathVariable Long id) throws Exception{
        List<IngredientsItem> restaurantsIngredients = ingredientService.findRestaurantsIngredients(id);
        return new ResponseEntity<>(restaurantsIngredients, HttpStatus.OK);
    }

}
