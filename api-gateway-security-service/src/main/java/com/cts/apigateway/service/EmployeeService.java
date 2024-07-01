package com.cts.apigateway.service;

import com.cts.apigateway.dto.EmployeeDTO;
import com.cts.apigateway.entity.Employee;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {
    Optional<EmployeeDTO> findById(int id);
    Optional<List<EmployeeDTO>> findByName(String name);
    Optional<EmployeeDTO> createEmployeeRecord(EmployeeDTO emp);
    Optional<EmployeeDTO> updateEmployeeRecord(EmployeeDTO emp);
    String deleteById(int id);
    Optional<List<EmployeeDTO>> findByAll();
    Optional<EmployeeDTO> patchEmployeeRecord(int id, EmployeeDTO emp);

    Optional<String> insertEmployee(String name, String address, double salary);
}
