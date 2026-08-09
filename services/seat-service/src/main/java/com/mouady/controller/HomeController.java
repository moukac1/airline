package com.mouady.controller;


import com.mouady.payload.response.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping
    public ApiResponse home() {
        ApiResponse apiResponse = new ApiResponse("les");
        apiResponse.setMessage("Hello World! from seat-service");
        return apiResponse;
    }
}
