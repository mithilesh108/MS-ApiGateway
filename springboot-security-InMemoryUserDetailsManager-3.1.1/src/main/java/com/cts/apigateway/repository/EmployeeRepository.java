package com.cts.apigateway.repository;

import com.cts.apigateway.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    Optional<List<Employee>> findByName(String name);

    @Modifying
    @Query(value ="INSERT INTO EMPLOYEE_JPA(NAME,ADDRESS,SALARY) VALUES (:name,:add,:sal)",  nativeQuery = true)
    int insertEmployee(@Param("name") String name,@Param("add") String address, @Param("sal") double salary);
}
