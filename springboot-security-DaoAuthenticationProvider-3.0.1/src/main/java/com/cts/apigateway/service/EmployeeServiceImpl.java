package com.cts.apigateway.service;

import com.cts.apigateway.dto.EmployeeDTO;
import com.cts.apigateway.entity.Employee;
import com.cts.apigateway.exception.ResourceNotFoundException;
import com.cts.apigateway.exception.ResourceNotInsertedException;
import com.cts.apigateway.repository.EmployeeRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements  EmployeeService{

    @Autowired
    private EmployeeRepository repository;

    @Override
    public Optional<EmployeeDTO> findById(int id) {
        System.out.println("EmployeeServiceImpl findById method, id : "+id);
        EmployeeDTO employeeDTO = new EmployeeDTO();
        Optional<Employee> employee = repository.findById(id);
        if(employee.isPresent()) {
            BeanUtils.copyProperties(employee.get(), employeeDTO);
            return Optional.of(employeeDTO);
        }else
           return Optional.empty();
    }

    @Override
    public Optional<List<EmployeeDTO>> findByName(String name) {
        System.out.println(" EmployeeServiceImpl findByName method, name : "+name);
        Optional<List<Employee>> listEmployee = repository.findByName(name);
        if(listEmployee.isPresent()){
            List<EmployeeDTO> listemployeeDTO = new ArrayList<>();
            listEmployee.get().stream().forEach(employee -> {
                EmployeeDTO employeeDTO = new EmployeeDTO();
                BeanUtils.copyProperties(employee, employeeDTO);
                listemployeeDTO.add(employeeDTO);
            });
            return Optional.of(listemployeeDTO);
        }else
           return Optional.empty();
    }

    @Override
    public Optional<List<EmployeeDTO>> findByAll() {
        System.out.println("EmployeeServiceImpl findByAll method");
        Optional<List<Employee>> listEmployee = Optional.ofNullable(repository.findAll());
        if(listEmployee.isPresent()){
            List<EmployeeDTO> listemployeeDTO = new ArrayList<>();
            listEmployee.get().stream().forEach(employee -> {
                EmployeeDTO employeeDTO = new EmployeeDTO();
                BeanUtils.copyProperties(employee, employeeDTO);
                listemployeeDTO.add(employeeDTO);
            });
            return Optional.of(listemployeeDTO);
        }else
            return Optional.empty();
    }

    @Override
    public Optional<EmployeeDTO> createEmployeeRecord(EmployeeDTO emp) {
        System.out.println("EmployeeServiceImpl createEmployeeRecord method "+emp);
        Employee entity = new Employee();
        BeanUtils.copyProperties(emp, entity);
        System.out.println("Entity obj : "+emp);
        repository.save(entity);
        return Optional.of(emp);
    }

    @Transactional
    @Override
    public Optional<EmployeeDTO> updateEmployeeRecord(EmployeeDTO emp) {
        System.out.println("EmployeeServiceImpl updateEmployeeRecord method "+emp);
        Optional<Employee> employee = repository.findById(emp.getId());
        if(employee.isPresent()){
            Employee entity = employee.get();
            BeanUtils.copyProperties(emp, entity);
            System.out.println("Entity obj : "+emp);
            repository.save(entity);
            return Optional.of(emp);
        }
        return createEmployeeRecord(emp);
    }
    @Override
    public Optional<EmployeeDTO> patchEmployeeRecord(int id, EmployeeDTO empDTO) {
        System.out.println("EmployeeServiceImpl patchEmployeeRecord method id : "+id +" "+empDTO);
        Optional<Employee> employee = repository.findById(id);
        if(employee.isPresent()){
            Employee entity = employee.get();
            if (empDTO.getName() != null) {
                entity.setName(empDTO.getName());
            }
            if (empDTO.getAddress() != null) {
                entity.setAddress(empDTO.getAddress());
            }
            if (empDTO.getSalary() != 0.0) {
                entity.setSalary(empDTO.getSalary());
            }
            if (empDTO.getDob() != null) {
                entity.setDob(empDTO.getDob());
            }
            System.out.println("Entity obj : "+entity);
            repository.save(entity);
            return Optional.of(empDTO);
        }else
            throw new ResourceNotFoundException("Record not found with id " + id);
    }

    @Transactional
    @Override
    public Optional<String> insertEmployee(String name, String address, double salary) {
        System.out.println("EmployeeServiceImpl insertEmployee method, name : "+name+" "+address+" "+salary);
        int resultId = repository.insertEmployee(name, address, salary);
        System.out.println("Inserted rec : "+resultId);
        if(resultId != 0)
            return Optional.ofNullable(resultId +" Employee record inserted" );
        else
            throw new ResourceNotInsertedException("Employee record not inserted");
    }

    @Override
    public String deleteById(int id) {
        System.out.println(" EmployeeServiceImpl deleteById method, id : "+id);
        Optional<Employee> employee = repository.findById(id);
        if(employee.isPresent()) {
            repository.deleteById(id);
            return "Record deleted successfully, Id is : " + id;
        }else
            return "Record not Found, Id is : " + id;
    }


}
