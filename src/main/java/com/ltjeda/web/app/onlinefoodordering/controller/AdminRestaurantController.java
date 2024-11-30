package com.ltjeda.web.app.onlinefoodordering.controller;

import com.ltjeda.web.app.onlinefoodordering.model.Restaurant;
import com.ltjeda.web.app.onlinefoodordering.model.User;
import com.ltjeda.web.app.onlinefoodordering.request.CreateRestaurantRequest;
import com.ltjeda.web.app.onlinefoodordering.response.ApiResponse;
import com.ltjeda.web.app.onlinefoodordering.service.IRestaurantService;
import com.ltjeda.web.app.onlinefoodordering.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/restaurants")
public class AdminRestaurantController {

    private final IRestaurantService restaurantService;
    private final IUserService userService;

    @PostMapping("/create")
    public ResponseEntity<Restaurant> createRestaurant(@RequestBody CreateRestaurantRequest request,
                                                       @RequestHeader("Authorization") String token) throws Exception {
        User user = userService.findUserByJwtToken(token);
        Restaurant restaurant = restaurantService.createRestaurant(request, user);
        return new ResponseEntity<>(restaurant, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Restaurant> updateRestaurant(@PathVariable Long id,
                                                       @RequestBody CreateRestaurantRequest request,
                                                       @RequestHeader("Authorization") String token
                                                       ) throws Exception {
        User user = userService.findUserByJwtToken(token);
        Restaurant restaurant = restaurantService.updateRestaurant(id, request);
        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteRestaurant(@PathVariable Long id,
                                                        @RequestHeader("Authorization") String token) throws Exception {
        User user = userService.findUserByJwtToken(token);
        restaurantService.deleteRestaurant(id);
        return new ResponseEntity<>(new ApiResponse("Restaurant deleted successfully"), HttpStatus.NO_CONTENT);
    }

    @PutMapping("update-status/{id}")
    public ResponseEntity<Restaurant> updateRestaurantStatus(@PathVariable Long id,
                                                             @RequestHeader("Authorization") String token) throws Exception {
        return new ResponseEntity<>(restaurantService.updateRestaurantStatus(id), HttpStatus.OK);
    }

    @GetMapping("/users")
    public ResponseEntity<Restaurant> findRestaurantByUserId(@RequestHeader("Authorization") String token) throws Exception {
        User user = userService.findUserByJwtToken(token);
        return new ResponseEntity<>(restaurantService.getRestaurantByUserId(user.getId()), HttpStatus.OK);
    }

}
