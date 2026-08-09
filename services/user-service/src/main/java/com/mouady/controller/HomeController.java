package com.mouady.controller;


import com.mouady.payload.response.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    @GetMapping
    public ApiResponse hey() {
        return new ApiResponse("welcome to user service") ;
    }
}
