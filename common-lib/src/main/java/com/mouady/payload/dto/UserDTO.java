package com.mouady.payload.dto;


import com.mouady.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {


    private Long id;
    private String fullName;
    private String password;
    private String email;
    private String phone;
    private UserRole role ;
    private LocalDateTime lastLogin;


}
