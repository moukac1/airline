package com.mouady.service.impl;

import com.mouady.model.User;
import com.mouady.repository.UserRepository;
import com.mouady.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {


    private final UserRepository userRepository;



    @Override
    public User getUserByEmail(String email) throws Exception {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new Exception("not found this user");
        }
        return user;
    }

    @Override
    public User getUserById(Long id) throws Exception {
        return userRepository.findById(id).orElseThrow(
                () -> new Exception("not found this user with id: " + id)
        );
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }
}
