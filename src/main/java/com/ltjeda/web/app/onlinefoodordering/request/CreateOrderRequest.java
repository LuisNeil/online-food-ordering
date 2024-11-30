package com.ltjeda.web.app.onlinefoodordering.request;

import com.ltjeda.web.app.onlinefoodordering.model.Address;
import lombok.Data;

@Data
public class CreateOrderRequest {
    private Long restaurantId;
    private Address address;
}
