package com.consulting.mgt.springboot.acmehrsystem.service;

import java.util.List;

import com.consulting.mgt.springboot.acmehrsystem.model.Employee;

public interface EmployeeService {

	Employee retrieveById(Long id); // ya

	List<Employee> retrieveAll(); // ya
	
	Employee register(Employee employee); // ya

	Employee update(Employee employee); // ya

	Employee delete(Employee employee); // ya

	List<Employee> retrieveByFirstName(String firstName); // ya

	List<Employee> retrieveByFirstNameAndLastName(String firstName, String lastName); // ya

	Employee retrieveByEmployeeNumber(String employeeNumber); // ya
}
