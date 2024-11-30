package com.ltjeda.web.app.onlinefoodordering.controller;

import com.ltjeda.web.app.onlinefoodordering.model.Food;
import com.ltjeda.web.app.onlinefoodordering.model.User;
import com.ltjeda.web.app.onlinefoodordering.service.IFoodService;
import com.ltjeda.web.app.onlinefoodordering.service.IRestaurantService;
import com.ltjeda.web.app.onlinefoodordering.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/foods")
public class FoodController {

    private final IFoodService foodService;
    private final IUserService userService;
    private final IRestaurantService restaurantService;

    @GetMapping("/search")
    public ResponseEntity<List<Food>> searchFood(@RequestParam String keyword,
                                                 @RequestHeader("Authorization") String token) throws Exception {
        User user = userService.findUserByJwtToken(token);
        return new ResponseEntity<>(foodService.searchFood(keyword), HttpStatus.OK);
    }

    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<Food>> getRestaurantFoods(@PathVariable Long restaurantId,
                                                         @RequestParam boolean vegetarian,
                                                         @RequestParam boolean seasonal,
                                                         @RequestParam boolean nonVeg,
                                                         @RequestParam(required = false) String foodCategory,
                                                         @RequestHeader("Authorization") String token
                                                 ) throws Exception {
        User user = userService.findUserByJwtToken(token);
        return new ResponseEntity<>(foodService.getRestaurantFoods(restaurantId, vegetarian, seasonal, nonVeg, foodCategory), HttpStatus.OK);
    }


}
