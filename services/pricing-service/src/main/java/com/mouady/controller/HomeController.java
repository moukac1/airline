package com.mouady.controller;

import com.mouady.payload.response.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class HomeController {
    @GetMapping
    public ApiResponse home() {
        return new ApiResponse("pricing service") ;
    }
}
