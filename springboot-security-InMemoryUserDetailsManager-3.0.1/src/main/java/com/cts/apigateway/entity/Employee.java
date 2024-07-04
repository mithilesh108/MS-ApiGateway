package com.cts.apigateway.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "EMPLOYEE_JPA")
@Data
@NoArgsConstructor
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String address;
    private double salary;
    private LocalDate dob;

    public Employee(String name, String address, double salary, LocalDate dob){
        this.name = name;
        this.address = address;
        this.salary = salary;
        this.dob = dob;
    }
}
