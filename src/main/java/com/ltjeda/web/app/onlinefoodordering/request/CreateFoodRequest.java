package com.ltjeda.web.app.onlinefoodordering.request;

import com.ltjeda.web.app.onlinefoodordering.model.Category;
import com.ltjeda.web.app.onlinefoodordering.model.IngredientsItem;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CreateFoodRequest {
    private String name;
    private String description;
    private BigDecimal price;
    private Category category;
    private List<String> images;
    private Long restaurantId;
    private boolean vegetarian;
    private boolean seasonal;
    private List<IngredientsItem> ingredients;
}
