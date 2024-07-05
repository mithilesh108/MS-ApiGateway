package com.cts.apigateway.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDate;

@Data
public class EmployeeDTO {
    @ToString.Exclude
    private  int id;
    private String name;
    private String address;
    private double salary;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate dob;
}
