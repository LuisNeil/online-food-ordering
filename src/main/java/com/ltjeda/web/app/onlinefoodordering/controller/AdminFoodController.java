package com.ltjeda.web.app.onlinefoodordering.controller;

import com.ltjeda.web.app.onlinefoodordering.model.Food;
import com.ltjeda.web.app.onlinefoodordering.model.Restaurant;
import com.ltjeda.web.app.onlinefoodordering.model.User;
import com.ltjeda.web.app.onlinefoodordering.request.CreateFoodRequest;
import com.ltjeda.web.app.onlinefoodordering.response.ApiResponse;
import com.ltjeda.web.app.onlinefoodordering.service.IFoodService;
import com.ltjeda.web.app.onlinefoodordering.service.IRestaurantService;
import com.ltjeda.web.app.onlinefoodordering.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/foods")
public class AdminFoodController {

    private final IFoodService foodService;
    private final IUserService userService;
    private final IRestaurantService restaurantService;

    @PostMapping("/create")
    public ResponseEntity<Food> createFood(@RequestBody CreateFoodRequest request,
                                           @RequestHeader("Authorization") String token) throws Exception {
        User user = userService.findUserByJwtToken(token);
        Restaurant restaurant = restaurantService.findRestaurantById(request.getRestaurantId());
        return new ResponseEntity<>(foodService.createFood(request, request.getCategory(), restaurant), HttpStatus.CREATED);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<ApiResponse> deleteFood(@PathVariable Long id,
                                                  @RequestHeader ("Authorization") String token) throws Exception {
        User user = userService.findUserByJwtToken(token);
        restaurantService.deleteRestaurant(id);
        return new ResponseEntity<>(new ApiResponse("Food deleted successfully"), HttpStatus.NO_CONTENT);
    }

    @PutMapping("update-availability/{id}")
    public ResponseEntity<Food> updateAvailability(@PathVariable Long id,
                                                   @RequestHeader("Authorization") String token) throws Exception {
        User user = userService.findUserByJwtToken(token);
        return new ResponseEntity<>(foodService.updateAvailabilityStatus(id),HttpStatus.OK);
    }

}
