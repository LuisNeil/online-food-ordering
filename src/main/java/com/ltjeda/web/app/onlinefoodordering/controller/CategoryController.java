package com.ltjeda.web.app.onlinefoodordering.controller;

import com.ltjeda.web.app.onlinefoodordering.model.Category;
import com.ltjeda.web.app.onlinefoodordering.model.User;
import com.ltjeda.web.app.onlinefoodordering.service.ICategoryService;
import com.ltjeda.web.app.onlinefoodordering.service.IUserService;
import com.ltjeda.web.app.onlinefoodordering.service.impl.CategoryService;
import com.ltjeda.web.app.onlinefoodordering.service.impl.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CategoryController {

    private final ICategoryService categoryService;
    private final IUserService userService;

    @PostMapping("/admin/category/create")
    public ResponseEntity<Category> createCategory(@RequestBody Category category,
                                                   @RequestHeader("Authorization") String token) throws Exception {
        User user = userService.findUserByJwtToken(token);
        return new ResponseEntity<>(categoryService.createCategory(category.getName(), user.getId()), HttpStatus.CREATED);
    }

    @GetMapping("category/{userId}")
    public ResponseEntity<List<Category>> getRestaurantCategories(@PathVariable Long userId,
                                                                  @RequestHeader("Authorization") String token) throws Exception {
        User user = userService.findUserByJwtToken(token);
        return new ResponseEntity<>(categoryService.findCategoryByRestaurantId(userId), HttpStatus.OK);
    }

    @GetMapping("category-by-id/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long id,
                                                    @RequestHeader("Authorization") String token) throws Exception {
        User user = userService.findUserByJwtToken(token);
        return new ResponseEntity<>(categoryService.findCategoryById(id), HttpStatus.OK);
    }
}
