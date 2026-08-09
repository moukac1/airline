package com.mouady.service;


import com.mouady.model.User;

import java.util.List;

public interface UserService {

    User getUserByEmail(String email) throws Exception;
    User getUserById(Long id) throws Exception;
    List<User> getUsers();
}
