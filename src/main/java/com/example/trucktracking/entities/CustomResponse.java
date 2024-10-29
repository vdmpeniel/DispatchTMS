package com.example.trucktracking.entities;

import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Value;

@Builder
public class CustomResponse {
    @Builder.Default
    String status = "OK";

    @Builder.Default
    String message = "Everything went well.";

    String body;


}
