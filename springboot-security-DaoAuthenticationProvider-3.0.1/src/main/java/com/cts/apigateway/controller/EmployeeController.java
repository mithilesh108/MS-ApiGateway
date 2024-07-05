package com.cts.apigateway.controller;

import com.cts.apigateway.dto.EmployeeDTO;
import com.cts.apigateway.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService service;

    @GetMapping("/id/{empId}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<String> findById(@PathVariable("empId") int id){
        System.out.println("findById method, id: " +id);
        Optional<EmployeeDTO> dtoOptional =  service.findById(id);
        return dtoOptional.isPresent()
                ? ResponseEntity.status(HttpStatus.OK).body(dtoOptional.get().toString())
                : ResponseEntity.status(HttpStatus.OK).body("Record not found for Employee Id: "+id);
    }

    @GetMapping("/name/{empName}")
    public ResponseEntity<List<EmployeeDTO>> findByName(@PathVariable("empName") String name){
        System.out.println("findByName method, Name: " +name);
        Optional<List<EmployeeDTO>> dtoOptional =   service.findByName(name);
        return dtoOptional.isPresent()
                ? ResponseEntity.status(HttpStatus.OK).body(dtoOptional.get())
                : ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }
    @GetMapping("/all")
    public ResponseEntity<List<EmployeeDTO>> findByAllEmployee(){
        System.out.println("findByAllEmployee method");
        Optional<List<EmployeeDTO>> dtoOptional =   service.findByAll();
        return dtoOptional.isPresent()
                ? ResponseEntity.status(HttpStatus.OK).body(dtoOptional.get())
                : ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

    @GetMapping("/insert")
    public ResponseEntity<String> insertEmployee(@RequestParam("name") String name, @RequestParam("add") String address,
                                                 @RequestParam("sal") double salary){
        System.out.println("insertEmployee method, name: " +name+" "+address+" "+salary);
        return ResponseEntity.status(HttpStatus.OK).body(service.insertEmployee(name, address, salary).get().toString());
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    //@PreAuthorize("hasAuthority('ROLE_ADMIN' , 'ROLE_USER')")
    public ResponseEntity<String> createEmployeeResource(@RequestBody EmployeeDTO empDto){
        System.out.println("createEmployeeResource method, " +empDto);
        Optional<EmployeeDTO> empDtoResult =  service.createEmployeeRecord(empDto);
        return empDtoResult.isPresent()
                ? ResponseEntity.status(HttpStatus.OK).body("Record created : "+empDtoResult.get().toString())
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Record not created");
    }

    @PutMapping
    public ResponseEntity<String> updateEmployeeRecord(@RequestBody EmployeeDTO emp){
        System.out.println("updateEmployeeRecord method, Emp Id : " +emp);
        Optional<EmployeeDTO> dtoOptional = service.updateEmployeeRecord(emp);
        return dtoOptional.isPresent()
                ? ResponseEntity.status(HttpStatus.OK).body("Record Updated : "+dtoOptional.get().toString())
                : ResponseEntity.status(HttpStatus.NO_CONTENT).body("Employee record not found, Id : "+emp.getId());
    }

    @PatchMapping("/{empId}")
    public ResponseEntity<String> patchEmployeeRecord(@PathVariable("empId") int id, @RequestBody EmployeeDTO emp){
        System.out.println("partialUpdateEmployeeRecord method, Emp Id : " +emp);
        return ResponseEntity.status(HttpStatus.OK).body("Record Updated : "+service.patchEmployeeRecord(id, emp).get().toString());
    }

    @DeleteMapping("/{empId}")
    public ResponseEntity<String> deleteById(@PathVariable("empId") int id){
        System.out.println("deleteById method, Emp Id : " +id);
        return  ResponseEntity.status(HttpStatus.OK).body(service.deleteById(id));
    }
}