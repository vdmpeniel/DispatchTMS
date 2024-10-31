package com.example.trucktracking.controllers;
import com.example.trucktracking.entities.CustomResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/")
public class MainController {

    @GetMapping(
        value = "get_status",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public CustomResponse getApiStatus() throws JsonProcessingException {
        log.info(" Get Mapping: get_status");
        CustomResponse response = CustomResponse.builder()
                .status("OK")
                .message("App is up.")
                .body("")
                .build();
        return response;
    }

    @PostMapping(
            value = "driver",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public CustomResponse updateDriverData(@RequestBody String body){
        log.info("Post Mapping: driver");
        CustomResponse response = CustomResponse.builder()
                .status("OK")
                .message("Record Updated")
                .body(body)
                .build();
        return response;
    }

    @ExceptionHandler
    public void exceptionHandler(Exception e){
        log.error("Exception: {}", e.getMessage());
    }
}
