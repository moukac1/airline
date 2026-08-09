package com.mouady.mapper;


import com.mouady.model.User;
import com.mouady.payload.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
//@NoArgsConstructor
//@AllArgsConstructor
@Builder
public class UserMapper {

    public static UserDTO toDTO(User user) {
        if(user == null) {
            return null;
        }
        return UserDTO.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .phone(user.getPhone())
                .email(user.getEmail())
                .lastLogin(user.getLastLoginAt())
                .role(user.getRole())
                .build();
    }


}
