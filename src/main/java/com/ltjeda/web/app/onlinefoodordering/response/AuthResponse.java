package com.ltjeda.web.app.onlinefoodordering.response;

import com.ltjeda.web.app.onlinefoodordering.model.Role;
import lombok.Data;

@Data
public class AuthResponse {
    private String token;
    private String message;
    private Role role;
}
