package com.ltjeda.web.app.onlinefoodordering.repository;

import com.ltjeda.web.app.onlinefoodordering.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}
