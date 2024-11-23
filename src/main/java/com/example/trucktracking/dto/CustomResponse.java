package com.example.trucktracking.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
public class CustomResponse {
    @Builder.Default
    String status = "OK";

    String message;
    String body;
}
