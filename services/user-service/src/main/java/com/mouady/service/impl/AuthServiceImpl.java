package com.mouady.service.impl;

import com.mouady.config.JwtProvider;
import com.mouady.enums.UserRole;
import com.mouady.mapper.UserMapper;
import com.mouady.model.User;
import com.mouady.payload.dto.UserDTO;
import com.mouady.payload.response.AuthResponse;
import com.mouady.repository.UserRepository;
import com.mouady.service.AuthService;
import com.mouady.service.CustomUserDetailsService;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Builder
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository ;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final CustomUserDetailsService customUserDetailsService;

    @Transactional
    @Override
    public AuthResponse signup(UserDTO req) throws Exception {

        /*
        * pour effectuer sign up :
        * 1) i ll check the email
        * 2) encode passwor via BcryptPassword
        * 3) save user
        * 4) generate jwtToken via
        * 5) return token
        * */
        User  user = userRepository.findByEmail(req.getEmail());
        if (user != null) {
            throw new Exception("email already exists") ;
        }

        if(req.getRole()== UserRole.ROLE_SYSTEM_ADMIN){
            throw new Exception("you cannot sign up system admin");
        }
        User newUser = User.builder()

                .email(req.getEmail())
                .password(passwordEncoder.encode(req.getPassword()))
                .role(req.getRole())
                .phone(req.getPhone())
                .fullName(req.getFullName())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .lastLoginAt(LocalDateTime.now())
                .build();

        User savedUser = userRepository.save(newUser);
        Authentication auth =
                new UsernamePasswordAuthenticationToken(
                        savedUser.getEmail(), savedUser.getPassword()
                );

        String jwt = jwtProvider.generateToken(
                auth,  savedUser.getId()
        ) ;
        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(jwt);
        authResponse.setUser(UserMapper.toDTO(savedUser));
        authResponse.setMessage("register success");
        authResponse.setTitle("Welcome" + savedUser.getFullName());

        return authResponse ;
    }
    @Transactional
    @Override
    public AuthResponse login(String email, String password) throws Exception {
        /*
        * steps into loginn :
        * 1° load user byemail
        * 2° compare paassword with Bcrypt
        * 3° update lastLogin time
        * 4° generate jwt token
        * 5° return token and user information
        * */
        Authentication authentication = authenticate(email,password) ;

        User user = userRepository.findByEmail(email) ;
        user.setLastLoginAt(LocalDateTime.now());
        User savedUser= userRepository.save(user) ;
        String jwt = jwtProvider.generateToken(authentication, savedUser.getId());
        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(jwt);
        authResponse.setUser(UserMapper.toDTO(savedUser));
        authResponse.setMessage("register success");
        authResponse.setTitle("Welcome" + savedUser.getFullName());


        return authResponse;
    }
    private Authentication authenticate(String email, String password) throws Exception {
        UserDetails userDetails = customUserDetailsService
                .loadUserByUsername(email);
        if(!passwordEncoder.matches(password,userDetails.getPassword())){
            throw new Exception("invalid password") ;
        }

        return new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities()
        );

    }


}
