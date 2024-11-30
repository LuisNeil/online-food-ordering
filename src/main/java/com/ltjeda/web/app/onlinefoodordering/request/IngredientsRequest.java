package com.ltjeda.web.app.onlinefoodordering.request;

import lombok.Data;

@Data
public class IngredientsRequest {
    private Long restaurantId;
    private String ingredientName;
    private Long categoryId;
}
