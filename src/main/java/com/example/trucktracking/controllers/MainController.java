package com.example.trucktracking.controllers;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/")
public class MainController {

    @GetMapping("get_status")
    public String getApiStatus(){
        return "Hi there!";
//        return CustomResponse.builder()
//                .status()
//                .message()
//                .body()
//                .build();
    }
}
