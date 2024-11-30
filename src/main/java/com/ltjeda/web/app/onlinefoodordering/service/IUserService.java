package com.ltjeda.web.app.onlinefoodordering.service;

import com.ltjeda.web.app.onlinefoodordering.model.User;

public interface IUserService {

    User findUserByJwtToken(String jwtToken) throws Exception;
    User findUserByEmail(String email) throws Exception;
}
