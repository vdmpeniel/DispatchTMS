package com.example.trucktracking.controllers;
import com.example.trucktracking.entities.CustomResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/")
public class MainController {

    @GetMapping(
        value = "get_status",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public CustomResponse getApiStatus() throws JsonProcessingException {
        CustomResponse response = CustomResponse.builder()
                .message("Some Message")
                .body("Hi there")
                .build();
        return response;
    }

    @PostMapping(
            value = "driver",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public CustomResponse updateDriverData(){
        CustomResponse response = CustomResponse.builder()
                .message("Some Message")
                .body("Hi there")
                .build();
        return response;
    }
    @ExceptionHandler
    public void exceptionHandler(Exception e){

    }
}
