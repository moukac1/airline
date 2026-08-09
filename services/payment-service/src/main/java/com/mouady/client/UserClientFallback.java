package com.mouady.client;

import com.mouady.exception.UserException;
import com.mouady.payload.dto.UserDTO;
import org.springframework.stereotype.Component;

@Component
public class UserClientFallback implements UserClient {

    @Override
    public UserDTO getUserById(Long userId) throws UserException {
        return null;
    }
}
