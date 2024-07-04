package com.cts.apigateway;

import com.cts.apigateway.entity.Employee;
import com.cts.apigateway.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class ApigatewaySecurityServiceApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ApigatewaySecurityServiceApplication.class, args);
	}

	@Autowired
	private EmployeeRepository repository;

	@Override
	public void run(String... args) throws Exception {
		System.out.println("run method");
		repository.deleteAll();
		List<Employee> list = new ArrayList<>();
		list.add(new Employee("ram","Mumbai",12345, LocalDate.now()));
		list.add(new Employee("davit","UP",10000, LocalDate.of(1999,Month.JANUARY,18)));
		list.add(new Employee("manish","Hyd",88888, LocalDate.of(1992,3,29)));
		list.add(new Employee("sita","UP",11111, LocalDate.of(1993, Month.NOVEMBER,26)));
		list.add(new Employee("ram","Delhi",33333, LocalDate.of(1995,10,12)));
		list.add(new Employee("ruby","MP",12345, LocalDate.of(1998,12,11)));
		list.add(new Employee("lakhan","UP",55555, LocalDate.of(1990,Month.APRIL,21)));
		repository.saveAllAndFlush(list);
	}
}
