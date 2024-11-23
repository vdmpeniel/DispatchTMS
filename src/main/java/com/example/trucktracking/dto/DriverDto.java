package com.example.trucktracking.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
public class DriverDto {
    String status;
    String name;
    String lastName;
    String address;
    String truckNumber;
    String trailerNumber;
}
