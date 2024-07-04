package com.cts.apigateway.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/student")
public class StudentController {

    @GetMapping("/name/{empName}")
    @PreAuthorize("hasRole('ROLE_USER')") // only user role can access, if we disable everyone can access
    public ResponseEntity<String> findByName(@PathVariable("empName") String name){
        System.out.println("findByName method, Name: " +name);
        return ResponseEntity.status(HttpStatus.OK).body("findByName Student method: "+name);
    }
    @GetMapping("/all")
    public ResponseEntity<String> findByAllStudent(){
        System.out.println("findByAllStudent method");
        return ResponseEntity.status(HttpStatus.OK).body("findByAllStudent method");
    }
}
