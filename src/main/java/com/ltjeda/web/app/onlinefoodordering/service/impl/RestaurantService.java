package com.ltjeda.web.app.onlinefoodordering.service.impl;

import com.ltjeda.web.app.onlinefoodordering.dto.RestaurantDto;
import com.ltjeda.web.app.onlinefoodordering.model.Address;
import com.ltjeda.web.app.onlinefoodordering.model.Restaurant;
import com.ltjeda.web.app.onlinefoodordering.model.User;
import com.ltjeda.web.app.onlinefoodordering.repository.AddressRepository;
import com.ltjeda.web.app.onlinefoodordering.repository.RestaurantRepository;
import com.ltjeda.web.app.onlinefoodordering.repository.UserRepository;
import com.ltjeda.web.app.onlinefoodordering.request.CreateRestaurantRequest;
import com.ltjeda.web.app.onlinefoodordering.service.IRestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RestaurantService implements IRestaurantService {
    private final RestaurantRepository restaurantRepository;
    private final UserService userService;
    private final AddressRepository addressRepository;
    private final UserRepository userRepository;

    @Override
    public Restaurant createRestaurant(CreateRestaurantRequest restaurantRequest, User user) {
        Address address = addressRepository.save(restaurantRequest.getAddress());
        Restaurant restaurant =Restaurant.builder()
                .address(address)
                .contactInformation(restaurantRequest.getContactInformation())
                .cuisineType(restaurantRequest.getCuisineType())
                .description(restaurantRequest.getDescription())
                .images(restaurantRequest.getImages())
                .name(restaurantRequest.getName())
                .openingHours(restaurantRequest.getOpeningHours())
                .registrationDate(LocalDateTime.now())
                .owner(user)
                .build();
        return restaurantRepository.save(restaurant);
    }

    @Override
    public Restaurant updateRestaurant(Long restaurantId, CreateRestaurantRequest restaurantRequest) throws Exception {
        Restaurant restaurant = findRestaurantById(restaurantId);
        if (restaurant.getCuisineType() != null) {
            restaurant.setCuisineType(restaurantRequest.getCuisineType());
        }
        if (restaurant.getDescription() != null) {
            restaurant.setDescription(restaurantRequest.getDescription());
        }
        if (restaurant.getName() != null) {
            restaurant.setName(restaurantRequest.getName());
        }

        return restaurantRepository.save(restaurant);
    }

    @Override
    public void deleteRestaurant(Long restaurantId) {
        restaurantRepository.findById(restaurantId)
                .ifPresentOrElse(restaurantRepository::delete, () -> {
                    throw new RuntimeException();
                });
    }

    @Override
    public List<Restaurant> getAllRestaurants() {
        return restaurantRepository.findAll();
    }

    @Override
    public List<Restaurant> searchRestaurant(String keyword) {
        return restaurantRepository.findBySearchQuery(keyword);
    }

    @Override
    public Restaurant findRestaurantById(Long restaurantId) throws Exception {
        return restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new Exception("Restaurant not found"));
    }

    @Override
    public Restaurant getRestaurantByUserId(Long userId) throws Exception {
        return restaurantRepository.findByOwnerId(userId).orElseThrow(() -> new Exception("Restaurant not found"));
    }

    @Override
    public RestaurantDto addToFavorites(Long restaurantId, User user) throws Exception {
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(() -> new Exception("Restaurant not found"));
        RestaurantDto dto = RestaurantDto.builder()
                .description(restaurant.getDescription())
                .images(restaurant.getImages())
                .title(restaurant.getName())
                .id(restaurantId)
                .build();
        boolean isFavorited = false;
        List<RestaurantDto> favorites = user.getFavorites();
        for(RestaurantDto favorite : favorites) {
            if(favorite.getId().equals(restaurantId)) {
                isFavorited = true;
                break;
            }
        }
        if(isFavorited) {
           favorites.removeIf(favorite -> favorite.getId().equals(restaurantId));
        } else {
            favorites.add(dto);

        }
        User updatedUser = userRepository.save(user);
        return dto;
    }

    @Override
    public Restaurant updateRestaurantStatus(Long restaurantId) throws Exception {
        Restaurant restaurant = findRestaurantById(restaurantId);
        restaurant.setOpen(!restaurant.isOpen());
        return restaurantRepository.save(restaurant);
    }
}
