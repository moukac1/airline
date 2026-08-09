package com.mouady.service;

import com.mouady.payload.dto.UserDTO;
import com.mouady.payload.response.AuthResponse;

public interface AuthService {

    AuthResponse login(String email , String password) throws Exception;
    AuthResponse signup(UserDTO req) throws Exception;
}
