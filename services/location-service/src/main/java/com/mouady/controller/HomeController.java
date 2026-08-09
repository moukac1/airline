package com.mouady.controller;


import com.mouady.payload.response.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/home")
    public ApiResponse HomeController() {
        ApiResponse apiResponse = new ApiResponse("dd");
        apiResponse.setMessage("location service");
        return  apiResponse ;
     }
}
